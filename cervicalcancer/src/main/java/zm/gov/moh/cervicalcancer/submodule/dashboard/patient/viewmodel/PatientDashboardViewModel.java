package zm.gov.moh.cervicalcancer.submodule.dashboard.patient.viewmodel;

import android.app.Application;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.system.Os;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.LinkedHashMultimap;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneOffset;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import androidx.lifecycle.MutableLiveData;
import zm.gov.moh.cervicalcancer.OpenmrsConfig;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.model.ObsListItem;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.model.VisitEncounterItem;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.model.VisitListItem;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.model.VisitState;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.database.Database;
import zm.gov.moh.core.repository.database.DatabaseUtils;
import zm.gov.moh.core.repository.database.dao.derived.GenericDao;
import zm.gov.moh.core.repository.database.dao.domain.ConceptDao;
import zm.gov.moh.core.repository.database.dao.domain.VisitDao;
import zm.gov.moh.core.repository.database.entity.domain.Encounter;
import zm.gov.moh.core.repository.database.entity.domain.Obs;
import zm.gov.moh.core.repository.database.entity.domain.Visit;
import zm.gov.moh.core.utils.BaseAndroidViewModel;
import zm.gov.moh.core.utils.ConcurrencyUtils;
import zm.gov.moh.core.utils.InjectableViewModel;

public class PatientDashboardViewModel extends BaseAndroidViewModel implements InjectableViewModel {

    private MutableLiveData<Integer> emitVisitState;
    private Bundle bundle;
    private VisitState visitState;
    private Visit visit;
    VisitDao visitDao = getRepository().getDatabase().visitDao();
    Database db = getRepository().getDatabase();
    long person_id;

    private MutableLiveData<LinkedHashMap<Long, Collection<Boolean>>> screeningDataEmitter;
    private MutableLiveData<LinkedHashMap<Long, Collection<Boolean>>> referralDataEmitter;
    private MutableLiveData<LinkedHashMap<Long, Collection<Boolean>>> treatmentDataEmitter;
    private MutableLiveData<LinkedHashMap<Long, Collection<String>>>  providerDataEmitter;
    private MutableLiveData<Map<String,LinkedHashMultimap<Long, String>>>  ediDataEmitter;
    private MutableLiveData<LinkedList<LinkedHashMultimap<VisitListItem, VisitEncounterItem>>> visitDataEmitter;

    public PatientDashboardViewModel(Application application) {
        super(application);

        this.visitState = new VisitState();


    }

    public void setVisitState(final int state) {

        visitState.setState(state);
        persistVisit(state);

        emitVisitState.setValue(state);
    }

    public MutableLiveData<Integer> getEmitVisitState() {

        if (emitVisitState == null)
            emitVisitState = new MutableLiveData<>();

        return emitVisitState;
    }

    public MutableLiveData<LinkedHashMap<Long, Collection<Boolean>>> getScreeningDataEmitter() {

        if (screeningDataEmitter == null)
            screeningDataEmitter = new MutableLiveData<>();

        return screeningDataEmitter;
    }

    public MutableLiveData<LinkedHashMap<Long, Collection<Boolean>>> getReferralDataEmitter() {

        if (referralDataEmitter == null)
            referralDataEmitter = new MutableLiveData<>();

        return referralDataEmitter;
    }

    public MutableLiveData<LinkedHashMap<Long, Collection<Boolean>>> getTreatmentDataEmitter() {

        if (treatmentDataEmitter == null)
            treatmentDataEmitter = new MutableLiveData<>();

        return treatmentDataEmitter;
    }

    public MutableLiveData<LinkedList<LinkedHashMultimap<VisitListItem, VisitEncounterItem>>> getVisitDataEmitter() {

        if (visitDataEmitter == null)
            visitDataEmitter = new MutableLiveData<>();

        return visitDataEmitter;
    }

    public MutableLiveData<LinkedHashMap<Long, Collection<String>>> getProviderDataEmitter() {

        if (providerDataEmitter == null)
            providerDataEmitter = new MutableLiveData<>();

        return providerDataEmitter;
    }

    public MutableLiveData<Map<String,LinkedHashMultimap<Long, String>>> getEDIDataEmitter() {

        if (ediDataEmitter == null)
            ediDataEmitter = new MutableLiveData<>();

        return ediDataEmitter;
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

            ConcurrencyUtils.consumeAsync(this::createVisit, this::onError, bundle);
        else if(visit.getDateStarted() != null) {

            visit.setDateStopped(LocalDateTime.now());
            ConcurrencyUtils.consumeAsync( visit-> visitDao.updateVisit(visit),this::onError,visit);
        }
    }


