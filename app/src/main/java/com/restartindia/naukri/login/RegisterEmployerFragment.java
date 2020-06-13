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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.restartindia.naukri.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class RegisterEmployerFragment extends Fragment {

    CircleImageView circleImageView;
    public static final int PICK_IMAGE_REQUEST = 1;
    Uri imageuri;
    private MaterialButton msubmit;
    private StorageReference mstorageref;
    ProgressDialog progressDialog;
    private int REQUEST_CODE_LOCATION_PERMISSION = 99;

    String address1;
    String area;
    String city;
    String postalCode;
    String country;
    FusedLocationProviderClient fusedLocationProviderClient;
    private double latitude, longitude;

    List<Address> addressList;
    Geocoder geocoder;
    EditText etDistrict;
    EditText etCity;
    EditText name;
    private static final String TAG = "Employer Fragment";
    int LOCATION_REQUEST_CODE = 10001;

    ImageView fetchLocation;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register_employer, container, false);


        circleImageView = view.findViewById(R.id.ivUploadedImage);
        msubmit = view.findViewById(R.id.empl_submit);

        etDistrict = view.findViewById(R.id.et_dist_emplee);
        etCity = view.findViewById(R.id.et_city_emplee);
        name = view.findViewById(R.id.f_name_emplr);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Registering");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Please Wait");

        mstorageref = FirebaseStorage.getInstance().getReference("profile_pics");

        //Geolocation
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(getContext());
        if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location=task.getResult();
                    if(location !=null){
                        Geocoder geocoder=new Geocoder(getActivity(), Locale.getDefault());
                        try {
                            addressList=geocoder.getFromLocation(
                                    location.getLatitude(),location.getLongitude(),1
                            );
                            etDistrict.setText(addressList.get(0).getLocality());
                            etCity.setText(addressList.get(0).getPostalCode());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }else{
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_REQUEST_CODE);
        }
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (name.getText().toString()==null || etCity.getText().toString() == null || etCity.getText().toString()==null){
                    Toast.makeText(getContext(), "Please fill all details", Toast.LENGTH_SHORT).show();
                }
                openfilechooser();
            }
        });


        msubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadphoto();
            }
        });



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
                                Toast.makeText(getContext(), "Upload Successful", Toast.LENGTH_LONG).show();
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
    }











}


