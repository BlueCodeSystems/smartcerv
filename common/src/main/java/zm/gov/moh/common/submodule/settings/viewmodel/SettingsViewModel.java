package zm.gov.moh.common.submodule.settings.viewmodel;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;


import java.net.MalformedURLException;
import java.net.URL;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.MutableLiveData;
import zm.gov.moh.common.submodule.settings.model.Preferences;
import zm.gov.moh.core.Constant;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.database.entity.system.EntityType;
import zm.gov.moh.core.service.worker.RemoteWorker;
import zm.gov.moh.core.utils.BaseAndroidViewModel;
import zm.gov.moh.core.utils.ConcurrencyUtils;

public class SettingsViewModel extends BaseAndroidViewModel {

    private MutableLiveData<Preferences> preferencesStream;
    private MutableLiveData<Boolean> preferencesSaveStatusStream;
    private MutableLiveData<Boolean> progressIndicatorVisibilityStatusStream;
    private MutableLiveData<Boolean> promptSaveDialogVisibilityStream;


    public SettingsViewModel(Application application){
        super(application);
    }

    public void savePreferences(Preferences preferences){

        getProgressIndicatorVisibilityStatusStream().setValue(true);

        String currentBaseUrl = getRepository().getDefaultSharePrefrences().getString(Key.BASE_URL,null);
        //Base URL
        if(preferences.getBaseUrl() != null && preferences.getBaseUrl() != "" ) {

            String newBaseUrl = preferences.getBaseUrl().toString();
            try {
                new URL(newBaseUrl);
            }catch (MalformedURLException exception){
                Toast.makeText(application,"Malformed URL",Toast.LENGTH_LONG).show();
                return;
            }

            if(!newBaseUrl.equals(currentBaseUrl))
                ConcurrencyUtils.asyncFunction(this::checkSynchronizationStatus, this::saveBaseUrl, preferences, this::onError);
            else
                getPreferencesSaveStatusStream().setValue(true);
        }

    }

    public Preferences checkSynchronizationStatus(Preferences preferences){

        if(isSynchronizationComplete())
            return preferences;
        else{

            preferences.setPromptToSave(true);
            getPreferencesStream().postValue(preferences);
            return preferences;
        }
    }

    public MutableLiveData<Preferences> getPreferencesStream() {

        if(preferencesStream == null)
            preferencesStream = new MutableLiveData<>();

        return preferencesStream;
    }

    public boolean isSynchronizationComplete(){

        final long offset = Constant.LOCAL_ENTITY_ID_OFFSET;
        long[] pushedPatientEntityId = db.entityMetadataDao().findEntityIdByTypeRemoteStatus(EntityType.PATIENT.getId(), RemoteWorker.Status.PUSHED.getCode());
        long[] pushedVisitEntityId = db.entityMetadataDao().findEntityIdByTypeRemoteStatus(EntityType.VISIT.getId(), RemoteWorker.Status.PUSHED.getCode());


        Long[] notPushedPatientEntityId = db.patientDao().findEntityNotWithId(offset, pushedPatientEntityId);
        Long[] notPushedVisitEntityId = db.visitDao().findEntityNotWithId(offset, pushedVisitEntityId);

       return (notPushedPatientEntityId.length > 0 || notPushedVisitEntityId.length > 0)? false : true;
    }

    public void saveBaseUrl(Preferences preferences){

        if(preferences.isPromptToSave()){

            getProgressIndicatorVisibilityStatusStream().setValue(false);
            getPromptSaveDialogVisibilityStream().setValue(true);
            return;
        }

        getRepository().getDefaultSharePrefrences().edit().clear().apply();
        ConcurrencyUtils.asyncRunnable(() -> db.clearAllTables(), this::onError);
        getRepository().getDefaultSharePrefrences().edit().putString(Key.BASE_URL, preferences.getBaseUrl().toString()).apply();

        getProgressIndicatorVisibilityStatusStream().setValue(false);
        getPreferencesSaveStatusStream().setValue(true);
    }

    public MutableLiveData<Boolean> getProgressIndicatorVisibilityStatusStream() {

        if(progressIndicatorVisibilityStatusStream == null)
            progressIndicatorVisibilityStatusStream = new MutableLiveData<>();
        return progressIndicatorVisibilityStatusStream;
    }

    public MutableLiveData<Boolean> getPromptSaveDialogVisibilityStream() {

        if(promptSaveDialogVisibilityStream == null)
           promptSaveDialogVisibilityStream = new MutableLiveData<>();

        return promptSaveDialogVisibilityStream;
    }

    public MutableLiveData<Boolean> getPreferencesSaveStatusStream() {

        if(preferencesSaveStatusStream == null)
            preferencesSaveStatusStream = new MutableLiveData<>();
        return preferencesSaveStatusStream;
    }
}
