package com.example.foodare.model;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Model {
    private static final Model _instance = new Model();
    private Executor executor = Executors.newSingleThreadExecutor();
    private Handler mainHandler = HandlerCompat.createAsync(Looper.getMainLooper());
    private FirebaseModel firebaseModel = new FirebaseModel();
    AppLocalDbRepository localDb = AppLocalDb.getAppDb();

    public static Model instance() {
        return _instance;
    }

    private Model() {
    }

    public interface Listener<T> {
        void onComplete(T data);
    }

    public enum LoadingState {
        LOADING,
        NOT_LOADING
    }

    final public MutableLiveData<LoadingState> EventPostsListLoadingState = new MutableLiveData<LoadingState>(LoadingState.NOT_LOADING);

    private LiveData<List<Post>> postsList;

    public LiveData<List<Post>> getAllPosts() {
        if (postsList == null) {
            postsList = localDb.PostDao().getAll();
            refreshAllPosts();
        }
        return postsList;
    }

    public void refreshAllPosts() {
        EventPostsListLoadingState.setValue(LoadingState.LOADING);
        // get local last update
        Long localLastUpdate = Post.getLocalLastUpdate();
        // get all updated recorde from firebase since local last update
        firebaseModel.getAllPostsSince(localLastUpdate, list -> {
            executor.execute(() -> {
                Log.d("TAG", " firebase return : " + list.size());
                Long time = localLastUpdate;
                for (Post post : list) {
                    // insert new records into ROOM
                    localDb.PostDao().insertAll(post);
                    if (time < post.getLastUpdated()) {
                        time = post.getLastUpdated();
                    }
                }
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // update local last update
                Post.setLocalLastUpdate(time);
                EventPostsListLoadingState.postValue(LoadingState.NOT_LOADING);
            });
        });
    }

    public void addPost(Post post, Listener<Void> listener) {
        firebaseModel.addPost(post, (Void) -> {
            refreshAllPosts();
            listener.onComplete(null);
        });
    }

    public void addFirebaseUser(String mail, String password) {
        firebaseModel.addFirebaseUser(mail, password);
    }

    public void logoutUser() {
        firebaseModel.logoutUser();
    }

    public boolean isUserConnected() {
        return firebaseModel.isUserConnected();
    }

    public void addUser(UserModel user, Listener<Void> listener) {
        firebaseModel.addUser(user, (Void) -> {
            listener.onComplete(null);
        });
    }

    public void getUserByMail(String userId, Listener<Void> listener) {
//        firebaseModel.getUserByMail(userId, (user) -> {
//            listener.onComplete(null);
//        });
    }

    public void uploadImage(String name, Bitmap bitmap, Listener<String> listener) {
        firebaseModel.uploadImage(name, bitmap, listener);
    }
}
