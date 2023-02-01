package com.example.foodare;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodare.databinding.PostsFragmentListBinding;
import com.example.foodare.model.Meal;
import com.example.foodare.model.MealModel;
import com.example.foodare.model.Model;
import com.example.foodare.model.Post;

public class PostsListFragment extends Fragment {
    PostRecyclerAdapter adapter;
    PostsFragmentListBinding binding;
    PostListFragmentViewModel viewModel;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = PostsFragmentListBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        adapter = new PostRecyclerAdapter(getLayoutInflater(), viewModel.getData().getValue(), R.layout.post_list_row);
        RecyclerView list = binding.postsFragList;
        HandleLayoutManager(adapter, list, binding.postsSwipeRefresh, view);

        binding.dailyMealBtn.setOnClickListener(btn -> {
            NavDirections action = DailyMealFragmentDirections.actionGlobalDailyMealFragment();
            Navigation.findNavController(view).navigate((NavDirections) action);
        });

        return view;
    }

    public void HandleLayoutManager(PostRecyclerAdapter adapterHandler, RecyclerView listHandler, androidx.swiperefreshlayout.widget.SwipeRefreshLayout postsSwipeRefreshHandler,
                                    View viewHandler) {
        listHandler.setHasFixedSize(true);
        listHandler.setLayoutManager(new LinearLayoutManager(getContext()));
        listHandler.setAdapter(adapterHandler);

        adapterHandler.setOnItemClickListener(position -> {
            Log.d("TAG", "Row was clicked " + position);
            Post post = viewModel.getData().getValue().get(position);
            NavDirections action =
                    PostDetailsFragmentDirections.actionGlobalPostDetailsFragment(post.restaurant,
                            post.meal, post.rate, post.description, post.username, post.imageUrl);
            Navigation.findNavController(viewHandler).navigate((NavDirections) action);
        });

        viewModel.getData().observe(getViewLifecycleOwner(), adapterHandler::setData);

        Model.instance().EventPostsListLoadingState.observe(getViewLifecycleOwner(), status -> {
            postsSwipeRefreshHandler.setRefreshing(status == Model.LoadingState.LOADING);
        });

        postsSwipeRefreshHandler.setOnRefreshListener(this::reloadData);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(PostListFragmentViewModel.class);
    }

    void reloadData() {
        Model.instance().refreshAllPosts();
    }
}