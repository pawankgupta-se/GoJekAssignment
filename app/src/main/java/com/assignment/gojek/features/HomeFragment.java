package com.assignment.gojek.features;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.assignment.gojek.BaseFragment;
import com.assignment.gojek.databinding.FragmentHomeBinding;
import com.assignment.gojek.models.GitRepo;
import com.assignment.gojek.R;
import com.assignment.gojek.utils.Utilities;

import java.util.List;


/**
 * Created by Pawan Gupta on 19/05/19.
 */
public class HomeFragment extends BaseFragment<HomeViewModel> {
	private FragmentHomeBinding mFragmentHomeBinding;
	private TrendingRepoAdapter mTrendingReopAdapter;

	public static HomeFragment newInstance() {
		return new HomeFragment();
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getAppComponent().inject(this);
		initViewModel(HomeViewModel.class);

	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		mFragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
		initViews();
		loadData(true);
		mFragmentHomeBinding.setViewModel(mViewModel);
		mFragmentHomeBinding.setLifecycleOwner(getViewLifecycleOwner());
		mViewModel.getError().observe(getViewLifecycleOwner(), error -> {
			if (!TextUtils.isEmpty(error)) {
				Activity activity = getActivity();
				showData(false);
			}
		});

		return mFragmentHomeBinding.getRoot();
	}

	private void loadData(boolean isInitialLoading) {
		if (Utilities.isOnline(getContext())) {
			mViewModel.getTrendingRepo(isInitialLoading).observe(getViewLifecycleOwner(), repoList -> {
				loadDataToView(repoList);
				mFragmentHomeBinding.swipeContainer.setRefreshing(false);
			});
		} else {
			Utilities.showError(getActivity(), getString(R.string.not_connected_to_internet));
		}
	}

	private void initViews() {
		mTrendingReopAdapter = new TrendingRepoAdapter();
		LinearLayoutManager layoutManager = new LinearLayoutManager(mFragmentHomeBinding.recyclerView.getContext());
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		mFragmentHomeBinding.recyclerView.setLayoutManager(layoutManager);
		mFragmentHomeBinding.recyclerView.setHasFixedSize(true);
		mFragmentHomeBinding.recyclerView.setAdapter(mTrendingReopAdapter);
		DividerItemDecoration itemDecor = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
		itemDecor.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
		mFragmentHomeBinding.recyclerView.addItemDecoration(itemDecor);
		mFragmentHomeBinding.swipeContainer.setOnRefreshListener(() -> loadData(false));
	}

	private void showData(boolean show) {
		int visibility = show ? View.GONE : View.VISIBLE;
		mFragmentHomeBinding.emptyView.setVisibility(visibility);
	}

	private void loadDataToView(List<GitRepo> gitRepos) {
		if (gitRepos != null) {
			if (gitRepos.size() > 0) {
				mTrendingReopAdapter.updateData(gitRepos);
				showData(true);
			} else {
				showData(false);
			}
		}
	}
}

