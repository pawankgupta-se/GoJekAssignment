package com.assignment.gojek.network.services;

import com.assignment.gojek.models.GitRepo;
import com.assignment.gojek.network.ApiUrls;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Pawan Gupta on 19/05/19.
 */
public interface TrendingRepoService {
    @GET(ApiUrls.TRENDING_REPO)
    Call<List<GitRepo>> fetchTrendingRepo();
}
