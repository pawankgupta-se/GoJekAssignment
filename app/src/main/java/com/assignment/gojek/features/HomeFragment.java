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

import static android.support.v7.widget.RecyclerView.HORIZONTAL;


/**
 * Created by Pawan Gupta on 19/05/19.
 */
public class HomeFragment extends BaseFragment<HomeViewModel> {
	private FragmentHomeBinding mFragmentHomeBinding;
	private TrendingRepoAdapter mFactAdapter;

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
		loadData();
		mViewModel.getError().observe(getViewLifecycleOwner(), error -> {
			if (!TextUtils.isEmpty(error)) {
				Activity activity = getActivity();
				Utilities.showError(activity, error);
			}
		});

		return mFragmentHomeBinding.getRoot();
	}

	private void loadData() {
		if (Utilities.isOnline(getContext())) {
			mViewModel.getFacts().observe(getViewLifecycleOwner(), facts -> {
				loadDataToView(facts);
				mFragmentHomeBinding.swipeContainer.setRefreshing(false);
			});
		} else {
			Utilities.showError(getActivity(), getString(R.string.not_connected_to_internet));
		}
	}

	private void initViews() {
		mFactAdapter = new TrendingRepoAdapter();
		LinearLayoutManager layoutManager = new LinearLayoutManager(mFragmentHomeBinding.recyclerView.getContext());
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		mFragmentHomeBinding.recyclerView.setLayoutManager(layoutManager);
		mFragmentHomeBinding.recyclerView.setHasFixedSize(true);
		mFragmentHomeBinding.recyclerView.setAdapter(mFactAdapter);
		DividerItemDecoration itemDecor = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
		itemDecor.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
		mFragmentHomeBinding.recyclerView.addItemDecoration(itemDecor);
		mFragmentHomeBinding.swipeContainer.setOnRefreshListener(() -> loadData());
	}

	private void showData(boolean show) {
		int visibility = show ? View.GONE : View.VISIBLE;
		mFragmentHomeBinding.emptyView.setVisibility(visibility);
	}

	private void loadDataToView(List<GitRepo> gitRepos) {
		if (gitRepos != null) {
			if (gitRepos.size() > 0) {
				mFactAdapter.updateData(gitRepos);
				showData(true);
			} else {
				showData(false);
			}
		}
	}
}

