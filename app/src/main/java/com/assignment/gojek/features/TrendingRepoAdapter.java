package com.assignment.gojek.features;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.assignment.gojek.R;
import com.assignment.gojek.databinding.ItemRepoBinding;
import com.assignment.gojek.models.GitRepo;
import com.google.android.gms.common.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Pawan Gupta on 19/05/19.
 */
public class TrendingRepoAdapter extends RecyclerView.Adapter<TrendingRepoAdapter.ViewHolder> {
	private List<GitRepo> mGitRepos;
	private ActionHandler mHandler;

	public TrendingRepoAdapter(ActionHandler handler){
		mGitRepos = new ArrayList<>();
		mHandler = handler;
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
		ItemRepoBinding itemRepoBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.item_repo, viewGroup, false);
		return new ViewHolder(itemRepoBinding);
	}

	@Override
	public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
		viewHolder.bind(mGitRepos.get(i), mHandler);
	}

	@Override
	public int getItemCount() {
		return mGitRepos.size();
	}

	public class ViewHolder extends RecyclerView.ViewHolder {
		ItemRepoBinding mItemRepoBinding;

		public ViewHolder(@NonNull ItemRepoBinding itemRepoBinding) {
			super(itemRepoBinding.getRoot());
			mItemRepoBinding = itemRepoBinding;
		}

		public void bind(GitRepo gitRepo, ActionHandler handler) {
			mItemRepoBinding.setGitRepo(gitRepo);
			mItemRepoBinding.getRoot().setOnClickListener(v -> {
				if(handler != null){
					handler.expand(gitRepo);
				}
			});
		}
	}

	public void updateData(List<GitRepo> gitRepos) {
		if (!CollectionUtils.isEmpty(mGitRepos)) {
			mGitRepos.clear();
		}
		mGitRepos.addAll(gitRepos);
		notifyDataSetChanged();
	}

	public int getItemPosition(GitRepo repo){
		return mGitRepos.indexOf(repo);
	}
}
