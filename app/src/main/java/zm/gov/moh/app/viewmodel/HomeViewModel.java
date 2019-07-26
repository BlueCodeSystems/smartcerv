package zm.gov.moh.app.viewmodel;

import android.app.Application;

import java.util.Map;

import androidx.lifecycle.MutableLiveData;
import zm.gov.moh.core.model.submodule.Module;
import zm.gov.moh.core.utils.BaseAndroidViewModel;
import zm.gov.moh.core.utils.BaseApplication;

public class HomeViewModel extends BaseAndroidViewModel {

    private MutableLiveData<Module> startSubmodule;
    private MutableLiveData<Map<String,Long>> metricsEmitter;


    public HomeViewModel(Application application){
        super(application);

    }
    public MutableLiveData<Module> getStartSubmodule() {

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
                    metrics.put("count",value);


    }

    public void onMetricsRetrieved(Map<String,Long> metrics){


    }*/
}

