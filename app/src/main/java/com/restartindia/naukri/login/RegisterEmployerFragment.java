package com.restartindia.naukri.login;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import com.restartindia.naukri.main.view.MainActivity;
import com.restartindia.naukri.util.Constants;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
import static androidx.core.content.PermissionChecker.PERMISSION_GRANTED;
import static androidx.core.content.PermissionChecker.checkSelfPermission;


public class RegisterEmployerFragment extends Fragment {

    CircleImageView circleImageView;
    public static final int PICK_IMAGE_REQUEST = 1;
    Uri imageuri;
    private MaterialButton msubmit;
    private StorageReference mstorageref;
    ProgressDialog progressDialog;
    private int REQUEST_CODE_LOCATION_PERMISSION = 99;

    String phoneNumber;
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
    TextInputLayout name;

    private static final String TAG = "Employer Fragment";
    int LOCATION_REQUEST_CODE = 10001;

    ImageView fetchLocation;
    Retrofit retrofit;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register_employer, container, false);
        //bundle

        final Bundle bundle = getArguments();
        if (bundle != null) {
            phoneNumber = bundle.getString(Constants.PHONE_NUMBER);
        }




        //bundle
        circleImageView = view.findViewById(R.id.ivUploadedImageemp);
        msubmit = view.findViewById(R.id.empl_submit);


        etDistrict = view.findViewById(R.id.dist_emplee);
        etCity = view.findViewById(R.id.city_emplee);
        name = view.findViewById(R.id.f_name_emplee);


        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Registering");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Please Wait");

        mstorageref = FirebaseStorage.getInstance().getReference("profile_pics");

        //Geolocation
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLastLocation();

        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
        }
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openfilechooser();
            }
        });


        msubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(name.getEditText().getText().toString()) || TextUtils.isEmpty(etCity.getEditText().getText().toString()) || TextUtils.isEmpty(etDistrict.getEditText().getText().toString())) {
                    Toast.makeText(getContext(), "Please fill all details", Toast.LENGTH_SHORT).show();

                } else {
                    uploadphoto();
                    startActivity(new Intent(getContext(), MainActivity.class));
                }

            }
        });
        return view;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                //TODO: Handle permission not granted later
                Toast.makeText(getContext(), "You need to allow permission to continue.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                    try {
                        addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        etDistrict.getEditText().setText(addressList.get(0).getLocality());
                        etCity.getEditText().setText(addressList.get(0).getPostalCode());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

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
                                //Toast.makeText(getContext(), "Upload Successful", Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                                afterImageUpload();

                            }
                        });

            }).addOnFailureListener(e -> {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            });
        } else {
            afterImageUpload();
        }

    }

    public void afterImageUpload() {
        //TODO : Upload data here


        boolean isEmployee = false;
        OkHttpClient.Builder okhttpBuider = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okhttpBuider.addInterceptor(loggingInterceptor);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://13.235.43.83:9090/")
                .addConverterFactory(GsonConverterFactory.create());
        retrofit = builder.build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        Map<String, Object> params = new HashMap<>();
        params.put("name", "Arjun");
        params.put("phone", "7889608162");
        params.put("district", "jammu");
        params.put("pincode", "180012");
        params.put("isEmployee", isEmployee );

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






