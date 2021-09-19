package com.dv.grocery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dv.grocery.R;
import com.dv.grocery.models.ProductModel;

import java.util.List;

public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.ViewHolder>{
    private final Context context;
    private final List<ProductModel> productModelList;

    public RecommendedAdapter(Context context, List<ProductModel> productModelList) {
        this.context = context;
        this.productModelList = productModelList;
    }

    @NonNull
    @Override
    public RecommendedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecommendedAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_product_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(productModelList.get(position).getImage()).into(holder.rcmImage);
        holder.name.setText(productModelList.get(position).getName());
        holder.price.setText(productModelList.get(position).getPrice());
    }

    @Override
    public int getItemCount() {
        return productModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView rcmImage;
        TextView name, price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            rcmImage = itemView.findViewById(R.id.home_product_img);
            name = itemView.findViewById(R.id.home_product_name);
            price = itemView.findViewById(R.id.home_product_price);
        }
    }

}
