package com.assignment.gojek.features;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.assignment.gojek.models.GitRepo;
import com.assignment.gojek.repository.TrendingRepoRepository;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import timber.log.Timber;

/**
 * Created by Pawan Gupta on 19/05/19.
 */
public class HomeViewModel extends ViewModel {

    @Inject
    TrendingRepoRepository mTrendingRepoRepository;
    private MutableLiveData <List<GitRepo>> mFacts = new MutableLiveData<>();
    private MutableLiveData <String> mError = new MutableLiveData<>();

    @Inject public HomeViewModel(){}

    public LiveData<List<GitRepo>> getFacts(){
        new Thread(() -> {
            try {
                mFacts.postValue(mTrendingRepoRepository.getFacts());
            } catch (IOException e) {
                Timber.e(e);
                mError.postValue(e.getMessage());
            }
        }).start();
        return mFacts;
    }
    public LiveData<String> getError(){
        return mError;
    }
}
