package zm.gov.moh.core.util;

import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.Maybe;
import io.reactivex.Single;
import okhttp3.Credentials;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import zm.gov.moh.core.model.AuthInfo;

public class RestAPIImpl  {

    RestAPI restAPI;

    public  RestAPIImpl(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://openmrs.bluecodeltd.com/middleware/rest/")
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

          this.restAPI = retrofit.create(RestAPI.class);
    }

    public Maybe<AuthInfo> session(String credential){

            try {
                return restAPI.authStatus(credential);
            }
            catch (Exception e){
                return Maybe.empty();
            }


            }

    }
