package zm.gov.moh.core.utils;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class Provider {

    public static RestAPI getRestAPI() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://openmrs.bluecodeltd.com/middleware/rest/")
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

       return retrofit.create(RestAPI.class);
    }

}
