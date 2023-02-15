package com.example.foodare.model;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;

@Dao
public interface PostDao {
    @Query("select * from Post")
    LiveData<List<Post>> getAll();

    @Query("select * from Post where isDeleted = :isDeleted")
    LiveData<List<Post>> getAllPosts(Boolean isDeleted);

    @Query("select * from Post where id= :userId")
    List<Post> getPostsByUserId(String userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Post... posts);

    @Delete
    void delete(Post post);

    @Query("DELETE FROM Post WHERE id = :postId")
    void deletePostById(String postId);
}