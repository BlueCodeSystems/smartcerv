package zm.gov.moh.core.service;

import android.app.IntentService;
import android.content.Intent;

import com.jakewharton.threetenabp.AndroidThreeTen;

import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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
        HashMap<String,Object> formData = (HashMap<String,Object>)intent.getExtras().getSerializable(FORM_DATA_KEY);
        AndroidThreeTen.init(this);

        repository.consumeAsync(this::onSubmit,this::onError,formData);
    }

    public void onSubmit(HashMap<String,Object> formData){

        long provider_id = (long)formData.get(Key.PROVIDER_ID);
        long user_id = (long)formData.get(Key.USER_ID);
        long location_id = (long)formData.get(Key.LOCATION_ID);
        long person_id = (long)formData.get(Key.PERSON_ID);
        long encounter_type_id = (long)formData.get(Key.ENCOUNTER_TYPE_ID);
        long visit_id = (long)formData.get(Key.VISIT_ID);
        ZonedDateTime zonedDatetimeNow = ZonedDateTime.now();

        long encounter_id = submitEncounter(encounter_type_id, person_id, location_id, visit_id, user_id, zonedDatetimeNow);

        submitObs(person_id, encounter_id, location_id, user_id, zonedDatetimeNow,formData.values());

        submitEncounterProvider(encounter_id, provider_id,null,user_id);
    }

    public Long submitEncounter(long encounter_type_id, long patient_id, long location_id,long visit_id, long creator, ZonedDateTime zonedDatetimeNow){

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

    public List<Long> submitObs(long person_id, long encounter_id, long location_id, long user_id, ZonedDateTime zonedDatetimeNow,Collection<Object> formValues){

        long obs_id = DatabaseUtils.generateLocalId(repository.getDatabase().obsDao()::getMaxId);

        for(Object value : formValues)
            if(value instanceof ObsValue && ((ObsValue) value).getValue() != null) {

                Obs obs = new Obs(obs_id, person_id, encounter_id, zonedDatetimeNow, location_id, user_id);

                ObsValue<Object>obsValue = (ObsValue<Object>)value;

                List<Obs> obsList = new LinkedList<>();

                String conceptDataType = (obsValue.getConceptDataType().equals(ConceptDataType.BOOLEAN))?
                        ConceptDataType.CODED
                        :obsValue.getConceptDataType();

                switch (conceptDataType) {

                    case ConceptDataType.NUMERIC:

                        obsList.add(obs.setConceptId(obsValue.getConceptId())
                                .setValue(Double.valueOf(obsValue.getValue().toString())));
                        break;

                    case ConceptDataType.TEXT:

                        obsList.add(obs.setConceptId(obsValue.getConceptId())
                                .setValue(Double.valueOf(obsValue.getValue().toString())));
                        break;

                    case ConceptDataType.DATE:

                        String date = obsValue.getValue().toString()+this.getResources().getString(R.string.zoned_time_mid_day);

                        obsList.add(obs.setConceptId(obsValue.getConceptId())
                                .setValue(ZonedDateTime.parse(date, DateTimeFormatter.ISO_ZONED_DATE_TIME)));
                        break;

                    case ConceptDataType.CODED:

                        Set<Long> answerConcepts = (Set<Long>)obsValue.getValue();
                        obsList.addAll(obs.setConceptId(obsValue.getConceptId())
                                .setValue(answerConcepts));
                        break;
                }

                repository.getDatabase().obsDao().insert(obsList);

                return Observable.fromIterable(obsList)
                        .map(obs1 -> obs1.obs_id)
                        .toList().blockingGet();
            }

            return null;
    }

    @Override
    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public void onError(Throwable throwable){
        Exception e = new Exception(throwable);
    }
}