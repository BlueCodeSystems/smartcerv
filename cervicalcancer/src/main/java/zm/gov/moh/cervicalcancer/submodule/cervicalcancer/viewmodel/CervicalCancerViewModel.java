package zm.gov.moh.cervicalcancer.submodule.cervicalcancer.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;

import java.util.EnumMap;

import zm.gov.moh.cervicalcancer.submodule.enrollment.view.CervicalCancerEnrollmentActivity;
import zm.gov.moh.core.model.Submodules;
import zm.gov.moh.core.model.submodule.Submodule;
import zm.gov.moh.core.model.submodule.SubmoduleGroup;
import zm.gov.moh.core.utils.BaseAndroidViewModel;
import zm.gov.moh.core.utils.BaseApplication;

public class CervicalCancerViewModel extends BaseAndroidViewModel {

    EnumMap<Submodules, Class> submodules;
    Context context;
    MutableLiveData<Submodule> startSubmodule;

    public CervicalCancerViewModel(Application application){
        super(application);

        submodules = new EnumMap<>(Submodules.class);
        context = application;
        submodules.put(Submodules.REGISTRATION, CervicalCancerEnrollmentActivity.class);
    }

    public void startSubmodule(int index){

        SubmoduleGroup cervicalCancerSubmodule = (SubmoduleGroup)((BaseApplication)getApplication()).getSubmodule(BaseApplication.CareSubmodules.CERVICAL_CANCER);

        Submodule submodule1 = cervicalCancerSubmodule.getSubmodules().get(index);
        startSubmodule.setValue(submodule1);
    }

    public LiveData<Submodule> getStartSubmodule() {

        if(startSubmodule == null)
            startSubmodule = new MutableLiveData<>();

        return startSubmodule;
    }

    public void startSubmodule(Submodule submodule){
        startSubmodule.setValue(submodule);
    }
}

/*
*   private Repository mRepository;
    private MutableLiveData<Submodule> startSubmodule;

    public BaseAndroidViewModel(Application application){
        super(application);

        InjectorUtils.provideRepository(this, application);
        startSubmodule = new MutableLiveData<>();
    }

    @Override
    public void setRepository(Repository repository) {
        this.mRepository = repository;
    }

    public Repository getRepository() {
        return mRepository;
    }

    public LiveData<Submodule> getStartSubmodule() {

        if(startSubmodule == null)
            startSubmodule = new MutableLiveData<>();

        return startSubmodule;
    }

    public void startSubmodule(Submodule submodule){
        startSubmodule.setValue(submodule);
    }
* **/

