package zm.gov.moh.common.submodule.vitals.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import zm.gov.moh.core.utils.BaseAndroidViewModel;
import zm.gov.moh.core.utils.InjectableViewModel;

public class VitalsViewModel extends BaseAndroidViewModel implements InjectableViewModel {


    public VitalsViewModel(Application application){
        super(application);

    }
}