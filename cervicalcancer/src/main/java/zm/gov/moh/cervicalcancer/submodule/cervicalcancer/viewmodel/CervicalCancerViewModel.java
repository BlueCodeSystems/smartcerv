package zm.gov.moh.cervicalcancer.submodule.cervicalcancer.viewmodel;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import android.content.Context;

import java.util.EnumMap;

import zm.gov.moh.cervicalcancer.CervicalCancerModule;
import zm.gov.moh.cervicalcancer.submodule.enrollment.view.CervicalCancerEnrollmentActivity;
import zm.gov.moh.core.model.Submodules;
import zm.gov.moh.core.model.submodule.Module;
import zm.gov.moh.core.model.submodule.ModuleGroup;
import zm.gov.moh.core.utils.BaseAndroidViewModel;
import zm.gov.moh.core.utils.BaseApplication;

public class CervicalCancerViewModel extends BaseAndroidViewModel {

    EnumMap<Submodules, Class> submodules;
    Context context;
    MutableLiveData<Module> startSubmodule;

    public CervicalCancerViewModel(Application application){
        super(application);

        submodules = new EnumMap<>(Submodules.class);
        context = application;
        submodules.put(Submodules.REGISTRATION, CervicalCancerEnrollmentActivity.class);
    }

    public void startSubmodule(int index){

        ModuleGroup cervicalCancerSubmodule = (ModuleGroup)((BaseApplication)getApplication()).getModule(CervicalCancerModule.MODULE);

        Module module1 = cervicalCancerSubmodule.getModules().get(index);
        startSubmodule.setValue(module1);
    }

    public LiveData<Module> getStartSubmodule() {

        if(startSubmodule == null)
            startSubmodule = new MutableLiveData<>();

        return startSubmodule;
    }

    public void startSubmodule(Module module){
        startSubmodule.setValue(module);
    }
}

/*
*   private Repository mRepository;
    private MutableLiveData<Module> startModule;

    public BaseAndroidViewModel(Application application){
        super(application);

        InjectorUtils.provideRepository(this, application);
        startModule = new MutableLiveData<>();
    }

    @Override
    public void setRepository(Repository repository) {
        this.mRepository = repository;
    }

    public Repository getRepository() {
        return mRepository;
    }

    public LiveData<Module> getStartSubmodule() {

        if(startModule == null)
            startModule = new MutableLiveData<>();

        return startModule;
    }

    public void startModule(Module submodule){
        startModule.setValue(submodule);
    }
* **/

