package com.example.foodare.model;

import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Model {
    private static final Model _instance = new Model();
    private Executor executor = Executors.newSingleThreadExecutor();
    private Handler mainHandler = HandlerCompat.createAsync(Looper.getMainLooper());
    private FirebaseModel firebaseModel = new FirebaseModel();

    public static Model instance() {
        return _instance;
    }

    private Model() {
    }

    List<Post> data = new LinkedList<>();

    AppLocalDbRepository localDb = AppLocalDb.getAppDb();

    public interface GetAllPostsListener {
        void onComplete(List<Post> data);
    }

    public void getAllPosts(GetAllPostsListener callback) {
//        executor.execute(() -> {
//            List<Post> data = localDb.PostDao().getAll();
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            mainHandler.post(() -> {
//                callback.onComplete(data);
//            });
//        });

        firebaseModel.getAllPosts(callback);

    }

    public interface AddPostListener {
        void onComplete();
    }

    public void addPost(Post post, AddPostListener listener) {
//        executor.execute(() -> {
//            localDb.PostDao().insertAll(post);
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            mainHandler.post(() -> {
//                listener.onComplete();
//            });
//        });

        firebaseModel.addPost(post, listener);
    }
}
