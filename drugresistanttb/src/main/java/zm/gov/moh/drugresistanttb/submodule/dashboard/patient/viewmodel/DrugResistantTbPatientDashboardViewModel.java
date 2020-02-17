package zm.gov.moh.drugresistanttb.submodule.dashboard.patient.viewmodel;


import android.app.Application;
import android.os.Bundle;

import com.google.common.collect.LinkedHashMultimap;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import androidx.lifecycle.MutableLiveData;

import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.api.Repository;
import zm.gov.moh.core.repository.database.Database;
import zm.gov.moh.core.repository.database.dao.domain.VisitDao;
import zm.gov.moh.core.repository.database.entity.domain.EncounterEntity;
import zm.gov.moh.core.repository.database.entity.domain.ObsEntity;
import zm.gov.moh.core.repository.database.entity.domain.VisitEntity;
import zm.gov.moh.core.utils.BaseAndroidViewModel;
import zm.gov.moh.core.utils.ConcurrencyUtils;
import zm.gov.moh.core.utils.InjectableViewModel;
import zm.gov.moh.drugresistanttb.OpenmrsConfig;
import zm.gov.moh.drugresistanttb.submodule.dashboard.patient.model.ObsListItem;
import zm.gov.moh.drugresistanttb.submodule.dashboard.patient.model.VisitEncounterItem;
import zm.gov.moh.drugresistanttb.submodule.dashboard.patient.model.VisitListItem;

public class DrugResistantTbPatientDashboardViewModel extends BaseAndroidViewModel implements InjectableViewModel {
    private Bundle bundle;
    long person_id;

    private MutableLiveData<Map.Entry<List<Long>, Map<Long, Long>>> filterObsEmitter;
    private VisitDao visitDao = getRepository().getDatabase().visitDao();
    private Database db = getRepository().getDatabase();
    private MutableLiveData<LinkedList<LinkedHashMultimap<VisitListItem, VisitEncounterItem>>> visitDataEmitter;

    public DrugResistantTbPatientDashboardViewModel(Application application) {

        super(application);
    }

    public Repository getRepository() {
        return mRepository;
    }

    public MutableLiveData<LinkedList<LinkedHashMultimap<VisitListItem, VisitEncounterItem>>> getVisitDataEmitter() {

        if (visitDataEmitter == null)
            visitDataEmitter = new MutableLiveData<>();

        return visitDataEmitter;
    }

    public MutableLiveData<Map.Entry<List<Long>, Map<Long, Long>>> getFilterObsEmitter() {

        if (filterObsEmitter == null)
            filterObsEmitter = new MutableLiveData<>();

        return filterObsEmitter;
    }


    public void setRepository(Repository repository) {
        mRepository = repository;
    }

    @Override
    public Bundle getBundle() {
        return bundle;
    }

    @Override
    public void setBundle(Bundle bundle) {
        this.bundle = bundle;

        person_id = (Long) bundle.get(Key.PERSON_ID);
    }

    public void onVisitsRetrieved(List<VisitEntity> visits) {


        ConcurrencyUtils.asyncFunction(this::extractVisitData, getVisitDataEmitter()::setValue, visits, this::onError);
        ConcurrencyUtils.asyncFunction(this::extractObsFilterData, getFilterObsEmitter()::setValue, visits, this::onError);
    }

    public void onError(Throwable throwable) {

    }

