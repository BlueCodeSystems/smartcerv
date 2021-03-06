package zm.gov.moh.core.service;

import android.content.Intent;
import android.os.Bundle;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import androidx.annotation.Nullable;
import zm.gov.moh.core.model.ConceptDataType;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.model.ObsValue;
import zm.gov.moh.core.model.Visit;
import zm.gov.moh.core.model.VisitState;
import zm.gov.moh.core.repository.database.DatabaseUtils;
import zm.gov.moh.core.repository.database.entity.domain.EncounterEntity;
import zm.gov.moh.core.repository.database.entity.domain.EncounterProvider;
import zm.gov.moh.core.repository.database.entity.domain.ObsEntity;
import zm.gov.moh.core.repository.database.entity.domain.VisitEntity;
import zm.gov.moh.core.utils.Utils;

public class PersistEncounter extends PersistService {

    public PersistEncounter() {
        super(ServiceManager.Service.PERSIST_ENCOUNTERS);
    }

    @Override
    protected void executeAsync() {

        long provider_id = (long) mBundle.get(Key.PROVIDER_ID);
        long user_id = (long) mBundle.get(Key.USER_ID);
        long location_id = (long) mBundle.get(Key.LOCATION_ID);
        long person_id = (long) mBundle.get(Key.PERSON_ID);
        long encounter_type_id = (long) mBundle.get(Key.ENCOUNTER_TYPE_ID);
        Long visit_id = (Long) mBundle.get(Key.VISIT_ID);

        LocalDate visitDatetime =  db.visitDao().getDateStartTimeByVisitId(visit_id).toLocalDate();

        LocalDateTime encounterDateTime = LocalDateTime.of(visitDatetime, LocalTime.now());

        //void previous visit data
        EncounterEntity encounterEntity = db.encounterDao().getByTypeVisitId(visit_id,encounter_type_id);

        if(encounterEntity != null){

            encounterEntity.setVoided((short) 1);
            db.encounterDao().insert(encounterEntity);

            EncounterProvider encounterProvider = db.encounterProviderDao().getByEncounterId(encounterEntity.getEncounterId());

            if(encounterProvider != null) {
                encounterProvider.setVoided((short) 1);
                db.encounterProviderDao().insert(encounterProvider);
            }

            List<ObsEntity> obsEntities = db.obsDao().getObsByEncounterId(encounterEntity.getEncounterId());

                if(obsEntities.size() > 0)
                    for(ObsEntity obsEntity:obsEntities){
                        obsEntity.setVoided((short)1);
                        db.obsDao().insert(obsEntity);
                    }
             encounterDateTime = encounterEntity.getEncounterDatetime();
        }



        long encounter_id = submitEncounter(encounter_type_id, person_id, location_id, visit_id, user_id, encounterDateTime);

        submitObs(person_id, encounter_id, location_id, user_id, encounterDateTime, mBundle);

        submitEncounterProvider(encounter_id, provider_id, null, user_id);
    }

    public Long submitEncounter(long encounter_type_id, long patient_id, long location_id, long visit_id, long creator, LocalDateTime zonedDatetimeNow) {

        long encounter_id = DatabaseUtils.generateLocalId(getRepository().getDatabase().encounterDao()::getMaxId);
        EncounterEntity encounter = new EncounterEntity(encounter_id, encounter_type_id, patient_id, location_id, visit_id, creator, zonedDatetimeNow);
        getRepository().getDatabase().encounterDao().insert(encounter);

        return encounter_id;
    }

    public Long submitEncounterProvider(long encounter_id, long provider_id, Long encounter_role_id, long creator) {

        long encounter_provider_id = DatabaseUtils.generateLocalId(getRepository().getDatabase().encounterProviderDao()::getMaxId);
        EncounterProvider encounterProvider = new EncounterProvider(encounter_provider_id, encounter_id, provider_id, creator);
        getRepository().getDatabase().encounterProviderDao().insert(encounterProvider);

        return encounter_provider_id;
    }

    public List<Long> submitObs(long person_id, long encounter_id, long location_id, long user_id, LocalDateTime zonedDatetimeNow, Bundle mBundle) {

        ArrayList<String> keys = mBundle.getStringArrayList(Key.FORM_TAGS);

        try {

            for (String key : keys) {

                Object value = mBundle.get(key);

                if (value instanceof ObsValue && ((ObsValue) value).getValue() != null) {

                    long obs_id = DatabaseUtils.generateLocalId(getRepository().getDatabase().obsDao()::getMaxId);

                    ObsEntity obs = new ObsEntity(obs_id, person_id, encounter_id, zonedDatetimeNow, location_id, user_id);


                    ObsValue<Object> obsValue = (ObsValue<Object>) value;

                    List<ObsEntity> obsList = new LinkedList<>();

                    String conceptDataType = (obsValue.getConceptDataType().equals(ConceptDataType.BOOLEAN)) ?
                            ConceptDataType.CODED
                            : obsValue.getConceptDataType();

                    switch (conceptDataType) {


                        case ConceptDataType.NUMERIC:
                            String numericValue = obsValue.getValue().toString();


                            //Persist numeric value
                            if (android.text.TextUtils.isDigitsOnly(numericValue)) {
                                obsList.add(obs.setObsConceptId(obsValue.getConceptId())
                                        .setValue(Double.valueOf(obsValue.getValue().toString())));
                            }


                            if (Utils.isNumber(numericValue)) {
                                obsList.add(obs.setObsConceptId(obsValue.getConceptId())
                                        .setValue(Double.valueOf(obsValue.getValue().toString())));
                            }

                            break;

                        case ConceptDataType.TEXT:
                            // String textValue = obsValue.toString();// if (Utils.getStringFromInputStream(textValue instanceof  String = True)) {
                            obsList.add(obs.setObsConceptId(obsValue.getConceptId())
                                    .setValue(obsValue.getValue().toString()));

                            break;

                        case ConceptDataType.DATE:

                            String date = obsValue.getValue().toString() + MID_DAY_TIME;

                            obsList.add(obs.setObsConceptId(obsValue.getConceptId())
                                    .setValue(LocalDateTime.parse(date, DateTimeFormatter.ISO_ZONED_DATE_TIME)));
                            break;

                        case ConceptDataType.CODED:

                            Set<Long> answerConcepts = (Set<Long>) obsValue.getValue();

                            if (answerConcepts.isEmpty())
                                break;
                            obsList.addAll(obs.setObsConceptId(obsValue.getConceptId())
                                    .setValue(answerConcepts));
                            break;
                    }

                    if (!obsList.isEmpty())
                        getRepository().getDatabase().obsDao().insert(obsList);
                    mBundle.remove(key);
                }
            }
        }catch (Exception e){
            Exception e1 =e;
        }

        notifyCompleted();

        return null;
    }

    public void onError(Throwable throwable) {
        Exception e = new Exception(throwable);
    }
}