    public LinkedHashMap<Long, Collection<Boolean>> extractScreeningData(List<Visit> visits){

        final long CONCEPT_ID_VIA_INSPECTION_DONE = db.conceptDao().getConceptIdByUuid(OpenmrsConfig.CONCEPT_UUID_VIA_INSPECTION_DONE);

        if(visits.size() > 0) {

            ConceptDao conceptDao = getRepository().getDatabase().conceptDao();
            GenericDao genericDao = getRepository().getDatabase().genericDao();
            LinkedHashMap<Long, Collection<Boolean>> screeningResults = new LinkedHashMap<>();

            for(Visit visit: visits) {

                LinkedHashMap<Long, Boolean> screeningData = new LinkedHashMap<>();

                List<Obs> obsIterator = getRepository()
                        .getDatabase()
                        .genericDao()
                        .getPatientObsByEncounterTypeAndVisitId(person_id,visit.getVisitId(), OpenmrsConfig.ENCOUNTER_TYPE_UUID_TEST_RESULT);

                List<Obs> obsList = db.genericDao().getPatientObsByConceptIdVisitId(34L,db.conceptDao().getConceptIdByUuid(OpenmrsConfig.CONCEPT_UUID_VIA_SCREENING_RESULT),9223372036854725890L);


                for (Obs obs:obsIterator) {

                    //Obs obs = obsIterator.next();

                    Long i = genericDao.getPatientObsCodedValueByEncounterIdConceptId(person_id,CONCEPT_ID_VIA_INSPECTION_DONE,obs.getEncounterId());

                    screeningData.put(CONCEPT_ID_VIA_INSPECTION_DONE, false);
                    screeningData.put(conceptDao.getConceptIdByUuid(OpenmrsConfig.CONCEPT_UUID_VIA_NEGATIVE), false);
                    screeningData.put(conceptDao.getConceptIdByUuid(OpenmrsConfig.CONCEPT_UUID_VIA_POSITIVE), false);
                    screeningData.put(conceptDao.getConceptIdByUuid(OpenmrsConfig.CONCEPT_UUID_SUSPECTED_CANCER), false);


                    if (screeningData.containsKey(obs.getValueCoded())|| i != null) {

                        if(i != null && i == 1)
                            screeningData.put(CONCEPT_ID_VIA_INSPECTION_DONE, true);

                        Long datetime = obs.getObsDateTime().toInstant(ZoneOffset.UTC).getEpochSecond();

                        if(screeningData.containsKey(obs.getValueCoded())) {
                            screeningData.put(obs.getValueCoded(), true);
                            screeningResults.put(datetime, ImmutableList.copyOf(screeningData.values()));
                        }
                    }
                }
            }
            return screeningResults;
        }
        return null;
    }

    public Map<String,LinkedHashMultimap<Long, String>> extractEDIData(List<Visit> visits){

        Map<String,LinkedHashMultimap<Long, String>> ediVisitData;
        if(visits.size() > 0) {
            ediVisitData = new LinkedHashMap<>();
            LinkedHashMultimap<Long, String> ediData = LinkedHashMultimap.create();
            Long ediConceptId = db.conceptDao().getConceptIdByUuid(OpenmrsConfig.CONCEPT_UUID_EDI_IMAGE);

            Obs eiObs = db.obsDao().findByConceptId(ediConceptId);

            for(Visit visit: visits) {
                String visitName = db.visitTypeDao().getVisitTypeById(visit.getVisitTypeId());
                long visitDatetime = visit.getDateStarted().toInstant(ZoneOffset.UTC).getEpochSecond();
                List<Obs> ediObs = db.obsDao().getObsByVisitIdConceptId(visit.getVisitId(),ediConceptId);

                for(Obs obs: ediObs){
                    ediData.put(visitDatetime, obs.getValueText());
                }



                ediVisitData.put(visitName,ediData);
            }
        }else return  null;

        return ediVisitData;
    }

