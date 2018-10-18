package zm.gov.moh.core.util;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.Maybe;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import zm.gov.moh.core.model.AuthInfo;

public class RestAPIImpl {

    RestAPI restAPI;

    public RestAPIImpl() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://openmrs.bluecodeltd.com/middleware/rest/")
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        this.restAPI = retrofit.create(RestAPI.class);
    }

    public Maybe<AuthInfo> session(String credential) {

        return restAPI.session(credential);
    }
}
