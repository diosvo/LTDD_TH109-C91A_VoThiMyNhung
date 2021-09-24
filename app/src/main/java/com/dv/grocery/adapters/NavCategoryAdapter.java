package com.dv.grocery.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dv.grocery.R;
import com.dv.grocery.activities.ViewAllActivity;
import com.dv.grocery.models.CategoryModel;

import java.util.List;

public class NavCategoryAdapter extends RecyclerView.Adapter<NavCategoryAdapter.ViewHolder> {
    Context context;
    List<CategoryModel> categoryList;

    public NavCategoryAdapter(Context context, List<CategoryModel> categoryModelList) {
        this.context = context;
        this.categoryList = categoryModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_category_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NavCategoryAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(categoryList.get(position).getImage()).into(holder.catImage);
        holder.catName.setText(categoryList.get(position).getName());
        holder.catDesc.setText(categoryList.get(position).getDescription());

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, ViewAllActivity.class);
            intent.putExtra("group", categoryList.get(position).getGroup());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView catImage;
        TextView catName, catDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            catImage = itemView.findViewById(R.id.cat_item_img);
            catName = itemView.findViewById(R.id.cat_item_name);
            catDesc = itemView.findViewById(R.id.cat_item_desc);
        }
    }
}
