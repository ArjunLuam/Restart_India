package com.restartindia.naukri.main.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.restartindia.naukri.R;
import com.restartindia.naukri.main.model.JobCategory;

import java.util.List;

public class CategoriesRecyclerViewAdapter extends RecyclerView.Adapter<CategoriesRecyclerViewAdapter.CategoriesViewHolder> {
    private List<JobCategory> jobCategoryList;
    private Context mCtx;

    public CategoriesRecyclerViewAdapter(List<JobCategory> jobCategoryList, Context mCtx) {
        this.jobCategoryList = jobCategoryList;
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.card_categories, parent, false);
        return new CategoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder holder, int position) {
        JobCategory jobCategory = jobCategoryList.get(position);
        holder.icon.setImageResource(jobCategory.getIcon());
        holder.icon.setColorFilter(ContextCompat.getColor(mCtx, jobCategory.getColor()));
        holder.category.setText(jobCategory.getCategory());
        holder.jobs.setText(jobCategory.getJobs() + " Jobs");
        holder.shadow.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(mCtx, jobCategory.getColor())));
        holder.shadowText.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(mCtx, jobCategory.getColor())));
    }

    @Override
    public int getItemCount() {
        return jobCategoryList.size();
    }

    class CategoriesViewHolder extends RecyclerView.ViewHolder {
        private ImageView icon;
        private View shadow, shadowText;
        private TextView category, jobs;

        public CategoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            shadow = itemView.findViewById(R.id.shadow);
            shadowText = itemView.findViewById(R.id.shadowText);
            category = itemView.findViewById(R.id.category);
            jobs = itemView.findViewById(R.id.jobs);

        }
    }
}
