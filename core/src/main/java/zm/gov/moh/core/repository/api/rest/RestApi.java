package zm.gov.moh.core.repository.api.rest;

import io.reactivex.functions.Consumer;
import zm.gov.moh.core.model.Authentication;

public interface RestApi {

    public void session(final String credentials, final Consumer<Authentication> success, final Consumer<Throwable> failure);

    String getAccessToken();

    void onClear();

}
