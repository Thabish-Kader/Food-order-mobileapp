package com.android.foodorderapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.foodorderapp.R;
import com.android.foodorderapp.model.CategoryModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.MyViewHolder> {

    private List<CategoryModel> categoryModelList;
    private CategoryListClickListener clickListener;

    public CategoryListAdapter(List<CategoryModel> restaurantModelList, CategoryListClickListener clickListener) {
        this.categoryModelList = restaurantModelList;
        this.clickListener = clickListener;
    }

    public void updateData(List<CategoryModel> categoryModelList) {
        this.categoryModelList = categoryModelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recycler, parent, false);
        return  new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryListAdapter.MyViewHolder holder, int position) {
        holder.categoryName.setText(categoryModelList.get(position).getName());
        holder.categoryAddress.setText("Address: "+ categoryModelList.get(position).getAddress());
        holder.categoryHours.setText("Today's hours: " + categoryModelList.get(position).getHours().getTodaysHours());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(categoryModelList.get(position));
            }
        });
        Glide.with(holder.thumbnail)
                .load(categoryModelList.get(position).getImage())
                .into(holder.thumbnail);

    }

    @Override
    public int getItemCount() {
        return categoryModelList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        TextView categoryAddress;
        TextView categoryHours;
        ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            categoryName = view.findViewById(R.id.categoryName);
            categoryAddress = view.findViewById(R.id.categoryAddress);
            categoryHours = view.findViewById(R.id.operationHours);
            thumbnail = view.findViewById(R.id.thumbnail);

        }
    }

    public interface CategoryListClickListener {
        public void onItemClick(CategoryModel restaurantModel);
    }
}
