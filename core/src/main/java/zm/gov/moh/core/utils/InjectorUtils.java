package zm.gov.moh.core.utils;

import android.app.Application;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.squareup.moshi.Moshi;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import zm.gov.moh.core.repository.api.RepositoryImp;
import zm.gov.moh.core.repository.api.rest.RestApi;

public class InjectorUtils {


    public static void provideRepository(InjectableViewModel injectableViewModel, Application application){

        injectableViewModel.setRepository(new RepositoryImp(application));
    }

    public static RestApi provideRestAPIAdapter() {

        Moshi moshi = new Moshi.Builder().add(new JsonAdapter()).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://smartcerv.bluecodeltd.com/middleware/rest/")
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return  retrofit.create(RestApi.class);
    }


    }
