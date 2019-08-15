package zm.gov.moh.common.submodule.visit.viewmodel;

import android.app.Application;
import android.os.Bundle;

import org.threeten.bp.LocalDateTime;

import androidx.lifecycle.MutableLiveData;
import zm.gov.moh.core.model.VisitState;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.database.DatabaseUtils;
import zm.gov.moh.core.repository.database.entity.domain.VisitEntity;
import zm.gov.moh.core.utils.BaseAndroidViewModel;
import zm.gov.moh.core.utils.ConcurrencyUtils;
import zm.gov.moh.core.utils.InjectableViewModel;

public class VisitViewModel extends BaseAndroidViewModel implements InjectableViewModel {


    MutableLiveData<Bundle> viewState;
    public VisitViewModel(Application application){
        super(application);

        viewState = new MutableLiveData<>();
    }

    public MutableLiveData<Bundle> getViewState() {
        return viewState;
    }

    public void onClick(){

        VisitState visitState = (VisitState) mBundle.getSerializable(Key.VISIT_STATE);

        if(visitState == VisitState.AMEND || visitState == VisitState.SESSION)
            mBundle.putSerializable(Key.VISIT_STATE, VisitState.END);
        else if(visitState == VisitState.NEW) {
            mBundle.putSerializable(Key.VISIT_STATE, VisitState.SESSION);
        }

        viewState.setValue(mBundle);
    }

    public void createVisit(){

        ConcurrencyUtils.asyncRunnable(() -> {

            long visitId = DatabaseUtils.generateLocalId(getRepository().getDatabase().visitDao()::getMaxId);
            long visitTypeId = (Long) mBundle.get(Key.VISIT_TYPE_ID);
            long locationId = mRepository.getDefaultSharePrefrences().getLong(Key.LOCATION_ID,0);
            long creator = mRepository.getDefaultSharePrefrences().getLong(Key.USER_ID,0);
            long providerId = mRepository.getDefaultSharePrefrences().getLong(Key.PROVIDER_ID,0);
            long personId = (Long) mBundle.get(Key.PERSON_ID);
            LocalDateTime start_time = LocalDateTime.now();

            db.visitDao().insert(new VisitEntity(visitId,visitTypeId,personId,locationId,creator,start_time));
            mBundle.putLong(Key.VISIT_ID, visitId);
            mBundle.putLong(Key.PROVIDER_ID, providerId);
        }, this::onError);

    }

    public void endVisit(){

        ConcurrencyUtils.asyncRunnable(() -> {

            LocalDateTime endTime = LocalDateTime.now();
            long visitId = mBundle.getLong(Key.VISIT_ID);
            VisitEntity visitEntity = db.visitDao().getById(visitId);
            visitEntity.setDateStopped(endTime);
            db.visitDao().insert(visitEntity);
        }, this::onError);

    }
}