    public LinkedHashMap<Long, Collection<Boolean>> extractReferralData(List<Visit> visits){

        if(visits.size() > 0) {

            LinkedHashMap<Long, Collection<Boolean>> referralResults = new LinkedHashMap<>();
            final long CONCEPT_ID_REASON_FOR_REFERRAL = db.conceptDao().getConceptIdByUuid(OpenmrsConfig.CONCEPT_UUID_REASON_FOR_REFERRAL);

            for(Visit visit: visits) {

                long datatime = visit.getDateStarted().toInstant(ZoneOffset.UTC).getEpochSecond();
                LinkedHashMap<Long, Boolean> referralData = new LinkedHashMap<>();
                List<Long> codedValues = db.genericDao().getPatientObsCodedValueByVisitIdConceptId(person_id,visit.getVisitId(),CONCEPT_ID_REASON_FOR_REFERRAL);

                referralData.put(db.conceptDao().getConceptIdByUuid(OpenmrsConfig.CONCEPT_UUID_LARGE_LESION_REFFERAL), false);
                referralData.put(db.conceptDao().getConceptIdByUuid(OpenmrsConfig.CONCEPT_UUID_SUSPECTED_CANCER_REFFERAL), false);

                if(codedValues != null && !codedValues.isEmpty()){

                    for(Long codedValue: codedValues)
                        referralData.put(codedValue,true);

                    referralResults.put(datatime, ImmutableList.copyOf(referralData.values()));
                }
            }
            return referralResults;
        }
        return null;
    }

    public LinkedHashMap<Long, Collection<Boolean>> extractTreatmentData(List<Visit> visits){

        final Long cryoThermolDoneToday = 165174165175L;
        final Long cryoThermolDoneDelayed = 165334176165177L;
        final Long cryoThermolDonePostponed = 165176165177L;

        if(visits.size() > 0) {

            LinkedHashMap<Long, Collection<Boolean>> treatResults = new LinkedHashMap<>();
            final long CONCEPT_ID_VIA_TREATMENT_TYPE_DONE = db.conceptDao().getConceptIdByUuid(OpenmrsConfig.CONCEPT_UUID_VIA_TREATMENT_TYPE_DONE);

            for(Visit visit: visits) {

                long datatime = visit.getDateStarted().toInstant(ZoneOffset.UTC).getEpochSecond();
                LinkedHashMap<Long, Boolean> treatmentData = new LinkedHashMap<>();
                List<Long> codedValues = db.genericDao().getPatientObsCodedValueByVisitIdConceptId(person_id,visit.getVisitId(), CONCEPT_ID_VIA_TREATMENT_TYPE_DONE);

                treatmentData.put(cryoThermolDoneToday, false);
                treatmentData.put(cryoThermolDonePostponed, false);
                treatmentData.put(cryoThermolDoneDelayed, false);

                if(codedValues != null && !codedValues.isEmpty()){

                    for(Long codedValue: codedValues) {

                        if(cryoThermolDoneToday.toString().contains(codedValue.toString()))
                            treatmentData.put(cryoThermolDoneToday, true);

                        if(cryoThermolDoneDelayed.toString().contains(codedValue.toString()))
                            treatmentData.put(cryoThermolDoneDelayed, true);

                    }

                    treatResults.put(datatime, ImmutableList.copyOf(treatmentData.values()));
                }
            }
            return treatResults;
        }
        return null;
    }

    public void onVisitsRetrieved(List<Visit> visits){

        ConcurrencyUtils.asyncFunction(this::extractScreeningData, getScreeningDataEmitter()::setValue, visits, this::onError);
        ConcurrencyUtils.asyncFunction(this::extractEDIData, getEDIDataEmitter()::setValue, visits, this::onError);
        ConcurrencyUtils.asyncFunction(this::extractReferralData, getReferralDataEmitter()::setValue, visits, this::onError);
        ConcurrencyUtils.asyncFunction(this::extractTreatmentData, getTreatmentDataEmitter()::setValue, visits, this::onError);
        ConcurrencyUtils.asyncFunction(this::extractProviderData, getProviderDataEmitter()::setValue, visits, this::onError);
        ConcurrencyUtils.asyncFunction(this::extractVisitData,getVisitDataEmitter()::setValue, visits, this::onError);
    }

    public void onError(Throwable throwable){

    }

    public void createVisit(Bundle bundle){

        long visit_id = DatabaseUtils.generateLocalId(getRepository().getDatabase().visitDao()::getMaxId);
        long visit_type_id = (Long) bundle.get(Key.VISIT_TYPE_ID);
        long location_id = (Long) bundle.get(Key.LOCATION_ID);
        long creator = (Long) bundle.get(Key.USER_ID);
        LocalDateTime start_time = LocalDateTime.now();

        visit = new Visit(visit_id,visit_type_id,person_id,location_id,creator,start_time);
        visitDao.insert(visit);
        bundle.putLong(Key.VISIT_ID, visit_id);
    }

