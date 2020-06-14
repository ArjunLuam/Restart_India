package com.restartindia.naukri.main.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.restartindia.naukri.R;
import com.restartindia.naukri.login.model.PostResponse;
import com.restartindia.naukri.main.adapter.ViewCategoryAdapter;
import com.restartindia.naukri.main.viewmodel.ViewCategoryViewModel;
import com.restartindia.naukri.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class ViewCategoryActivity extends AppCompatActivity implements ViewCategoryAdapter.Listener {

    private int pinCode;
    private String category;
    private ViewCategoryViewModel viewModel;
    private List<PostResponse> employees;
    private ViewCategoryAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_category);

        viewModel = new ViewModelProvider(this).get(ViewCategoryViewModel.class);
        employees = new ArrayList<>();
        adapter = new ViewCategoryAdapter(employees, this, this);
        recyclerView = findViewById(R.id.rv);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Intent intent = getIntent();
        if (intent.getIntExtra(Constants.PIN_CODE, Integer.MAX_VALUE) != Integer.MAX_VALUE) {
            pinCode = intent.getIntExtra(Constants.PIN_CODE, Integer.MAX_VALUE);
        }
        if (intent.getStringExtra(Constants.CATEGORY) != null) {
            category = intent.getStringExtra(Constants.CATEGORY);
        }

        searchEmployees();
    }

    private void searchEmployees() {
        viewModel.getEmployees(pinCode, category).observe(this, new Observer<List<PostResponse>>() {
            @Override
            public void onChanged(List<PostResponse> postResponses) {
                employees.clear();
                employees.addAll(postResponses);
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public void onDialClicked(String phone) {
        dialPhoneNumber(phone);
    }
}