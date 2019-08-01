package zm.gov.moh.core.utils;

import android.app.Application;
import android.os.Bundle;

import androidx.lifecycle.AndroidViewModel;
import zm.gov.moh.core.repository.api.Repository;
import zm.gov.moh.core.repository.database.Database;


public class BaseAndroidViewModel extends AndroidViewModel implements InjectableViewModel {

    private Repository mRepository;
    private final short PREFERED = 1;
    private final String LOCALE_EN = "en";
    private Bundle bundle;
    protected Application application;
    protected Database db;

    public BaseAndroidViewModel(Application application){
        super(application);
        this.application = application;
        InjectorUtils.provideRepository(this, application);
        db = mRepository.getDatabase();
    }

    @Override
    public void setRepository(Repository repository) {
        this.mRepository = repository;
    }

    public Repository getRepository() {
        return mRepository;
    }

    public short preffered() {
        return PREFERED;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public String getLOCALE_EN() {
        return LOCALE_EN;
    }
}