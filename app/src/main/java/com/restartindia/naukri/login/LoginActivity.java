package com.restartindia.naukri.login;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.restartindia.naukri.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        getSupportFragmentManager().beginTransaction().replace(R.id.container, new RegisterEmployerFragment()).commit();
    }
}
//firebase user kaise aa rha otp verify toh krwa nhi rhe