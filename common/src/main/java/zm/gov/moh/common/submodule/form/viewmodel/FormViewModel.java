package zm.gov.moh.common.submodule.form.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;

import zm.gov.moh.core.repository.database.entity.domain.Location;
import zm.gov.moh.core.utils.BaseAndroidViewModel;
import zm.gov.moh.core.utils.BaseFragment;

public class FormViewModel extends BaseAndroidViewModel {

    MutableLiveData<Location> locationLiveData;
    public FormViewModel(Application application){
        super(application);
    }
}
