package zm.gov.moh.app.viewmodel;

import android.app.Application;

import java.util.Map;

import androidx.lifecycle.MutableLiveData;
import zm.gov.moh.core.utils.BaseAndroidViewModel;
import zm.gov.moh.core.utils.BaseApplication;
import zm.gov.moh.core.model.submodule.Submodule;

public class FirstPointOfContactViewModel extends BaseAndroidViewModel {

    private MutableLiveData<Submodule> startSubmodule;
    private BaseApplication applicationContext;
    private MutableLiveData<Map<String,Long>> metricsEmitter;

    public static String findPatient;
    public static String registerPatient;
    public static String vitals;
    public static String firstPointOfCare;

    public FirstPointOfContactViewModel(Application application){
        super(application);

        applicationContext = (BaseApplication)application;

        findPatient = BaseApplication.CoreModule.REGISTER;
        registerPatient = BaseApplication.CoreModule.REGISTRATION;
        vitals = BaseApplication.CoreModule.VITALS;
        firstPointOfCare = BaseApplication.CoreModule.FIRST_POINT_OF_CARE;
    }

    public void startSubmodule(String submodule){

        startSubmodule.setValue(applicationContext.getSubmodule(submodule));
    }

    public MutableLiveData<Submodule> getStartSubmodule() {

        startSubmodule = new MutableLiveData<>();
        return startSubmodule;
    }

    public MutableLiveData<Map<String, Long>> getMetricsEmitter() {

        if(metricsEmitter == null)
            metricsEmitter = new MutableLiveData<>();
        return metricsEmitter;
    }

  /*  public Map<String,Long> getMetrics(Repository repository){

        Database db = repository.getDatabase();
        final long locationId = repository.getDefaultSharePrefrences()
                .getLong(getApplication().getResources().getString(zm.gov.moh.core.R.string.session_location_key), 1);

        Map<String,Long> metrics = new HashMap<>();

        Long cervicalCancerRegisteredPatients = db.metricsDao().countClientsByLocationPatientIdType(locationId,3);

        re.getDatabase().genericDao().countPatientsByLocationId(locationId)
                .observe(this,value -> {
                    metrics.put("total",value);


    }

    public void onMetricsRetrieved(Map<String,Long> metrics){


    }*/
}

