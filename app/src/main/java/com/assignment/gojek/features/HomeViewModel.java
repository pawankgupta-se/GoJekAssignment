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
	private MutableLiveData<List<GitRepo>> mRepoList = new MutableLiveData<>();
	private MutableLiveData<String> mError = new MutableLiveData<>();
	private MutableLiveData<Boolean> mProgress = new MutableLiveData<>();

	@Inject
	public HomeViewModel() {
	}

	public LiveData<List<GitRepo>> getTrendingRepo(boolean isInitialLoading) {
		if (isInitialLoading) {
			mProgress.postValue(true);
		}
		new Thread(() -> {
			try {
				mRepoList.postValue(mTrendingRepoRepository.getTrendingRepo());
				if (isInitialLoading) {
					mProgress.postValue(false);
				}
			} catch (IOException e) {
				Timber.e(e);
				mError.postValue(e.getMessage());
				if (isInitialLoading) {
					mProgress.postValue(false);
				}
			}
		}).start();
		return mRepoList;
	}

	public LiveData<String> getError() {
		return mError;
	}

	public LiveData<Boolean> getProgress() {
		return mProgress;
	}
}
