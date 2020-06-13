package com.restartindia.naukri.login;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.restartindia.naukri.R;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class RegisterEmployerFragment extends Fragment {

    CircleImageView circleImageView;
    public static final int PICK_IMAGE_REQUEST = 1;
    Uri imageuri;
    private MaterialButton msubmit;
    private StorageReference mstorageref;
    ProgressDialog progressDialog;

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