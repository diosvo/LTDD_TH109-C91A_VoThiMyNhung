package com.dv.grocery.adapters;

import android.annotation.SuppressLint;
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
import com.dv.grocery.activities.ViewAllActivity;
import com.dv.grocery.models.ProductModel;

import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {

    private final Context context;
    private final List<ProductModel> productModelList;

    public PopularAdapter(Context context, List<ProductModel> productModelList) {
        this.context = context;
        this.productModelList = productModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_product_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(productModelList.get(position).getImage()).into(holder.popImage);
        holder.name.setText(productModelList.get(position).getName());
        holder.price.setText(productModelList.get(position).getPrice());

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
        ImageView popImage;
        TextView name, price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            popImage = itemView.findViewById(R.id.home_product_img);
            name = itemView.findViewById(R.id.home_product_name);
            price = itemView.findViewById(R.id.home_product_price);
        }
    }
}
