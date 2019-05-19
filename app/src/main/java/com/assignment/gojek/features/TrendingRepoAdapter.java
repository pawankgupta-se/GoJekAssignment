package com.assignment.gojek.features;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.assignment.gojek.R;
import com.assignment.gojek.databinding.ItemRepoBinding;
import com.assignment.gojek.models.GitRepo;
import com.google.android.gms.common.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pawan Gupta on 19/05/19.
 */
public class TrendingRepoAdapter extends RecyclerView.Adapter<TrendingRepoAdapter.ViewHolder> {
    List<GitRepo> mGitRepos = new ArrayList<>();
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemRepoBinding itemRepoBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),R.layout.item_repo, viewGroup, false);
        return new ViewHolder(itemRepoBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(mGitRepos.get(i));
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

        public void bind(GitRepo gitRepo){
            mItemRepoBinding.setGitRepo(gitRepo);
        }
    }

    public void updateData(List<GitRepo> gitRepos){
        if(!CollectionUtils.isEmpty(mGitRepos)){
            mGitRepos.clear();
        }
        mGitRepos.addAll(gitRepos);
        notifyDataSetChanged();
    }
}
