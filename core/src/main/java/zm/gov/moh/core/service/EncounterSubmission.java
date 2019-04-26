package zm.gov.moh.core.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import androidx.annotation.Nullable;
import io.reactivex.Observable;
import io.reactivex.Single;
import zm.gov.moh.core.R;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.model.ConceptDataType;
import zm.gov.moh.core.model.ObsValue;
import zm.gov.moh.core.repository.api.Repository;
import zm.gov.moh.core.repository.database.DatabaseUtils;
import zm.gov.moh.core.repository.database.entity.domain.Encounter;
import zm.gov.moh.core.repository.database.entity.domain.EncounterProvider;
import zm.gov.moh.core.repository.database.entity.domain.Obs;
import zm.gov.moh.core.repository.database.entity.domain.Visit;
import zm.gov.moh.core.utils.InjectableViewModel;
import zm.gov.moh.core.utils.InjectorUtils;

public class EncounterSubmission extends IntentService implements InjectableViewModel {

    public final static String FORM_DATA_KEY = "formdata";
    private Repository repository;

    public EncounterSubmission(){
        super("");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        InjectorUtils.provideRepository(this, getApplication());
        Bundle bundle = intent.getExtras();
        AndroidThreeTen.init(this);


        repository.consumeAsync(this::onSubmit,this::onError,bundle);
    }

    public void onSubmit(Bundle bundle){

        long provider_id = (long)bundle.get(Key.PROVIDER_ID);
        long user_id = (long)bundle.get(Key.USER_ID);
        long location_id = (long)bundle.get(Key.LOCATION_ID);
        long person_id = (long)bundle.get(Key.PERSON_ID);
        long encounter_type_id = (long)bundle.get(Key.ENCOUNTER_TYPE_ID);
        Long visit_id = (Long) bundle.get(Key.VISIT_ID);

        if(visit_id == null) {

            visit_id = DatabaseUtils.generateLocalId(repository.getDatabase().visitDao()::getMaxId);
            long visit_type_id = (Long) bundle.get(Key.VISIT_TYPE_ID);
            LocalDateTime start_time = LocalDateTime.now();

            Visit visit = new Visit(visit_id,visit_type_id,person_id,location_id,user_id,start_time,start_time);
            repository.getDatabase().visitDao().insert(visit);
        }

        LocalDateTime zonedDatetimeNow = LocalDateTime.now();

        long encounter_id = submitEncounter(encounter_type_id, person_id, location_id, visit_id, user_id, zonedDatetimeNow);

       submitObs(person_id, encounter_id, location_id, user_id, zonedDatetimeNow,bundle);

        submitEncounterProvider(encounter_id, provider_id,null,user_id);
    }

    public Long submitEncounter(long encounter_type_id, long patient_id, long location_id,long visit_id, long creator, LocalDateTime zonedDatetimeNow){

        long encounter_id = DatabaseUtils.generateLocalId(repository.getDatabase().encounterDao()::getMaxId);
        Encounter encounter = new Encounter(encounter_id, encounter_type_id, patient_id, location_id, visit_id, creator, zonedDatetimeNow);
        repository.getDatabase().encounterDao().insert(encounter);

        return  encounter_id;
    }

    public Long submitEncounterProvider(long encounter_id, long provider_id, Long encounter_role_id, long creator){

        long encounter_provider_id = DatabaseUtils.generateLocalId(repository.getDatabase().encounterProviderDao()::getMaxId);
        EncounterProvider encounterProvider = new EncounterProvider(encounter_provider_id,encounter_id,provider_id,creator);
        repository.getDatabase().encounterProviderDao().insert(encounterProvider);

        return encounter_provider_id;
    }

    public List<Long> submitObs(long person_id, long encounter_id, long location_id, long user_id, LocalDateTime zonedDatetimeNow,Bundle bundle){

        ArrayList<String> keys = bundle.getStringArrayList(Key.FORM_TAGS);

        for (String key: keys) {

            Object value = bundle.get(key);

            if (value instanceof ObsValue && ((ObsValue) value).getValue() != null) {

                long obs_id = DatabaseUtils.generateLocalId(repository.getDatabase().obsDao()::getMaxId);

                Obs obs = new Obs(obs_id, person_id, encounter_id, zonedDatetimeNow, location_id, user_id);

                ObsValue<Object> obsValue = (ObsValue<Object>) value;

                List<Obs> obsList = new LinkedList<>();

                String conceptDataType = (obsValue.getConceptDataType().equals(ConceptDataType.BOOLEAN)) ?
                        ConceptDataType.CODED
                        : obsValue.getConceptDataType();

                switch (conceptDataType) {

                    case ConceptDataType.NUMERIC:
                        String numericValue = obsValue.getValue().toString();
                        if(android.text.TextUtils.isDigitsOnly(numericValue))
                            break;

                        obsList.add(obs.setConceptId(obsValue.getConceptId())
                                .setValue(Double.valueOf(obsValue.getValue().toString())));
                        break;

                    case ConceptDataType.TEXT:

                        obsList.add(obs.setConceptId(obsValue.getConceptId())
                                .setValue(obsValue.getValue().toString()));
                        break;

                    case ConceptDataType.DATE:

                        String date = obsValue.getValue().toString() + this.getResources().getString(R.string.zoned_time_mid_day);

                        obsList.add(obs.setConceptId(obsValue.getConceptId())
                                .setValue(LocalDateTime.parse(date, DateTimeFormatter.ISO_ZONED_DATE_TIME)));
                        break;

                    case ConceptDataType.CODED:

                        Set<Long> answerConcepts = (Set<Long>) obsValue.getValue();

                        if(answerConcepts.isEmpty())
                            break;
                        obsList.addAll(obs.setConceptId(obsValue.getConceptId())
                                .setValue(answerConcepts));
                        break;
                }

                if(!obsList.isEmpty())
                    repository.getDatabase().obsDao().insert(obsList);
                bundle.remove(key);
            }
        }

        return null;
    }

    @Override
    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    @Override
    public Repository getRepository() {
        return repository;
    }

    public void onError(Throwable throwable){
        Exception e = new Exception(throwable);
    }
}