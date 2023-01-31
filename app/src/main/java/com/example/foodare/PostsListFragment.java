package com.example.foodare;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodare.databinding.PostsFragmentListBinding;
import com.example.foodare.model.Model;
import com.example.foodare.model.Post;

import java.util.LinkedList;
import java.util.List;

public class PostsListFragment extends Fragment {
    PostRecyclerAdapter adapter;
    PostsFragmentListBinding binding;
    PostListFragmentViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.posts_fragment_list, container, false);

        binding = PostsFragmentListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
//        data = Model.instance().getAllPosts();

//        reloadData();

//        RecyclerView list = view.findViewById(R.id.posts_frag_list);
        RecyclerView list = binding.postsFragList;

        list.setHasFixedSize(true);

        list.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PostRecyclerAdapter(getLayoutInflater(), viewModel.getData().getValue(), R.layout.post_list_row);
        list.setAdapter(adapter);

        adapter.setOnItemClickListener(position -> {
            Log.d("TAG", "Row was clicked " + position);
            Post post = viewModel.getData().getValue().get(position);
            PostsListFragmentDirections.ActionPostsListFragmentToPostDetailsFragment action = PostsListFragmentDirections.actionPostsListFragmentToPostDetailsFragment(post.restaurant, post.meal, post.rate, post.description, post.username, post.imageUrl);
            Navigation.findNavController(view).navigate((NavDirections) action);
        });

        viewModel.getData().observe(getViewLifecycleOwner(), observeList -> {
            adapter.setData(observeList);
            binding.postsProgressBar.setVisibility(View.GONE);
        });

        Model.instance().EventPostsListLoadingState.observe(getViewLifecycleOwner(), status -> {
            binding.postsSwipeRefresh.setRefreshing(status == Model.LoadingState.LOADING);
        });

        binding.postsSwipeRefresh.setOnRefreshListener(() -> {
            reloadData();
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(PostListFragmentViewModel.class);
    }

    void reloadData() {
//        binding.postsProgressBar.setVisibility(View.VISIBLE);
//        Model.instance().getAllPosts((postList) -> {
//            viewModel.setData(postList);
//            adapter.setData(viewModel.getData());
//            binding.postsProgressBar.setVisibility(View.GONE);
//        });
        Model.instance().refreshAllPosts();
    }
}