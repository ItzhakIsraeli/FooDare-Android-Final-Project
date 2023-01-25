package com.example.foodare;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodare.model.Model;
import com.example.foodare.model.Post;

import java.util.List;

public class PostsListFragment extends Fragment {
    List<Post> data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.posts_fragment_list, container, false);
        data = Model.instance().getAllPosts();
        RecyclerView list = view.findViewById(R.id.posts_frag_list);
        list.setHasFixedSize(true);

        list.setLayoutManager(new LinearLayoutManager(getContext()));
        PostRecyclerAdapter adapter = new PostRecyclerAdapter(getLayoutInflater(), data);
        list.setAdapter(adapter);

        adapter.setOnItemClickListener(position -> {
            Log.d("TAG", "Row was clicked " + position);
            Post post = data.get(position);
            PostsListFragmentDirections.ActionPostsListFragmentToPostDetailsFragment action = PostsListFragmentDirections.actionPostsListFragmentToPostDetailsFragment(post.restaurant, post.meal, post.rate, post.description, post.username);
            Navigation.findNavController(view).navigate(action);
        });
        return view;
    }
}