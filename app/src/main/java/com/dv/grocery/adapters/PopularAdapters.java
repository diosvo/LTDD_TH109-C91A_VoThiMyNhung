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
import com.dv.grocery.models.PopularProductModel;

import java.util.List;

public class PopularAdapters extends RecyclerView.Adapter<PopularAdapters.ViewHolder> {

    private final Context context;
    private final List<PopularProductModel> popularProductModelList;

    public PopularAdapters(Context context, List<PopularProductModel> popularProductModelList) {
        this.context = context;
        this.popularProductModelList = popularProductModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_products, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(popularProductModelList.get(position).getImage()).into(holder.popImage);
        holder.name.setText(popularProductModelList.get(position).getName());
        holder.description.setText(popularProductModelList.get(position).getDescription());
        holder.price.setText(popularProductModelList.get(position).getPrice());
        holder.group.setText(popularProductModelList.get(position).getGroup());
    }

    @Override
    public int getItemCount() {
        return popularProductModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView popImage;
        TextView name, description, price, group;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            popImage = itemView.findViewById(R.id.popular_product_img);
            name = itemView.findViewById(R.id.popular_product_name);
            description = itemView.findViewById(R.id.popular_product_desc);
            price = itemView.findViewById(R.id.popular_product_price);
            group = itemView.findViewById(R.id.popular_product_group);
        }
    }
}
