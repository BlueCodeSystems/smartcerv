package zm.gov.moh.core.utils;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import zm.gov.moh.core.repository.api.Repository;


public class BaseAndroidViewModel extends AndroidViewModel implements InjectableViewModel {

    private Repository mRepository;

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
}