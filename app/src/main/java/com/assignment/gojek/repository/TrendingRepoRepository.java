package com.assignment.gojek.repository;

import android.support.annotation.WorkerThread;

import com.assignment.gojek.BuildConfig;
import com.assignment.gojek.models.GitRepo;
import com.assignment.gojek.network.services.TrendingRepoService;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.inject.Named;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Pawan Gupta on 19/05/19.
 */
public class TrendingRepoRepository {
	private Retrofit mRetrofit;
	private Retrofit mRetrofitForceNetwork;

	public TrendingRepoRepository(@Named("retrofit") Retrofit retrofit, @Named("retrofit_force_network") Retrofit retrofitForceNetwork) {
		mRetrofit = retrofit;
		mRetrofitForceNetwork = retrofitForceNetwork;
	}

	@WorkerThread
	public List<GitRepo> getTrendingRepo() throws IOException {
		Response<List<GitRepo>> response = mRetrofit.create(TrendingRepoService.class).fetchTrendingRepo().execute();
		if (response.isSuccessful()) {
			return response.body();
		}
		return Collections.emptyList();
	}


	@WorkerThread
	public List<GitRepo> refreshTrendingRepo() throws IOException {
		TrendingRepoService service = mRetrofitForceNetwork.create(TrendingRepoService.class);
		Response<List<GitRepo>> response = service.fetchTrendingRepo().execute();
		if (response.isSuccessful()) {
			return response.body();
		}

		return Collections.emptyList();
	}
}
