package zm.gov.moh.core.util;

import io.reactivex.Maybe;
import retrofit2.http.GET;
import retrofit2.http.Header;
import zm.gov.moh.core.model.AuthInfo;

public interface RestAPI {

    @GET("session/")
    Maybe<AuthInfo> authStatus(@Header("Authorization") String credentials);
}
