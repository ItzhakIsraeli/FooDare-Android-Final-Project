package com.example.foodare;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodare.model.Model;
import com.example.foodare.model.Post;

import java.util.List;

public class PostListFragmentViewModel extends ViewModel {
    private LiveData<List<Post>> data = Model.instance().getAllPosts();

    public LiveData<List<Post>> getData() {
        return data;
    }

    public LiveData<List<Post>> getDataByUser(String userId) {
        return data;
    }

//    public void setData(List<Post> list) {
//        data = list;
//    }
}
