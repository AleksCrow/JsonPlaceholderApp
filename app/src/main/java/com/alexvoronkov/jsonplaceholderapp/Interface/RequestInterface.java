package com.alexvoronkov.jsonplaceholderapp.Interface;

import com.alexvoronkov.jsonplaceholderapp.Models.Posts;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestInterface {

    @GET("posts")
    Call<ArrayList<Posts>> getPosts();
}
