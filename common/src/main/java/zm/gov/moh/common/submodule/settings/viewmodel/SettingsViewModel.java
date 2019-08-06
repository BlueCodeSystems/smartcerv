package zm.gov.moh.common.submodule.settings.viewmodel;

import android.app.Application;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

import androidx.lifecycle.MutableLiveData;
import zm.gov.moh.common.submodule.settings.model.Preferences;
import zm.gov.moh.core.BuildConfig;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.utils.BaseAndroidViewModel;
import zm.gov.moh.core.utils.ConcurrencyUtils;

public class SettingsViewModel extends BaseAndroidViewModel {

    private MutableLiveData<Boolean> preferencesSaveStatus;
    public SettingsViewModel(Application application){
        super(application);
    }

    public void savePreferences(Preferences preferences){

        if(preferences.getBaseUrl() != null && preferences.getBaseUrl() != "" ) {

            String baseUrl = preferences.getBaseUrl().toString();
            try {
                new URL(baseUrl);
            }catch (MalformedURLException exception){
                Toast.makeText(application,"Malformed URL",Toast.LENGTH_LONG).show();
                return;
            }

            getRepository().getDefaultSharePrefrences().edit().putString(Key.BASE_URL, preferences.getBaseUrl().toString()).apply();
            ConcurrencyUtils.asyncRunnable(()-> db.clearAllTables(), this::onError);
        }

        getPreferencesSaveStatus().setValue(true);

    }

    public MutableLiveData<Boolean> getPreferencesSaveStatus() {

        if(preferencesSaveStatus == null)
            preferencesSaveStatus = new MutableLiveData<>();
        return preferencesSaveStatus;
    }
}
