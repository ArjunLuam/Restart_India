package com.restartindia.naukri.service;

import androidx.lifecycle.MutableLiveData;

import com.restartindia.naukri.login.model.PostResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private MutableLiveData<List<PostResponse>> employeeList = new MutableLiveData<>();
    private MutableLiveData<PostResponse> user = new MutableLiveData<>();

    public MutableLiveData<List<PostResponse>> searchEmployees(int pinCode, String skill) {
        Call<List<PostResponse>> call = RetrofitInstance.getService().searchEmployees(pinCode, skill);
        call.enqueue(new Callback<List<PostResponse>>() {
            @Override
            public void onResponse(Call<List<PostResponse>> call, Response<List<PostResponse>> response) {
                List<PostResponse> employees = response.body();
                if (employees != null && !employees.isEmpty()) {
                    employeeList.setValue(employees);
                }
            }

            @Override
            public void onFailure(Call<List<PostResponse>> call, Throwable t) {
            }
        });
        return employeeList;
    }

    public MutableLiveData<PostResponse> getUser(String uid) {
        Call<PostResponse> call = RetrofitInstance.getService().getUser(uid);
        call.enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                PostResponse userFromServer = response.body();
                if (userFromServer != null) {
                    user.setValue(userFromServer);
                }
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {

            }
        });
        return user;
    }
}
