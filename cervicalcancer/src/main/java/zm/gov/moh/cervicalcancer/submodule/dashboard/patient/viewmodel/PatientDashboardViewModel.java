package zm.gov.moh.cervicalcancer.submodule.dashboard.patient.viewmodel;

import android.app.Application;
import android.os.Bundle;

import org.threeten.bp.ZonedDateTime;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.temporal.TemporalField;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import androidx.lifecycle.MutableLiveData;
import zm.gov.moh.cervicalcancer.ModuleConfig;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.model.VisitState;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.database.Database;
import zm.gov.moh.core.repository.database.DatabaseUtils;
import zm.gov.moh.core.repository.database.dao.derived.GenericDao;
import zm.gov.moh.core.repository.database.dao.domain.ConceptDao;
import zm.gov.moh.core.repository.database.dao.domain.VisitDao;
import zm.gov.moh.core.repository.database.entity.domain.Obs;
import zm.gov.moh.core.repository.database.entity.domain.Visit;
import zm.gov.moh.core.utils.BaseAndroidViewModel;
import zm.gov.moh.core.utils.InjectableViewModel;

public class PatientDashboardViewModel extends BaseAndroidViewModel implements InjectableViewModel {

    private MutableLiveData<Integer> emitVisitState;
    private Bundle bundle;
    private VisitState visitState;
    private Visit visit;
    VisitDao visitDao = getRepository().getDatabase().visitDao();
    Database db = getRepository().getDatabase();
    long person_id;

    private MutableLiveData<LinkedHashMap<Long,Collection<Boolean>>> emitScreenigData;

    public PatientDashboardViewModel(Application application){
        super(application);

        this.visitState = new VisitState();


    }

    public void setVisitState(final int state){

        visitState.setState(state);
        persistVisit(state);


        emitVisitState.setValue(state);
    }

    public MutableLiveData<Integer> getEmitVisitState() {

        if(emitVisitState == null)
            emitVisitState = new MutableLiveData<>();

        return emitVisitState;
    }

    public MutableLiveData<LinkedHashMap<Long,Collection<Boolean>>> getEmitScreenigData() {

        if(emitScreenigData == null)
            emitScreenigData = new MutableLiveData<>();

        return emitScreenigData;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;

        person_id = (Long) bundle.get(Key.PERSON_ID);


    }

    public Bundle getBundle() {

        if(bundle == null)
            bundle = new Bundle();

        return bundle;
    }

    public VisitState getVisitState() {
        return visitState;
    }

    public void persistVisit(int visitState){


        if(visit == null)
            visit = new Visit();

        if(visitState == VisitState.STARTED)
            getRepository().consumeAsync(this::createVisit, this::onError, bundle);
        else if(visit.getDate_started() != null) {

            visit.setDate_stopped(ZonedDateTime.now());
            getRepository().consumeAsync( visit-> visitDao.updateVisit(visit),this::onError,visit);
        }
    }


    public LinkedHashMap<Long, Collection<Boolean>> extractScreeningData(List<Visit> visits){

        final long CONCEPT_ID_VIA_INSPECTION_DONE = db.conceptDao().getConceptIdByUuid(ModuleConfig.CONCEPT_UUID_VIA_INSPECTION_DONE);

        if(visits.size() > 0) {

            ConceptDao conceptDao = getRepository().getDatabase().conceptDao();
            GenericDao genericDao = getRepository().getDatabase().genericDao();
            LinkedHashMap<Long, Collection<Boolean>> screeningResults = new LinkedHashMap<>();

            for(Visit visit: visits) {

                LinkedHashMap<Long, Boolean> screeningData = new LinkedHashMap<>();

                Iterator<Obs> obsIterator = getRepository()
                        .getDatabase()
                        .genericDao()
                        .getPatientObsByEncounterTypeAndVisitId(person_id,visit.getVisit_id(), ModuleConfig.ENCOUNTER_TYPE_UUID_TEST_RESULT)
                        .iterator();


                while (obsIterator.hasNext()) {

                    Obs obs = obsIterator.next();

                    Long i = genericDao.getPatientObsCodedValueByEncounterIdConceptId(person_id,CONCEPT_ID_VIA_INSPECTION_DONE,obs.getEncounter_id());

                    screeningData.put(CONCEPT_ID_VIA_INSPECTION_DONE, false);
                    screeningData.put(conceptDao.getConceptIdByUuid(ModuleConfig.CONCEPT_UUID_VIA_NEGATIVE), false);
                    screeningData.put(conceptDao.getConceptIdByUuid(ModuleConfig.CONCEPT_UUID_VIA_POSITIVE), false);
                    screeningData.put(conceptDao.getConceptIdByUuid(ModuleConfig.CONCEPT_UUID_SUSPECTED_CANCER), false);


                    if (screeningData.containsKey(obs.getValue_coded())|| i != null) {

                        if(i != null && i == 1);
                            screeningData.put(CONCEPT_ID_VIA_INSPECTION_DONE, true);

                        Long datetime = obs.getObs_datetime().toInstant().getEpochSecond();

                        if(screeningData.containsKey(obs.getValue_coded()))
                            screeningData.put(obs.getValue_coded(), true);

                        screeningResults.put(datetime, screeningData.values());
                    }
                }
            }
            return screeningResults;
        }
        return null;
    }

    public void onVisitsRetrieved(List<Visit> visits){
        getRepository().asyncFunction(this::extractScreeningData, getEmitScreenigData()::setValue, visits, this::onError);
    }

    public void onError(Throwable throwable){

    }

    public void createVisit(Bundle bundle){

        long visit_id = DatabaseUtils.generateLocalId(getRepository().getDatabase().visitDao()::getMaxId);
        long visit_type_id = (Long) bundle.get(Key.VISIT_TYPE_ID);
        long location_id = (Long) bundle.get(Key.LOCATION_ID);
        long creator = (Long) bundle.get(Key.USER_ID);
        ZonedDateTime start_time = ZonedDateTime.now();

        visit = new Visit(visit_id,visit_type_id,person_id,location_id,creator,start_time);
        visitDao.insert(visit);
        bundle.putLong(Key.VISIT_ID, visit_id);;
    }
}
