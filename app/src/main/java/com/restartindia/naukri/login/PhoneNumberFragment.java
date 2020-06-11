package com.restartindia.naukri.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.restartindia.naukri.R;
import com.restartindia.naukri.main.view.BottomNavActivity;

import java.util.List;
import java.util.Objects;


public class PhoneNumberFragment extends Fragment {

    TextInputEditText textInputEditText;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String phoneNumber;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_phone_number, container, false);
      textInputEditText=view.findViewById(R.id.etPhoneNumber);
        Button submit=view.findViewById(R.id.btnGetCode);

        GenerateOtp();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phn_no=textInputEditText.getText().toString();
                if (phn_no.isEmpty() || phn_no.length() < 10) {
                    textInputEditText.setError("Valid number is required");
                    textInputEditText.requestFocus();

                }
              else if (phn_no.length() == 10) {
                    phoneNumber = "+" + 91 + phn_no;
                    preferences = getActivity().getSharedPreferences("Naukri", Context.MODE_PRIVATE);
                    editor = preferences.edit();
                    editor.putString("PhoneNumber",phoneNumber);
                    editor.commit();

                    FragmentTransaction fr = getActivity().getSupportFragmentManager().beginTransaction();
                    fr.replace(R.id.container, new VerifyFragment());
                    fr.addToBackStack(null).commit();


                }

            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            List<? extends UserInfo> pd = user.getProviderData();
            UserInfo providerData = pd.get(1);
            String pid = providerData.getProviderId();

            Intent intent = new Intent(getContext(), BottomNavActivity.class);
            startActivity(intent);

            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container,new VerifyFragment());
            transaction.commit();

        }
    }

    private void GenerateOtp() {
        //String number = textInputEditText.getText().toString().trim();






    }
}