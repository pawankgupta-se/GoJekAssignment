package com.assignment.gojek.dependencies.modules;

import com.assignment.gojek.network.services.TrendingRepoService;
import com.assignment.gojek.repository.TrendingRepoRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Pawan Gupta on 19/05/19.
 */
@Module
public class DataModule {
    @Singleton
    @Provides
    TrendingRepoRepository providesTrendingRepoRepository(TrendingRepoService trendingRepoService){
        return new TrendingRepoRepository(trendingRepoService);
    }
}
