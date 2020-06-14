package com.restartindia.naukri.login.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.restartindia.naukri.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //TODO: Change this to LoginOption fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new RegisterEmployerFragment()).commit();
    }
}