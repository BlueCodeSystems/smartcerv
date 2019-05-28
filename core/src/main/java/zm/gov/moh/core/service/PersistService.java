package zm.gov.moh.core.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.jakewharton.threetenabp.AndroidThreeTen;

import androidx.annotation.Nullable;
import zm.gov.moh.core.repository.api.Repository;
import zm.gov.moh.core.utils.InjectableViewModel;
import zm.gov.moh.core.utils.InjectorUtils;

public abstract class PersistService extends IntentService implements InjectableViewModel {

    private Repository repository;
    protected final short PREFERRED = 1;
    protected final String MID_DAY_TIME = "T12:00:00Z";

    public PersistService(String name){
        super(name);

    }

    protected void onHandleIntent(@Nullable Intent intent) {

        InjectorUtils.provideRepository(this, getApplication());
        Bundle bundle = intent.getExtras();
         if(bundle == null)
             bundle = new Bundle();
        AndroidThreeTen.init(this);


        repository.consumeAsync(this::persistAsync,this::onError,bundle);
    }

    public abstract void persistAsync(Bundle bundle);

    @Override
    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    @Override
    public Repository getRepository() {
        return repository;
    }

    public abstract void onError(Throwable throwable);
}
