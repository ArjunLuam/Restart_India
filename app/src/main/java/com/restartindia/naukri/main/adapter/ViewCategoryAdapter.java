package com.restartindia.naukri.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.restartindia.naukri.R;
import com.restartindia.naukri.login.model.PostResponse;

import java.util.List;

public class ViewCategoryAdapter extends RecyclerView.Adapter<ViewCategoryAdapter.ViewCategoryViewHolder> {
    private List<PostResponse> employees;
    private Context mCtx;
    private Listener listener;

    public ViewCategoryAdapter(List<PostResponse> employees, Context mCtx, Listener listener) {
        this.employees = employees;
        this.mCtx = mCtx;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.card_employee, parent, false);
        return new ViewCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewCategoryViewHolder holder, int position) {
        PostResponse employee = employees.get(position);
        holder.tvName.setText(employee.getName());
        holder.tvDistrict.setText(employee.getDistrict());
        holder.tvPhone.setText(Long.toString(employee.getPhone()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDialClicked(employee.getPhone().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    public interface Listener {
        void onDialClicked(String phone);
    }

    public class ViewCategoryViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvPhone, tvDistrict;

        public ViewCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvDistrict = itemView.findViewById(R.id.tvDistrict);
        }
    }
}
