package com.example.truemidtermdc.api;

import com.example.truemidtermdc.pojos.Login;
import com.example.truemidtermdc.pojos.Shoes;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RequestPlaceholder {

    @POST("login")
    Call<Login> login(@Body Login login);

    @GET("get-all-posts")
    Call<List<Shoes>> getAllPosts(@Query("t") String t, @Query("u") String u);
}
