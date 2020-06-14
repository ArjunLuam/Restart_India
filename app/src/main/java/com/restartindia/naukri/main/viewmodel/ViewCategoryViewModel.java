package com.restartindia.naukri.main.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.restartindia.naukri.login.model.PostResponse;
import com.restartindia.naukri.service.Repository;

import java.util.List;

public class ViewCategoryViewModel extends ViewModel {
    private Repository repository;
    private LiveData<List<PostResponse>> employees;

    public ViewCategoryViewModel() {
        repository = new Repository();
    }

    public LiveData<List<PostResponse>> getEmployees(int pinCode, String category) {
        if (employees == null)
            employees = repository.searchEmployees(pinCode, category);
        return employees;
    }
}
