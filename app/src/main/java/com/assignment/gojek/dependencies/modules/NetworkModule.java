package com.assignment.gojek.dependencies.modules;

import android.os.Debug;

import com.assignment.gojek.BuildConfig;
import com.assignment.gojek.network.ApiUrls;
import com.assignment.gojek.network.services.TrendingRepoService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Pawan Gupta on 19/05/19.
 */
@Module
public class NetworkModule {
    @Singleton
    @Provides
    Retrofit providesRetrofitClient(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.addInterceptor(loggingInterceptor);

        return new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.BASE_URL)
                .build();
    }

    @Singleton
    @Provides
    TrendingRepoService providesTrendingRepoService(Retrofit retrofit){
        return retrofit.create(TrendingRepoService.class);
    }
}
