package com.restartindia.naukri.login.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.restartindia.naukri.R;
import com.restartindia.naukri.util.Constants;

public class LoginOptionFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login_option, container, false);
        Button register = view.findViewById(R.id.btn_signup);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Change this to the Register as employee or employer fragment
                PhoneNumberFragment phoneNumberFragment = new PhoneNumberFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean(Constants.IS_FROM_LOGIN, false);
                phoneNumberFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, phoneNumberFragment).addToBackStack(null).commit();
            }
        });

        Button login = view.findViewById(R.id.btn_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneNumberFragment phoneNumberFragment = new PhoneNumberFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean(Constants.IS_FROM_LOGIN, true);
                phoneNumberFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, phoneNumberFragment).addToBackStack(null).commit();
            }
        });
        return view;
    }
}