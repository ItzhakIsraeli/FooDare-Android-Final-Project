package com.example.foodare;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.foodare.model.Model;
import com.example.foodare.model.Post;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PostListFragmentViewModel extends ViewModel {
    private LiveData<List<Post>> data = Model.instance().getAllPosts();

    public LiveData<List<Post>> getData() {
        return data;
    }

    public LiveData<List<Post>> getDataByUser(String mail) {
        LiveData<List<Post>> temp = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            temp = Transformations.map(data, data -> data.stream().filter(post -> post.getUsername().equals(mail)).collect(Collectors.toList()));
        }
        return temp;
//        List<Post> temp = data.getValue().stream().filter(post -> post.getId() == mail).collect(Collectors.toList());
//        LiveData<List<Post>> retList = data.getValue().stream().filter(post -> post.getId() == mail).collect(Collectors.toList());


//        List<Post> postList = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//            postList =  data.getValue().stream().filter(post -> post.getId() == mail).collect(Collectors.toList());
//        }
////        LiveData<List<Post>> returnData = ;
//        return (LiveData<List<Post>>) postList;
    }
}
