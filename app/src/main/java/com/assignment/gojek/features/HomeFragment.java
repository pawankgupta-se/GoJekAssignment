package com.assignment.gojek.features;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.assignment.gojek.BaseFragment;
import com.assignment.gojek.R;
import com.assignment.gojek.databinding.FragmentHomeBinding;
import com.assignment.gojek.models.GitRepo;
import com.assignment.gojek.utils.Utilities;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * Created by Pawan Gupta on 19/05/19.
 */
public class HomeFragment extends BaseFragment<HomeViewModel> {
	private FragmentHomeBinding mFragmentHomeBinding;
	private TrendingRepoAdapter mTrendingRepoAdapter;
	private GitRepo currentExpandedItem = null;

	public static Fragment newInstance() {
		return new HomeFragment();
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getAppComponent().inject(this);
		initViewModel(HomeViewModel.class);
		if (mViewModel != null) {
			currentExpandedItem = mViewModel.getCurrentExpandedItem().getValue();
		}
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		mFragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
		initViews();
		loadData(true);
		mFragmentHomeBinding.setViewModel(mViewModel);
		mFragmentHomeBinding.setLifecycleOwner(getViewLifecycleOwner());
		return mFragmentHomeBinding.getRoot();
	}

	private void loadData(boolean isInitialLoading) {
		mViewModel.getTrendingRepo(isInitialLoading).observe(getViewLifecycleOwner(), repoList -> {
			resetItemState(repoList);
			loadDataToView(repoList);
			mFragmentHomeBinding.swipeContainer.setRefreshing(false);
		});
	}

	private void resetItemState(List<GitRepo> repoList) {
		if (currentExpandedItem != null) {
			int index = repoList.indexOf(currentExpandedItem);
			if (index >= 0) {
				currentExpandedItem = repoList.get(index);
				currentExpandedItem.setExpanded(true);
			}
		}
	}

	private void initViews() {
		setTitle();
		mViewModel.getGetSoftError().observe(getViewLifecycleOwner(), aBoolean -> {
			if (aBoolean != null) {
				if (aBoolean) {
					mFragmentHomeBinding.swipeContainer.setRefreshing(false);
					Utilities.showError(mFragmentHomeBinding.getRoot(), getString(R.string.unable_to_refresh));
				}
			}
		});
		mFragmentHomeBinding.errorLayout.tryAgainButton.setOnClickListener(v -> loadData(true));
		mTrendingRepoAdapter = new TrendingRepoAdapter(this::handleItemClick);
		LinearLayoutManager layoutManager = new LinearLayoutManager(mFragmentHomeBinding.recyclerView.getContext());
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		mFragmentHomeBinding.recyclerView.setLayoutManager(layoutManager);
		mFragmentHomeBinding.recyclerView.setHasFixedSize(true);
		mFragmentHomeBinding.recyclerView.setAdapter(mTrendingRepoAdapter);
		DividerItemDecoration itemDecor = new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL);
		itemDecor.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(getContext(), R.drawable.divider)));
		mFragmentHomeBinding.recyclerView.addItemDecoration(itemDecor);
		mFragmentHomeBinding.swipeContainer.setOnRefreshListener(() -> mViewModel.refresh());
	}

	private void handleItemClick(GitRepo repo) {
		if (currentExpandedItem != null && !currentExpandedItem.equals(repo)) {
			currentExpandedItem.setExpanded(false);
			mTrendingRepoAdapter.notifyItemChanged(mTrendingRepoAdapter.getItemPosition(currentExpandedItem));
		}
		if (repo.isExpanded()) {
			repo.setExpanded(false);
		} else {
			repo.setExpanded(true);
			currentExpandedItem = repo;
			mViewModel.setCurrentExpandedItem(repo);
		}
		mTrendingRepoAdapter.notifyItemChanged(mTrendingRepoAdapter.getItemPosition(repo));
	}

	private void showData(boolean show) {
		int visibility = show ? View.GONE : View.VISIBLE;
		mFragmentHomeBinding.noContentLayout.getRoot().setVisibility(visibility);
	}

	private void loadDataToView(List<GitRepo> gitRepos) {
		if (gitRepos != null) {
			if (gitRepos.size() > 0) {
				mTrendingRepoAdapter.updateData(gitRepos);
				showData(true);
			} else {
				showData(false);
			}
		}
	}

	private void setTitle() {
		Activity activity = getActivity();
		if (activity != null) {
			getActivity().setTitle(R.string.trending_screen_title);
		}
	}
}

