package com.example.foodare;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodare.databinding.MyPostsFragmentListBinding;
import com.example.foodare.databinding.PostsFragmentListBinding;
import com.example.foodare.model.Model;
import com.example.foodare.model.Post;

import java.util.LinkedList;
import java.util.List;

public class MyPostListFragment extends Fragment {

    PostListFragmentViewModel viewModel;
    PostRecyclerAdapter adapter;
    MyPostsFragmentListBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.my_posts_fragment_list, container, false);
//        data = Model.instance().getAllPosts();

        binding = MyPostsFragmentListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        RecyclerView list = binding.myPostsFragList;
        list.setHasFixedSize(true);

        list.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PostRecyclerAdapter(getLayoutInflater(), viewModel.getDataByUser("1234").getValue(), R.layout.my_post_list_row);
        list.setAdapter(adapter);

//        Button editBtn = view.findViewById(R.id.edit_profile_cancel_btn);

        adapter.setOnItemClickListener(position -> {
            Log.d("TAG", "Row was clicked " + position);
            Post post = viewModel.getDataByUser("1234").getValue().get(position);
            MyPostListFragmentDirections.ActionMyPostListFragmentToPostDetailsFragment action =
                    MyPostListFragmentDirections.actionMyPostListFragmentToPostDetailsFragment(post.restaurant, post.meal, post.rate, post.description, post.username, post.imageUrl);
            Navigation.findNavController(view).navigate((NavDirections) action);
        });

        viewModel.getData().observe(getViewLifecycleOwner(),observeList->{
            adapter.setData(observeList);
            binding.myPostsProgressBar.setVisibility(View.GONE);
        });

        Model.instance().EventPostsListLoadingState.observe(getViewLifecycleOwner(), status -> {
            binding.myPostsSwipeRefresh.setRefreshing(status == Model.LoadingState.LOADING);
        });


        binding.myPostsSwipeRefresh.setOnRefreshListener(()->{
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
//        binding.myPostsProgressBar.setVisibility(View.VISIBLE);
//        Model.instance().getAllPosts((postList) -> {
//            // TODO: think how to do this with the user Data
//            viewModel.setData(postList);
//            adapter.setData(viewModel.getDataByUser("1234"));
//            binding.myPostsProgressBar.setVisibility(View.GONE);
//        });
        Model.instance().refreshAllPosts();
    }
}