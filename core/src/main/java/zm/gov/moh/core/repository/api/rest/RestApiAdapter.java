package zm.gov.moh.core.repository.api.rest;

import io.reactivex.Maybe;
import retrofit2.http.GET;
import retrofit2.http.Header;
import zm.gov.moh.core.model.Authentication;

public interface RestApiAdapter {

    @GET("session/")
    Maybe<Authentication> session(@Header("Authorization") String credentials);
}