package com.restartindia.naukri.main.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.restartindia.naukri.R;
import com.restartindia.naukri.login.model.PostResponse;
import com.restartindia.naukri.main.viewmodel.ViewCategoryViewModel;
import com.restartindia.naukri.util.Constants;

import java.util.List;

public class ViewCategoryActivity extends AppCompatActivity {

    private int pinCode;
    private String category;
    private ViewCategoryViewModel viewModel;
    private List<PostResponse> employees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_category);

        viewModel = new ViewModelProvider(this).get(ViewCategoryViewModel.class);

        Intent intent = getIntent();
        if (intent.getIntExtra(Constants.PIN_CODE, Integer.MAX_VALUE) != Integer.MAX_VALUE) {
            pinCode = intent.getIntExtra(Constants.PIN_CODE, Integer.MAX_VALUE);
        }
        if (intent.getStringExtra(Constants.CATEGORY) != null) {
            category = intent.getStringExtra(Constants.CATEGORY);
        }


    }

    private void searchEmployees() {
        viewModel.getEmployees(pinCode, category).observe(this, new Observer<List<PostResponse>>() {
            @Override
            public void onChanged(List<PostResponse> postResponses) {
                employees.clear();
                employees.addAll(postResponses);
            }
        });
    }
}