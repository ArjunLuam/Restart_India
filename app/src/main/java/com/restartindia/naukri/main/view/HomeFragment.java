package com.restartindia.naukri.main.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.restartindia.naukri.R;
import com.restartindia.naukri.main.adapter.CategoriesRecyclerViewAdapter;
import com.restartindia.naukri.main.model.JobCategory;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private CategoriesRecyclerViewAdapter adapter;
    private List<JobCategory> jobCategoryList;
    private FirebaseUser user;
    private CircleImageView profileImage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user = FirebaseAuth.getInstance().getCurrentUser();
        jobCategoryList = new ArrayList<>();
        adapter = new CategoriesRecyclerViewAdapter(jobCategoryList, getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(adapter);

        profileImage = view.findViewById(R.id.ivProfileImage);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Glide.with(this).load(user.getPhotoUrl()).into(profileImage);
        jobCategoryList.add(new JobCategory("Design", 100, R.color.color1, R.drawable.ic_design_icon));
       // jobCategoryList.add(new JobCategory("IT", 400, R.color.color2, R.drawable.ic_design_icon));
        jobCategoryList.add(new JobCategory("Plumber", 25, R.color.color3, R.drawable.ic_design_icon));
        jobCategoryList.add(new JobCategory("Carpenter", 300, R.color.color4, R.drawable.ic_design_icon));
        jobCategoryList.add(new JobCategory("Goods Delivery ", 50, R.color.color5, R.drawable.ic_design_icon));
        jobCategoryList.add(new JobCategory("Constructor", 80, R.color.color6, R.drawable.ic_design_icon));
        jobCategoryList.add(new JobCategory("Mechanic", 100, R.color.color1, R.drawable.ic_design_icon));
        jobCategoryList.add(new JobCategory("Technician", 400, R.color.color2, R.drawable.ic_design_icon));
        jobCategoryList.add(new JobCategory("Electrician", 25, R.color.color3, R.drawable.ic_design_icon));
        jobCategoryList.add(new JobCategory("Cleaner", 300, R.color.color4, R.drawable.ic_design_icon));
     //   jobCategoryList.add(new JobCategory("Driver", 50, R.color.color5, R.drawable.ic_design_icon));
      //  jobCategoryList.add(new JobCategory("Mason", 80, R.color.color6, R.drawable.ic_design_icon));
        adapter.notifyDataSetChanged();
    }
}
