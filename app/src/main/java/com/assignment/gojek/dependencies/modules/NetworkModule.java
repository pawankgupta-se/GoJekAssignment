package com.assignment.gojek.dependencies.modules;

import android.content.Context;

import com.assignment.gojek.BuildConfig;
import com.assignment.gojek.Constants;
import com.assignment.gojek.utils.Utilities;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
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
	@Named("retrofit")
	Retrofit providesRetrofitClientWithCache(Context context) {
		return getClient(context);
	}

	@Singleton
	@Provides
	@Named("retrofit_force_network")
	Retrofit providesRetrofitClient() {
		return getClientWithoutCache();
	}

	private Retrofit getClient(Context context) {
		Cache mCache = new Cache(context.getCacheDir(), Constants.CACHE_SIZE);
		HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
		loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
		OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
		httpClientBuilder.addInterceptor(chain -> {
			Request request = chain.request();
			if (Utilities.isOnline(context)) {
				request.newBuilder().header("Cache-Control", "public, max-age=" + Constants.CACHE_EXPIRE_TIME).build();
			} else {
				request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + Constants.CACHE_EXPIRE_TIME).build();
			}

			return chain.proceed(request);
		});
		httpClientBuilder.cache(mCache);
		httpClientBuilder.addInterceptor(loggingInterceptor);

		return new Retrofit.Builder()
				.client(httpClientBuilder.build())
				.addConverterFactory(GsonConverterFactory.create())
				.baseUrl(BuildConfig.BASE_URL)
				.build();
	}


	private Retrofit getClientWithoutCache() {
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

}
