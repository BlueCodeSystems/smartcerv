package zm.gov.moh.core.repository.api;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.AbstractMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import zm.gov.moh.core.BuildConfig;
import zm.gov.moh.core.R;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.api.rest.RestApi;
import zm.gov.moh.core.repository.database.Database;
import zm.gov.moh.core.utils.InjectorUtils;

public class RepositoryImp implements Repository{

    private Database database;
    private RestApi restApi;
    private Context application;


    public RepositoryImp(Context application){

        this.application = application;
        this.database = Database.getDatabase(application);

        this.restApi = InjectorUtils.provideRestAPIAdapter(application);
    }


    public Database getDatabase() {
        return database;
    }

    @Override
    public RestApi getRestApi() {
        return restApi;
    }

    @Override
    public SharedPreferences getDefaultSharePrefrences() {

        return application.getSharedPreferences(
                application.getResources().getString(R.string.application_shared_prefernce_key),
                Context.MODE_PRIVATE);
    }
}