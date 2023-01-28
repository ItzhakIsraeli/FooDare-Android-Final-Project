package com.example.foodare.model;

import androidx.room.*;

import java.util.List;

@Dao
public interface PostDao {
    @Query("select * from Post")
    List<Post> getAll();

    @Query("select * from Post where id= :userId")
    List<Post> getPostsByUserId(String userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Post... posts);

    @Delete
    void delete(Post post);
}