package zm.gov.moh.common.submodule.form.viewmodel;

import android.app.Application;
import androidx.lifecycle.MutableLiveData;

import zm.gov.moh.core.repository.database.entity.domain.Location;
import zm.gov.moh.core.utils.BaseAndroidViewModel;

public class FormViewModel extends BaseAndroidViewModel {

    MutableLiveData<Location> locationLiveData;
    public FormViewModel(Application application){
        super(application);
    }
}
