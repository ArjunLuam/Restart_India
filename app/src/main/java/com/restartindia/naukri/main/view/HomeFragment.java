package com.restartindia.naukri.main.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.restartindia.naukri.R;
import com.restartindia.naukri.login.model.PostResponse;
import com.restartindia.naukri.main.adapter.CategoriesRecyclerViewAdapter;
import com.restartindia.naukri.main.model.JobCategory;
import com.restartindia.naukri.main.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private MainActivityViewModel viewModel;
    private CategoriesRecyclerViewAdapter adapter;
    private List<JobCategory> jobCategoryList;
    private FirebaseUser firebaseUser;
    private CircleImageView profileImage;
    private PostResponse userData;
    private ImageView ivIllus;
    private TextView tvTitle, tvThanks;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        jobCategoryList = new ArrayList<>();
        adapter = new CategoriesRecyclerViewAdapter(jobCategoryList, getContext());
        viewModel = new ViewModelProvider(getActivity()).get(MainActivityViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(adapter);
        profileImage = view.findViewById(R.id.ivProfileImage);
        ivIllus = view.findViewById(R.id.ivIllus);
        tvTitle = view.findViewById(R.id.tvTitle);
        tvThanks = view.findViewById(R.id.tvThanks);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Glide.with(this).load(firebaseUser.getPhotoUrl()).into(profileImage);
        getUserData();
    }

    private void getUserData() {
        viewModel.getUser(firebaseUser.getUid()).observe(getActivity(), new Observer<PostResponse>() {
            @Override
            public void onChanged(PostResponse postResponse) {
                userData = postResponse;
                if (userData.getIsEmployee()) {
                    showThankYouScreen();
                } else {
                    showCategories();
                }
            }
        });
    }

    private void showThankYouScreen() {
        recyclerView.setVisibility(View.INVISIBLE);
        tvTitle.setText(R.string.welcome);
        ivIllus.setVisibility(View.VISIBLE);
        tvThanks.setVisibility(View.VISIBLE);
    }

    private void showCategories() {
        recyclerView.setVisibility(View.VISIBLE);
        tvTitle.setText(R.string.categories);
        ivIllus.setVisibility(View.INVISIBLE);
        tvThanks.setVisibility(View.INVISIBLE);

        //TODO: Change icons and categories here, add from freepik. Pass the parameter to JobCategory from Constants class

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
