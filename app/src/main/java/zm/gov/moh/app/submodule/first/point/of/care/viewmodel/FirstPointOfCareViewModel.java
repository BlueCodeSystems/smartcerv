package zm.gov.moh.app.submodule.first.point.of.care.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.Intent;

import java.util.EnumMap;

import zm.gov.moh.common.submodule.register.view.RegisterActivity;
import zm.gov.moh.core.model.Submodules;
import zm.gov.moh.cervicalcancer.submodule.cervicalcancer.view.CervicalCancerActivity;
import zm.gov.moh.core.model.submodule.Submodule;
import zm.gov.moh.core.utils.BaseApplication;

public class FirstPointOfCareViewModel extends AndroidViewModel {

    private EnumMap<Submodules,Class> submodules;
    private MutableLiveData<Submodule> startSubmodule;
    private BaseApplication applicationContext;


    public FirstPointOfCareViewModel(Application application){
        super(application);

        applicationContext = getApplication();
        submodules = new EnumMap<>(Submodules.class);
        submodules.put(Submodules.CERVICAL_CANCER, CervicalCancerActivity.class);
        submodules.put(Submodules.FIND_PATIENT, RegisterActivity.class);
    }


    public void startSubmodule(String submodule){

        startSubmodule.setValue(applicationContext.getCareServices().get(0));// getSubmodule(submodule));
    }

    public MutableLiveData<Submodule> getStartSubmodule() {

        startSubmodule = new MutableLiveData<>();
        return startSubmodule;
    }
}

