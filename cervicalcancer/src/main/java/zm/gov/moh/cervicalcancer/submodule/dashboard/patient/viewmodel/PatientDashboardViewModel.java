package zm.gov.moh.cervicalcancer.submodule.dashboard.patient.viewmodel;

import android.app.Application;

import org.threeten.bp.ZonedDateTime;

import java.util.HashMap;

import androidx.lifecycle.MutableLiveData;
import zm.gov.moh.cervicalcancer.submodule.dashboard.patient.model.VisitState;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.database.DatabaseUtils;
import zm.gov.moh.core.repository.database.dao.domain.VisitDao;
import zm.gov.moh.core.repository.database.entity.domain.Visit;
import zm.gov.moh.core.utils.BaseAndroidViewModel;
import zm.gov.moh.core.utils.InjectableViewModel;

public class PatientDashboardViewModel extends BaseAndroidViewModel implements InjectableViewModel {

    private MutableLiveData<Integer> emitVisitState;
    private HashMap<String, Object> formState;
    private VisitState visitState;
    private Visit visit;

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

    public void setFormState(HashMap<String, Object> formState) {
        this.formState = formState;
    }

    public HashMap<String, Object> getFormState() {

        if(formState == null)
            formState = new HashMap<>();

        return formState;
    }

    public void setVisitState(VisitState visitState) {
        this.visitState = visitState;
    }

    public VisitState getVisitState() {
        return visitState;
    }

    public void persistVisit(int visitState){

        VisitDao visitDao = getRepository().getDatabase().visitDao();
        if(visit == null)
            visit = new Visit();

        if(visitState == VisitState.STARTED) {

            getRepository().consumeAsync(

                    formState -> {

                        long visit_id = DatabaseUtils.generateLocalId(getRepository().getDatabase().visitDao()::getMaxId);
                        long visit_type_id = (Long) formState.get(Key.VISIT_TYPE_ID);
                        long person_id = (Long) formState.get(Key.PERSON_ID);
                        long location_id = (Long) formState.get(Key.LOCATION_ID);
                        long creator = (Long) formState.get(Key.USER_ID);
                        ZonedDateTime start_time = ZonedDateTime.now();
                        visit = new Visit(visit_id,visit_type_id,person_id,location_id,creator,start_time);
                        visitDao.insert(visit);
                        formState.put(Key.VISIT_ID, visit_id);;
                    },
                    this::onError,
                    formState);
            }
            else if(visit.getDate_started() != null) {

                visit.setDate_stopped(ZonedDateTime.now());
                getRepository().consumeAsync( visit-> {

                            visitDao.updateVisit(visit);
                },
                        this::onError,
                        visit);

            }

    }

    public void onError(Throwable throwable){

    }
}
