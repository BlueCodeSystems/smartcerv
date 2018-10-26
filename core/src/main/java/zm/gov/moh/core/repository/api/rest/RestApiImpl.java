package zm.gov.moh.core.repository.api.rest;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import zm.gov.moh.core.model.Authentication;

public class RestApiImpl implements RestApi {

    private RestApiAdapter restAPI;
    private Disposable disposable;
    private String accessToken;

    public RestApiImpl(RestApiAdapter restAPI, final String accessToken) {

        this.restAPI = restAPI;
        this.accessToken = accessToken;
    }

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    public void session(final String credentials, final Consumer<Authentication> success, final Consumer<Throwable> failure) {

           disposable = restAPI.session(credentials)
                    .timeout(30000,TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(success, failure);

    }


    public void onClear(){

    }
}
