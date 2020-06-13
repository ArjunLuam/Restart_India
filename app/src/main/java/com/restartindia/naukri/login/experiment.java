package com.restartindia.naukri.login;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.restartindia.naukri.R;
import com.restartindia.naukri.main.ApiInterface;
import com.restartindia.naukri.main.model.PostDetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;

public class experiment {
    /*
    {

        CircleImageView circleImageView;
        public static final int PICK_IMAGE_REQUEST = 1001;
        Uri imageuri;
        private MaterialButton msubmit;
        private StorageReference mstorageref;
        ProgressDialog progressDialog;
        private int REQUEST_CODE_LOCATION_PERMISSION = 1;

        String address1;
        String area;
        String city;
        String postalCode;
        String country;
        FusedLocationProviderClient fusedLocationProviderClient;
        private double latitude, longitude;

        List<Address> addressList;
        Geocoder geocoder;
        TextInputLayout etDistrict;
        TextInputLayout etCity;
        TextInputLayout etName;

        //Retrofit
        Retrofit retrofit;

        @Override
        public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        View view = inflater.inflate(R.layout.fragment_register_employee, container, false);
        circleImageView = view.findViewById(R.id.uploadImageEmplee);
        msubmit = view.findViewById(R.id.submitEmplee);

        etDistrict = view.findViewById(R.id.dist_emplee);
        etCity = view.findViewById(R.id.city_emplee);
        etName = view.findViewById(R.id.f_name_emplee);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Registering");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Please Wait");

        mstorageref = FirebaseStorage.getInstance().getReference("profile_pics");
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                openfilechooser();
            }
        });


        msubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etName.getEditText().getText().toString()==null || etCity.getEditText().getText().toString() == null || etCity.getEditText().getText().toString()==null){
                    Toast.makeText(getContext(), "Please fill all details", Toast.LENGTH_SHORT).show();
                }
                uploadphoto();
            }
        });

        //  afterImageUpload();

        return view;
    }

        private void openfilechooser() {
        Intent gallery = new Intent();
        gallery.setType("image/*");
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(gallery, PICK_IMAGE_REQUEST);
    }

        @Override
        public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageuri = data.getData();
            circleImageView.setImageURI(imageuri);
        }
    }

        private String getfileExtension(Uri uri) {
        ContentResolver cr = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

        private void uploadphoto() {
        if (imageuri != null) {
            progressDialog.show();
            //imageuri = Utils.compressImage(getContext(), imageuri);
            StorageReference fileref = mstorageref.child(System.currentTimeMillis()
                    + "." + getfileExtension(imageuri));
            UploadTask uploadTask = fileref.putFile(imageuri);
            uploadTask.continueWithTask(task -> {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return fileref.getDownloadUrl();
            }).addOnCompleteListener(task -> {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setPhotoUri(task.getResult())
                        .build();

                user.updateProfile(profileUpdates)
                        .addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                //   Toast.makeText(getContext(), "Upload Successful", Toast.LENGTH_LONG).show();
                                Log.e("UPLOAD", "SUCCESSFUL");
                                progressDialog.dismiss();
                                afterImageUpload();

                            }
                        });

            }).addOnFailureListener(e -> {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            });
        } else {
            Toast.makeText(getContext(), "Upload photo", Toast.LENGTH_SHORT).show();
        }

    }

        public void afterImageUpload() {
        //TODO : Upload data here

        ArrayList<String> skills = new ArrayList<>();
        skills.add("Mason");
        skills.add("Designer");
        skills.add("Technician");
        OkHttpClient.Builder okhttpBuider = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okhttpBuider.addInterceptor(loggingInterceptor);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://13.235.43.83:9090/")
                .addConverterFactory(GsonConverterFactory.create());
        retrofit = builder.build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Map<String, Object> params = new HashMap<>();
        params.put("name", "Arjun");
        params.put("phone", "7889608162");
        params.put("district", "jammu");
        params.put("pincode", "180012");
        params.put("isEmployee", true);
        params.put("skills", skills);
        params.put("uid", "123456789");

        Call<PostDetails> call = apiInterface.postDetails(params);
        call.enqueue(new Callback<PostDetails>() {
            @Override
            public void onResponse(Call<PostDetails> call, Response<PostDetails> response) {
                if (response.isSuccessful()) {
                    Log.e("Result", "" + response.code());
                    Toast.makeText(getContext(), "" + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PostDetails> call, Throwable t) {
                Log.e("Failed", "" + t.getMessage());
                // Toast.makeText(getContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    }

     */
}
