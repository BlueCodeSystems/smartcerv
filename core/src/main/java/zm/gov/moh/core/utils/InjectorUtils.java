package zm.gov.moh.core.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.squareup.moshi.Moshi;

import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import zm.gov.moh.core.BuildConfig;
import zm.gov.moh.core.R;
import zm.gov.moh.core.model.Key;
import zm.gov.moh.core.repository.api.RepositoryImp;
import zm.gov.moh.core.repository.api.rest.RestApi;

public class InjectorUtils {


    public static void provideRepository(InjectableViewModel injectableViewModel, Context application){

        injectableViewModel.setRepository(new RepositoryImp(application));
    }

    public static RestApi provideRestAPIAdapter(Context application) {

        Moshi moshi = new Moshi.Builder().add(new JsonAdapter()).build();

        SharedPreferences sharedPreferences = application.getSharedPreferences(
                application.getResources().getString(R.string.application_shared_prefernce_key),
                Context.MODE_PRIVATE);

        String baseUrl = sharedPreferences.getString(Key.BASE_URL, BuildConfig.BASE_URL);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl+ BuildConfig.BASE_ROUTE)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return  retrofit.create(RestApi.class);
    }




    }
