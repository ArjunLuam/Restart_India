package com.restartindia.naukri.main.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.restartindia.naukri.R;
import com.restartindia.naukri.login.view.LoginActivity;
import com.restartindia.naukri.util.Constants;

public class ProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ImageView profileImage = view.findViewById(R.id.ivProfileImage);
        TextView tvName = view.findViewById(R.id.tvName);
        MaterialCardView cardAbout = view.findViewById(R.id.cardAbout);
        MaterialCardView cardHelp = view.findViewById(R.id.cardSavedPosts);

        cardAbout.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), DevelopersActivity.class));
        });
        cardHelp.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(Constants.YOUTUBE_URL));
            startActivity(i);
        });

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser.getPhotoUrl() != null) {
            Glide.with(this).load(firebaseUser.getPhotoUrl()).into(profileImage);
        }
        tvName.setText(firebaseUser.getDisplayName());

        ImageView logOutButton = view.findViewById(R.id.ivLogout);
        logOutButton.setOnClickListener(view1 -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            if (getActivity() != null)
                getActivity().finish();
        });

        return view;
    }


}