    public LinkedHashMap<Long, Collection<String>> extractProviderData(List<Visit> visits){


        LinkedHashMap<Long, String> providerData = new LinkedHashMap<>();

        if(visits.size() > 0) {

            LinkedHashMap<Long, Collection<String>> providerResults = new LinkedHashMap<>();

            for(Visit visit: visits) {
                long datatime = visit.getDateStarted().toInstant(ZoneOffset.UTC).getEpochSecond();
                Long treatmentEncounterId = db.genericDao().getPatientEncounterIdByVisitIdEncounterTypeId(person_id,visit.getVisitId(),OpenmrsConfig.ENCOUNTER_TYPE_UUID_TREAMENT);
                Long screeningEncounterId = db.genericDao().getPatientEncounterIdByVisitIdEncounterTypeId(person_id,visit.getVisitId(),OpenmrsConfig.ENCOUNTER_TYPE_UUID_TEST_RESULT);


                if(treatmentEncounterId !=null && screeningEncounterId != null) {
                    long treatmentProviderId = db.encounterProviderDao().getByEncounterId(treatmentEncounterId).getProviderId();
                    long screeningProviderId = db.encounterProviderDao().getByEncounterId(screeningEncounterId).getProviderId();

                    String treatmentProviderName = db.providerDao().getById(treatmentProviderId).getName();
                    String screeningProviderName = db.providerDao().getById(screeningProviderId).getName();

                    if (treatmentProviderName != null && screeningProviderName != null) {

                        providerData.put(1L, screeningProviderName);
                        providerData.put(2L, treatmentProviderName);

                        providerResults.put(datatime, providerData.values());
                    }
                }
            }
            return providerResults;
        }
        return null;
    }

    public LinkedList<LinkedHashMultimap<VisitListItem,VisitEncounterItem>> extractVisitData(List<Visit> visits){

        LinkedList<LinkedHashMultimap<VisitListItem,VisitEncounterItem>> visitListItems = new LinkedList<>();
        for(Visit visit: visits){

            List<Obs> obsList = db.genericDao().getPatientObsByVisitId(person_id,visit.getVisitId());

            LinkedHashMultimap<VisitListItem,VisitEncounterItem> itemLinkedHashMultimap = LinkedHashMultimap.create();

            VisitListItem visitListItem = new VisitListItem();
            visitListItem.setId(visit.getVisitId());
            visitListItem.setDateTimeStart(visit.getDateStarted());
            visitListItem.setDateTimeStop(visit.getDateStopped());
            visitListItem.setDateCreated(visit.getDateCreated());
            visitListItem.setVisitType(db.visitTypeDao().getVisitTypeById(visit.getVisitTypeId()));

            List<Encounter> visitEncounters = db.encounterDao().getByEncounterByVisitId(visit.getVisitId());

            if(visitEncounters != null && visitEncounters.size() > 0)
                for (Encounter encounter : visitEncounters) {

                    VisitEncounterItem visitEncounterItem = new VisitEncounterItem();
                    visitEncounterItem.setId(encounter.getEncounterId());
                    visitEncounterItem.setEncounterType(db.encounterTypeDao().getEncounterTypeNameById(encounter.getEncounterType()));

                    List<Obs> encounterObs = db.obsDao().getObsByEncountId(encounter.getEncounterId());
                    for (Obs obs:encounterObs) {
                        ObsListItem obsListItem = new ObsListItem();
                        obsListItem.setId(obs.getObsId());
                        obsListItem.setConceptId(obs.getConceptId());
                        obsListItem.setConceptName(db.conceptNameDao().getConceptNameByConceptId(obs.getConceptId(),getLOCALE_EN(),preffered()));

                        if(obs.getValueCoded() != null)
                           obsListItem.setObsValue(db.conceptNameDao().getConceptNameByConceptId(obs.getValueCoded(),getLOCALE_EN(),preffered()));
                        else if(obs.getValueText() != null)
                            obsListItem.setObsValue(obs.getValueText());
                        else if(obs.getValueNumeric() != null)
                            obsListItem.setObsValue(obs.getValueNumeric().toString());
                        else if(obs.getValueDateTime() != null)
                            obsListItem.setObsValue(obs.getValueDateTime().toString());

                        visitEncounterItem.getObsListItems().add(obsListItem);
                        visitEncounterItem.getId();
                    }

                    itemLinkedHashMultimap.put(visitListItem,visitEncounterItem);
                }
                else continue;

            visitListItems.add(itemLinkedHashMultimap);

        }

        return visitListItems;
    }
}
