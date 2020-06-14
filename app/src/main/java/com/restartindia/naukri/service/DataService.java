package com.restartindia.naukri.service;

import com.restartindia.naukri.login.model.PostData;
import com.restartindia.naukri.login.model.PostResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface DataService {
    @POST("user")
    Call<PostResponse> createPost(@Body PostData postData);

    @GET("searchEmployees")
    Call<List<PostResponse>> searchEmployees(@Query("pincode") int pinCode, @Query("skill") String skill);

    @GET("user")
    Call<PostResponse> getUser(@Query("uid") String uid);
}
