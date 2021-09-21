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
import com.dv.grocery.activities.DetailsActivity;
import com.dv.grocery.models.ProductModel;

import java.util.List;

public class ViewAllAdapter extends RecyclerView.Adapter<ViewAllAdapter.ViewHolder> {

    private final Context context;
    private final List<ProductModel> productModelList;

    public ViewAllAdapter(Context context, List<ProductModel> productModelList) {
        this.context = context;
        this.productModelList = productModelList;
    }

    @NonNull
    @Override
    public ViewAllAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_all_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAllAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(productModelList.get(position).getImage()).into(holder.va_img);
        holder.va_name.setText(productModelList.get(position).getName());
        holder.va_price.setText(productModelList.get(position).getPrice());
        holder.va_desc.setText(productModelList.get(position).getDescription());

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("details", productModelList.get(position));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView va_img;
        TextView va_name, va_price, va_desc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            va_img = itemView.findViewById(R.id.view_all_img);
            va_name = itemView.findViewById(R.id.view_all_name);
            va_price = itemView.findViewById(R.id.view_all_price);
            va_desc = itemView.findViewById(R.id.view_all_desc);
        }
    }
}
