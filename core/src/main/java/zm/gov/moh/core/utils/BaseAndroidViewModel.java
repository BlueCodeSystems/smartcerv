package zm.gov.moh.core.utils;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import zm.gov.moh.core.repository.api.Repository;


public class BaseAndroidViewModel extends AndroidViewModel implements InjectableViewModel {

    private Repository mRepository;
    private final short PREFERED = 1;

    public BaseAndroidViewModel(Application application){
        super(application);

        InjectorUtils.provideRepository(this, application);
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
}