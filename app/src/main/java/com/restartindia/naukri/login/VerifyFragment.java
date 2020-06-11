package com.restartindia.naukri.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.restartindia.naukri.R;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class VerifyFragment extends Fragment {



    private String verificationId;
    private FirebaseAuth mAuth;

    private EditText et1;
    private EditText et2;
    private EditText et3;
    private EditText et4;
    private EditText et5;
    private EditText et6;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_verify, container, false);
        Button verify=view.findViewById(R.id.btnVerify);
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = et1.getText().toString().trim()
                        + et2.getText().toString().trim()
                        + et3.getText().toString().trim()
                        + et4.getText().toString().trim()
                        + et5.getText().toString().trim()
                        + et6.getText().toString().trim();

                if (code.isEmpty() || code.length() < 6) {

                    et1.setError("Enter code...");
                    et1.requestFocus();
                    return;
                }
                verifyCode(code);
                //FragmentTransaction fr=getActivity().getSupportFragmentManager().beginTransaction();
                //fr.replace(R.id.container,new RegisterEmployerFragment());
                //fr.addToBackStack(null).commit();

            }
        });

        sharedPreferences = getActivity().getSharedPreferences("Naukri", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        sendVerificationCode(sharedPreferences.getString("PhoneNumber",""));


        et1 = view.findViewById(R.id.editTextNumber);
        et2 = view.findViewById(R.id.editTextNumber2);
        et3 = view.findViewById(R.id.editTextNumber3);
        et4 = view.findViewById(R.id.editTextNumber4);
        et5 = view.findViewById(R.id.editTextNumber5);
        et6 = view.findViewById(R.id.editTextNumber6);

        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("")) {
                    et2.requestFocus();
                }

            }
        });

        changeTextViewFocus(et1, et2, et3);
        changeTextViewFocus(et2, et3, et4);
        changeTextViewFocus(et3, et4, et5);
        changeTextViewFocus(et4, et5, et6);

        et6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")) {
                    et5.requestFocus();
                }

            }
        });
        return view;


    }
    private void changeTextViewFocus(EditText et1, EditText et2, EditText et3) {
        et2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!s.toString().equals("")) {
                    et3.requestFocus();
                } else {
                    et1.requestFocus();
                }

            }
        });
    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }
    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {


                                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.container,new RegisterEmployerFragment());
                                transaction.commit();

                            }



                     else {
                        Toast.makeText(getContext(), Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void sendVerificationCode(String number) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );

    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                int codeint = Integer.parseInt(code);

                int six = codeint % 10;
                int five = (codeint % 100) / 10;
                int four = (codeint % 1000) / 100;
                int three = (codeint % 10000) / 1000;
                int two = (codeint % 100000) / 10000;
                int one = (codeint) / 100000;

                et1.setText(String.valueOf(one));
                et2.setText(String.valueOf(two));
                et3.setText(String.valueOf(three));
                et4.setText(String.valueOf(four));
                et5.setText(String.valueOf(five));
                et6.setText(String.valueOf(six));

                verifyCode(code);
            } else {

                et1.setText("1");
                et2.setText("2");
                et3.setText("3");
                et4.setText("4");
                et5.setText("5");
                et6.setText("6");

                signInWithCredential(phoneAuthCredential);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(getContext(), "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
            Toast.makeText(getContext(), "Verification Code Sent", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
            super.onCodeAutoRetrievalTimeOut(s);
            Toast.makeText(getContext(), "Auto Retrieval Timeout, Enter code manually", Toast.LENGTH_SHORT).show();
        }
    };

}
