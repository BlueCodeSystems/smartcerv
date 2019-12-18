package zm.gov.moh.cervicalcancer.submodule.dashboard.patient.viewmodel;

import android.app.Application;
import android.os.Bundle;

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
import zm.gov.moh.core.model.VisitState;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.database.Database;
import zm.gov.moh.core.repository.database.DatabaseUtils;
import zm.gov.moh.core.repository.database.dao.derived.GenericDao;
import zm.gov.moh.core.repository.database.dao.domain.ConceptDao;
import zm.gov.moh.core.repository.database.dao.domain.VisitDao;
import zm.gov.moh.core.repository.database.entity.domain.EncounterEntity;
import zm.gov.moh.core.repository.database.entity.domain.ObsEntity;
import zm.gov.moh.core.repository.database.entity.domain.PersonAttributeEntity;
import zm.gov.moh.core.repository.database.entity.domain.PersonName;
import zm.gov.moh.core.repository.database.entity.domain.ProviderAttribute;
import zm.gov.moh.core.repository.database.entity.domain.VisitEntity;
import zm.gov.moh.core.utils.BaseAndroidViewModel;
import zm.gov.moh.core.utils.ConcurrencyUtils;
import zm.gov.moh.core.utils.InjectableViewModel;

public class PatientDashboardViewModel extends BaseAndroidViewModel implements InjectableViewModel {

    private Bundle bundle;
    long person_id;
    VisitEntity visit;


    private MutableLiveData<Map.Entry<List<Long>, Map<Long,Long>>> filterObsEmitter;
    private MutableLiveData<LinkedHashMap<Long, Collection<Boolean>>> screeningDataEmitter;
    private MutableLiveData<LinkedHashMap<Long, Collection<Boolean>>> referralDataEmitter;
    private MutableLiveData<LinkedHashMap<Long, Collection<Boolean>>> treatmentDataEmitter;
    private MutableLiveData<LinkedHashMap<Long, Collection<Map.Entry<String, String>>>>  providerDataEmitter;
    private MutableLiveData<LinkedHashMap<Long, Collection<String>>>  providerNumberEmitter;
    private MutableLiveData<Map<String,LinkedHashMultimap<Long, String>>>  ediDataEmitter;
    private MutableLiveData<LinkedList<LinkedHashMultimap<VisitListItem, VisitEncounterItem>>> visitDataEmitter;

