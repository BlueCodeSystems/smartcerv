package zm.gov.moh.core.service;

import android.content.Context;

import io.reactivex.functions.Function;
import okhttp3.Credentials;
import zm.gov.moh.core.model.Authentication;

public interface RestAPIService {

    void session(Context context, String credentials, Function<Authentication,Void> success);
}
