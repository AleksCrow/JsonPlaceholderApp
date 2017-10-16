package com.alexvoronkov.jsonplaceholderapp.Interface;

import com.alexvoronkov.jsonplaceholderapp.Card;
import com.alexvoronkov.jsonplaceholderapp.Models.Posts;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestInterface {

    @GET("posts")
    Call<ArrayList<Posts>> getPosts();

    @GET("comments")
    Call<Posts> getComments();

    @GET("users")
    Call<Posts> getUsers();

    @GET("photos/3")
    Call<Posts> getPhotos();

    @GET("todos")
    Call<Posts> getTodos();
}