    public PatientDashboardViewModel(Application application) {
        super(application);

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

    public MutableLiveData<LinkedHashMap<Long, Collection<Map.Entry<String, String>>>> getProviderDataEmitter() {

        if (providerDataEmitter == null)
            providerDataEmitter = new MutableLiveData<>();

        return providerDataEmitter;
    }

    public MutableLiveData<LinkedHashMap<Long, Collection<String>>> getProviderNumberEmitter() {

        if (providerNumberEmitter == null)
            providerNumberEmitter = new MutableLiveData<>();

        return providerNumberEmitter;
    }

    public MutableLiveData<Map<String,LinkedHashMultimap<Long, String>>> getEDIDataEmitter() {

        if (ediDataEmitter == null)
            ediDataEmitter = new MutableLiveData<>();

        return ediDataEmitter;
    }

    public MutableLiveData<Map.Entry<List<Long>, Map<Long,Long>>> getFilterObsEmitter() {

        if (filterObsEmitter == null)
            filterObsEmitter = new MutableLiveData<>();

        return filterObsEmitter;
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


    public LinkedHashMap<Long, Collection<Boolean>> extractScreeningData(List<VisitEntity> visits){

        final long CONCEPT_ID_VIA_INSPECTION_DONE = db.conceptDao().getConceptIdByUuid(OpenmrsConfig.CONCEPT_UUID_VIA_INSPECTION_DONE);

        if(visits.size() > 0) {

            ConceptDao conceptDao = getRepository().getDatabase().conceptDao();
            GenericDao genericDao = getRepository().getDatabase().genericDao();
            LinkedHashMap<Long, Collection<Boolean>> screeningResults = new LinkedHashMap<>();

            for(VisitEntity visit: visits) {

                LinkedHashMap<Long, Boolean> screeningData = new LinkedHashMap<>();

                List<ObsEntity> obsIterator = getRepository()
                        .getDatabase()
                        .genericDao()
                        .getPatientObsByEncounterTypeAndVisitId(person_id,visit.getVisitId(), OpenmrsConfig.ENCOUNTER_TYPE_UUID_TEST_RESULT);

                List<ObsEntity> obsList = db.genericDao().getPatientObsByConceptIdVisitId(34L,db.conceptDao().getConceptIdByUuid(OpenmrsConfig.CONCEPT_UUID_VIA_SCREENING_RESULT),9223372036854725890L);


                for (ObsEntity obs:obsIterator) {

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

    public Map<String,LinkedHashMultimap<Long, String>> extractEDIData(List<VisitEntity> visits){

        Map<String,LinkedHashMultimap<Long, String>> ediVisitData;
        if(visits.size() > 0) {
            ediVisitData = new LinkedHashMap<>();
            LinkedHashMultimap<Long, String> ediData = LinkedHashMultimap.create();
            Long ediConceptId = db.conceptDao().getConceptIdByUuid(OpenmrsConfig.CONCEPT_UUID_EDI_IMAGE);

            ObsEntity eiObs = db.obsDao().findByConceptId(ediConceptId);

            for(VisitEntity visit: visits) {
                String visitName = db.visitTypeDao().getVisitTypeById(visit.getVisitTypeId());
                long visitDatetime = visit.getDateStarted().toInstant(ZoneOffset.UTC).getEpochSecond();
                List<ObsEntity> ediObs = db.obsDao().getObsByVisitIdConceptId(visit.getVisitId(),ediConceptId);

                for(ObsEntity obs: ediObs){
                    ediData.put(visitDatetime, obs.getValueText());
                }



                ediVisitData.put(visitName,ediData);
            }
        }else return  null;

        return ediVisitData;
    }

    public LinkedHashMap<Long, Collection<Boolean>> extractReferralData(List<VisitEntity> visits){

        if(visits.size() > 0) {

            LinkedHashMap<Long, Collection<Boolean>> referralResults = new LinkedHashMap<>();
            final long CONCEPT_ID_REASON_FOR_REFERRAL = db.conceptDao().getConceptIdByUuid(OpenmrsConfig.CONCEPT_UUID_REASON_FOR_REFERRAL);

            for(VisitEntity visit: visits) {

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

    public LinkedHashMap<Long, Collection<Boolean>> extractTreatmentData(List<VisitEntity> visits){

        final Long cryoThermolDoneToday = 165174165175L;
        final Long cryoThermolDoneDelayed = 165334176165177L;
        final Long cryoThermolDonePostponed = 165176165177L;

        if(visits.size() > 0) {

            LinkedHashMap<Long, Collection<Boolean>> treatResults = new LinkedHashMap<>();
            final long CONCEPT_ID_VIA_TREATMENT_TYPE_DONE = db.conceptDao().getConceptIdByUuid(OpenmrsConfig.CONCEPT_UUID_VIA_TREATMENT_TYPE_DONE);

            for(VisitEntity visit: visits) {

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

    public void onVisitsRetrieved(List<VisitEntity> visits){

        ConcurrencyUtils.asyncFunction(this::extractScreeningData, getScreeningDataEmitter()::setValue, visits, this::onError);
        ConcurrencyUtils.asyncFunction(this::extractEDIData, getEDIDataEmitter()::setValue, visits, this::onError);
        ConcurrencyUtils.asyncFunction(this::extractReferralData, getReferralDataEmitter()::setValue, visits, this::onError);
        ConcurrencyUtils.asyncFunction(this::extractTreatmentData, getTreatmentDataEmitter()::setValue, visits, this::onError);
        ConcurrencyUtils.asyncFunction(this::extractProviderData, getProviderDataEmitter()::setValue, visits, this::onError);
        ConcurrencyUtils.asyncFunction(this::extractVisitData,getVisitDataEmitter()::setValue, visits, this::onError);
        ConcurrencyUtils.asyncFunction(this::extractObsFilterData,getFilterObsEmitter()::setValue,visits,this::onError);
    }

    public void onError(Throwable throwable){

    }

    public LinkedHashMap<Long, Collection<Map.Entry<String, String>>> extractProviderData(List<VisitEntity> visits){


        if(visits.size() > 0) {

            LinkedHashMap<Long, Collection<Map.Entry<String, String>>> providerResults = new LinkedHashMap<>();

            for(VisitEntity visit: visits) {
                LinkedHashMap<Long, Map.Entry<String, String>> providerData = new LinkedHashMap<>();
                long datatime = visit.getDateStarted().toInstant(ZoneOffset.UTC).getEpochSecond();
                Long treatmentEncounterId = db.genericDao().getPatientEncounterIdByVisitIdEncounterTypeId(person_id,visit.getVisitId(),OpenmrsConfig.ENCOUNTER_TYPE_UUID_TREAMENT);
                Long screeningEncounterId = db.genericDao().getPatientEncounterIdByVisitIdEncounterTypeId(person_id,visit.getVisitId(),OpenmrsConfig.ENCOUNTER_TYPE_UUID_TEST_RESULT);

                if(treatmentEncounterId !=null || screeningEncounterId != null) {

                    Long treatmentProviderId = (treatmentEncounterId != null ) ?
                            db.encounterProviderDao().getByEncounterId(treatmentEncounterId).getProviderId() : null;
                    Long screeningProviderId = (screeningEncounterId != null ) ?
                            db.encounterProviderDao().getByEncounterId(screeningEncounterId).getProviderId() : null;

                    PersonName treatmentProviderName = (treatmentProviderId != null) ?
                            db.personNameDao().getByInsightProviderId(treatmentProviderId) : null;
                    PersonName screeningProviderName = (screeningProviderId != null) ?
                            db.personNameDao().getByInsightProviderId(screeningProviderId) : null;

                    ProviderAttribute treatmentAttribute = (treatmentProviderId != null) ?
                            db.providerAttributeDao().findByProviderId(treatmentProviderId) : null;
                    ProviderAttribute screeningAttribute = (screeningProviderId != null) ?
                            db.providerAttributeDao().findByProviderId(screeningProviderId) : null;

                    // Extract name of provider
                    if (treatmentProviderName != null && treatmentAttribute != null)
                        providerData.put(2L, new AbstractMap.SimpleEntry<>(treatmentAttribute.getValueReference(),
                                treatmentProviderName.getGivenName() + " " + treatmentProviderName.getFamilyName()));
                    else
                        providerData.put(2L, new AbstractMap.SimpleEntry<>("No Number", "N/A"));

                    if (screeningProviderName != null && screeningAttribute != null)
                        providerData.put(1L, new AbstractMap.SimpleEntry<>(screeningAttribute.getValueReference(),
                                screeningProviderName.getGivenName() + " " + screeningProviderName.getFamilyName()));
                    else
                        providerData.put(1L, new AbstractMap.SimpleEntry<>("No Number", "N/A"));


                    providerResults.put(datatime, providerData.values());
                }
            }
            return providerResults;
        }
        return null;
    }

    public LinkedList<LinkedHashMultimap<VisitListItem,VisitEncounterItem>> extractVisitData(List<VisitEntity> visits){

        LinkedList<LinkedHashMultimap<VisitListItem,VisitEncounterItem>> visitListItems = new LinkedList<>();
        for(VisitEntity visit: visits){

            LinkedHashMultimap<VisitListItem,VisitEncounterItem> itemLinkedHashMultimap = LinkedHashMultimap.create();

            VisitListItem visitListItem = new VisitListItem();
            visitListItem.setId(visit.getVisitId());
            visitListItem.setDateTimeStart(visit.getDateStarted());
            visitListItem.setDateTimeStop(visit.getDateStopped());
            visitListItem.setDateCreated(visit.getDateCreated());
            visitListItem.setVisitType(db.visitTypeDao().getVisitTypeById(visit.getVisitTypeId()));

            List<EncounterEntity> visitEncounters = db.encounterDao().getByEncounterByVisitId(visit.getVisitId());

            if(visitEncounters != null && visitEncounters.size() > 0)
                for (EncounterEntity encounter : visitEncounters) {

                    VisitEncounterItem visitEncounterItem = new VisitEncounterItem();
                    visitEncounterItem.setId(encounter.getEncounterId());
                    visitEncounterItem.setEncounterType(db.encounterTypeDao().getEncounterTypeNameById(encounter.getEncounterType()));

                    List<ObsEntity> encounterObs = db.obsDao().getObsByEncounterId(encounter.getEncounterId());
                    for (ObsEntity obs:encounterObs) {
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

    public Map.Entry<List<Long>, Map<Long,Long>>extractObsFilterData(List<VisitEntity> visits){

        List<String>filterConceptIdUuid = new LinkedList<>();
        Map<String, String> substituteConceptIdUuid = new HashMap<>();
        List<Long> filterConceptId;
        Map<Long, Long> substituteConceptIds = new HashMap<>();

        filterConceptIdUuid.add(OpenmrsConfig.CONCEPT_UUID_VIA_INSPECTION_DONE);
        filterConceptIdUuid.add(OpenmrsConfig.CONCEPT_UUID_VIA_SCREENING_RESULT);
        filterConceptIdUuid.add(OpenmrsConfig.CONCEPT_UUID_REFERRAL_REASON);
        filterConceptIdUuid.add(OpenmrsConfig.CONCEPT_UUID_HEALTH_FACILITY_REFERRED);
        filterConceptIdUuid.add(OpenmrsConfig.CONCEPT_UUID_VIA_TREATMENT_PERFORMED);
        filterConceptIdUuid.add(OpenmrsConfig.CONCEPT_UUID_SCREENING_PROVIDER);
        filterConceptIdUuid.add(OpenmrsConfig.CONCEPT_UUID_TREATMENT_PROVIDER);
        filterConceptIdUuid.add(OpenmrsConfig.CONCEPT_UUID_HIV_STATUS);
        filterConceptIdUuid.add(OpenmrsConfig.CONCEPT_UUID_PICT_HIV_RESULT);

        filterConceptId = db.conceptDao().getConceptIdByUuid(filterConceptIdUuid);

        substituteConceptIdUuid.put(OpenmrsConfig.CONCEPT_UUID_HIV_STATUS, OpenmrsConfig.CONCEPT_UUID_PICT_HIV_RESULT);

        for(Map.Entry<String, String> entry: substituteConceptIdUuid.entrySet()){

            Long conceptId = db.conceptDao().getConceptIdByUuid(entry.getKey());
            Long substituteConceptId = db.conceptDao().getConceptIdByUuid(entry.getValue());
            substituteConceptIds.put(conceptId,substituteConceptId);
        }

        return new AbstractMap.SimpleImmutableEntry<>(filterConceptId, substituteConceptIds);
    }
}
