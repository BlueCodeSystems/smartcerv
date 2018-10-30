package zm.gov.moh.app.submodule.first.point.of.contact.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import zm.gov.moh.core.utils.BaseApplication;
import zm.gov.moh.core.utils.Submodule;

public class FirstPointOfContactViewModel extends AndroidViewModel {

    private MutableLiveData<Submodule> startSubmodule;
    private BaseApplication applicationContext;

    public static String findPatient;
    public static String registerPatient;
    public static String vitals;
    public static String firstPointOfCare;

    public FirstPointOfContactViewModel(Application application){
        super(application);

        applicationContext = (BaseApplication)application;

        findPatient = BaseApplication.CoreSubmodules.FIND_PATIENT;
        registerPatient = BaseApplication.CoreSubmodules.REGISTRATION;
        vitals = BaseApplication.CoreSubmodules.VITALS;
        firstPointOfCare = BaseApplication.CoreSubmodules.FIRST_POINT_OF_CARE;
    }

    public void startSubmodule(String submodule){

        startSubmodule.setValue(applicationContext.getSubmodule(submodule));
    }

    public MutableLiveData<Submodule> getStartSubmodule() {

        startSubmodule = new MutableLiveData<>();
        return startSubmodule;
    }
}

