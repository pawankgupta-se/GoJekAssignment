package com.assignment.gojek.dependencies.modules;

import com.assignment.gojek.network.services.TrendingRepoService;
import com.assignment.gojek.repository.TrendingRepoRepository;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Pawan Gupta on 19/05/19.
 */
@Module
public class DataModule {
    @Singleton
    @Provides
    TrendingRepoRepository providesTrendingRepoRepository(@Named("retrofit") Retrofit basicRetrofit, @Named("retrofit_force_network") Retrofit cacheRetrofit){
        return new TrendingRepoRepository(basicRetrofit, cacheRetrofit);
    }
}
