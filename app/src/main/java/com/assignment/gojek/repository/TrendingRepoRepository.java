package com.assignment.gojek.repository;

import android.support.annotation.WorkerThread;

import com.assignment.gojek.models.GitRepo;
import com.assignment.gojek.network.services.TrendingRepoService;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import retrofit2.Response;

/**
 * Created by Pawan Gupta on 19/05/19.
 */
public class TrendingRepoRepository {
	private TrendingRepoService mFactService;

	public TrendingRepoRepository(TrendingRepoService trendingRepoService) {
		mFactService = trendingRepoService;
	}

	@WorkerThread
	public List<GitRepo> getFacts() throws IOException {
		Response<List<GitRepo>> response = mFactService.fetchTrendingRepo().execute();
		if (response.isSuccessful()) {
			return response.body();
		}

		return Collections.emptyList();
	}
}
