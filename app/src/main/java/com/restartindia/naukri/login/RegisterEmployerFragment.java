package com.restartindia.naukri.login;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.restartindia.naukri.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class RegisterEmployerFragment extends Fragment {

    CircleImageView circleImageView;
    public static final int PICK_IMAGE_REQUEST=1;
    Uri imageuri;
    private MaterialButton msubmit;
    private StorageReference mstorageref;
    ProgressBar mprogressbar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_register_employer, container, false);
        circleImageView=view.findViewById(R.id.ivUploadedImage);
        msubmit=view.findViewById(R.id.empl_submit);
        mprogressbar=view.findViewById(R.id.progressBar);
        mstorageref= FirebaseStorage.getInstance().getReference("profile_pics");
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
        Intent gallery=new Intent();
        gallery.setType("image/*");
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(gallery,PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data !=null && data.getData() !=null){
            imageuri=data.getData();
            circleImageView.setImageURI(imageuri);
        }
    }
    private String getfileExtension(Uri uri){
       ContentResolver cr=getActivity().getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    private void uploadphoto(){
        if(imageuri !=null){
            StorageReference fileref=mstorageref.child(System.currentTimeMillis()
            + "."+getfileExtension(imageuri));

            fileref.putFile(imageuri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                                    mprogressbar.setProgress(0);

                            Toast.makeText(getContext(),"Upload Sucessful",Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress=(100 *(taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount()));
                            mprogressbar.setProgress((int) progress);
                        }
                    });
        }else{
            Toast.makeText(getContext(),"Upload photo",Toast.LENGTH_SHORT).show();
        }

    }

}