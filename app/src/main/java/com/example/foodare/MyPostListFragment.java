package com.example.foodare;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodare.databinding.MyPostsFragmentListBinding;
import com.example.foodare.model.Model;
import com.example.foodare.model.UserModel;

public class MyPostListFragment extends PostsListFragment {

    PostListFragmentViewModel viewModel;
    PostRecyclerAdapter adapter;
    MyPostsFragmentListBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = MyPostsFragmentListBinding.inflate(inflater, container, false);
        adapter = new PostRecyclerAdapter(getLayoutInflater(), viewModel.getDataByUser(Model.instance().getCurrentUserMail()).getValue(), R.layout.my_post_list_row);
        RecyclerView list = binding.myPostsFragList;
        HandleLayoutManager(adapter, list, binding.postsSwipeRefresh, binding.getRoot(), viewModel.getDataByUser(Model.instance().getCurrentUserMail()));
        return binding.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(PostListFragmentViewModel.class);
    }
}