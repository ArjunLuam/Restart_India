package com.restartindia.naukri.main;

import com.restartindia.naukri.main.model.PostDetails;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("/user")
    Call<PostDetails> postDetails(@FieldMap Map<String, Object> params);

}
