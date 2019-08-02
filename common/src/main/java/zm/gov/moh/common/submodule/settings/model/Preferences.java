package zm.gov.moh.common.submodule.settings.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import zm.gov.moh.common.BR;
import zm.gov.moh.core.BuildConfig;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.api.Repository;

public class Preferences extends BaseObservable {

    private CharSequence baseUrl;

    public void setBaseUrl(CharSequence baseUrl) {

        if(this.baseUrl != baseUrl) {

            this.baseUrl = baseUrl;
            notifyPropertyChanged(BR.baseUrl);
        }
    }

    @Bindable
    public CharSequence getBaseUrl() {
        return baseUrl;
    }

    public Preferences(Repository repository){
        this.setBaseUrl(repository.getDefaultSharePrefrences().getString(Key.BASE_URL, BuildConfig.BASE_URL));
    }
}