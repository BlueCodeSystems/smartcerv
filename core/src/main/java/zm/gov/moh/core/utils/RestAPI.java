package zm.gov.moh.core.utils;

import io.reactivex.Maybe;
import retrofit2.http.GET;
import retrofit2.http.Header;
import zm.gov.moh.core.model.Authentication;

public interface RestAPI {

    @GET("session/")
    Maybe<Authentication> session(@Header("Authorization") String credentials);
}