    public LinkedList<LinkedHashMultimap<VisitListItem, VisitEncounterItem>> extractVisitData(List<VisitEntity> visits) {

        LinkedList<LinkedHashMultimap<VisitListItem, VisitEncounterItem>> visitListItems = new LinkedList<>();
        for (VisitEntity visit : visits) {

            LinkedHashMultimap<VisitListItem, VisitEncounterItem> itemLinkedHashMultimap = LinkedHashMultimap.create();

            VisitListItem visitListItem = new VisitListItem();
            visitListItem.setId(visit.getVisitId());
            visitListItem.setDateTimeStart(visit.getDateStarted());
            visitListItem.setDateTimeStop(visit.getDateStopped());
            visitListItem.setDateCreated(visit.getDateCreated());
            visitListItem.setVisitType(db.visitTypeDao().getVisitTypeById(visit.getVisitTypeId()));

            List<EncounterEntity> visitEncounters = db.encounterDao().getByEncounterByVisitId(visit.getVisitId());

            if (visitEncounters != null && visitEncounters.size() > 0)
                for (EncounterEntity encounter : visitEncounters) {

                    VisitEncounterItem visitEncounterItem = new VisitEncounterItem();
                    visitEncounterItem.setId(encounter.getEncounterId());
                    visitEncounterItem.setEncounterType(db.encounterTypeDao().getEncounterTypeNameById(encounter.getEncounterType()));

                    List<ObsEntity> encounterObs = db.obsDao().getObsByEncounterId(encounter.getEncounterId());
                    for (ObsEntity obs : encounterObs) {
                        ObsListItem obsListItem = new ObsListItem();
                        obsListItem.setId(obs.getObsId());
                        obsListItem.setConceptId(obs.getConceptId());
                        obsListItem.setConceptName(db.conceptNameDao().getConceptNameByConceptId(obs.getConceptId(), getLOCALE_EN(), preffered()));

                        if (obs.getValueCoded() != null)
                            obsListItem.setObsValue(db.conceptNameDao().getConceptNameByConceptId(obs.getValueCoded(), getLOCALE_EN(), preffered()));
                        else if (obs.getValueText() != null)
                            obsListItem.setObsValue(obs.getValueText());
                        else if (obs.getValueNumeric() != null)
                            obsListItem.setObsValue(obs.getValueNumeric().toString());
                        else if (obs.getValueDateTime() != null)
                            obsListItem.setObsValue(obs.getValueDateTime().toString());

                        visitEncounterItem.getObsListItems().add(obsListItem);
                        visitEncounterItem.getId();
                    }

                    itemLinkedHashMultimap.put(visitListItem, visitEncounterItem);
                }
            else continue;

            visitListItems.add(itemLinkedHashMultimap);

        }

        return visitListItems;
    }

    public Map.Entry<List<Long>, Map<Long, Long>> extractObsFilterData(List<VisitEntity> visits) {

        List<String> filterConceptIdUuid = new LinkedList<>();
        Map<String, String> substituteConceptIdUuid = new HashMap<>();
        List<Long> filterConceptId;
        Map<Long, Long> substituteConceptIds = new HashMap<>();

        filterConceptIdUuid.add(OpenmrsConfig.CONCEPT_UUID_SMEAR_MICROSCOPY_RESULTS);
        filterConceptIdUuid.add(OpenmrsConfig.CONCEPT_UUID_CULTURE_RESULTS);

        filterConceptIdUuid.add(OpenmrsConfig.CONCEPT_UUID_TESTS_PERFORMED);
        filterConceptIdUuid.add(OpenmrsConfig.CONCEPT_UUID_PRESUMPTIVE_TB);
        filterConceptIdUuid.add(OpenmrsConfig.CONCEPT_UUID_RIFAMPICIN_RESISTANT);


        filterConceptIdUuid.add(OpenmrsConfig.CONCEPT_UUID_DRUG_RESISTANCE_TYPE);
        filterConceptIdUuid.add(OpenmrsConfig.CONCEPT_UUID_SITE_OF_DISEASE);


        filterConceptId = db.conceptDao().getConceptIdByUuid(filterConceptIdUuid);

        //substituteConceptIdUuid.put(OpenmrsConfig.VISIT_TYPE_UUID_MDR_MONTHLY_REVIEW_FORM, OpenmrsConfig.VISIT_TYPE_UUID_MDR_MONTHLY_REVIEW_FORM);

        for (Map.Entry<String, String> entry : substituteConceptIdUuid.entrySet()) {

            Long conceptId = db.conceptDao().getConceptIdByUuid(entry.getKey());
            Long substituteConceptId = db.conceptDao().getConceptIdByUuid(entry.getValue());
            substituteConceptIds.put(conceptId, substituteConceptId);
        }

        return new AbstractMap.SimpleImmutableEntry<>(filterConceptId, substituteConceptIds);
    }
}



