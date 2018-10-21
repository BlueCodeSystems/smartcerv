package zm.gov.moh.core.service;

import android.content.Context;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import zm.gov.moh.core.model.Authentication;

public interface RestAPIService {

    public void session(final String credentials, final Consumer<Authentication> success, final Consumer<Throwable> failure);

    void onClear();

}
