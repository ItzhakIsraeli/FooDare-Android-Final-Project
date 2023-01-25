package com.example.foodare.model;

import java.util.LinkedList;
import java.util.List;

public class Model {
    private static final Model _instance = new Model();

    public static Model instance() {
        return _instance;
    }

    private Model() {
        for (int i = 0; i < 2; i++) {
            addPost(new Post("Izhak","Hamburger" + i, i == 1 ? "Vitrina" : "Gorme26", "7.2", "Izhak King olam"));
        }
    }

    List<Post> data = new LinkedList<>();

    public List<Post> getAllPosts() {
        return data;
    }

    public void addPost(Post post) {
        data.add(post);
    }
}
