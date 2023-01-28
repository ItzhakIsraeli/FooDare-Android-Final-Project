package com.example.foodare;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
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
    List<Post> data = new LinkedList<>();
    PostRecyclerAdapter adapter;
    PostsFragmentListBinding binding;

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
        adapter = new PostRecyclerAdapter(getLayoutInflater(), data, R.layout.post_list_row);
        list.setAdapter(adapter);

        adapter.setOnItemClickListener(position -> {
            Log.d("TAG", "Row was clicked " + position);
            Post post = data.get(position);
            PostsListFragmentDirections.ActionPostsListFragmentToPostDetailsFragment action = PostsListFragmentDirections.actionPostsListFragmentToPostDetailsFragment(post.restaurant, post.meal, post.rate, post.description, post.username, post.imageUrl);
            Navigation.findNavController(view).navigate((NavDirections) action);
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        reloadData();
    }

    void reloadData() {
        binding.postsProgressBar.setVisibility(View.VISIBLE);
        Model.instance().getAllPosts((postList) -> {
            data = postList;
            adapter.setData(data);
            binding.postsProgressBar.setVisibility(View.GONE);
        });
    }
}