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
        holder.price.setText(popularProductModelList.get(position).getPrice());
        holder.typeName.setText(popularProductModelList.get(position).getType_name());
    }

    @Override
    public int getItemCount() {
        return popularProductModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView popImage;
        TextView name, description, price, typeName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            popImage = itemView.findViewById(R.id.popular_product_img);
            name = itemView.findViewById(R.id.popular_product_name);
            price = itemView.findViewById(R.id.popular_product_price);
            typeName = itemView.findViewById(R.id.popular_product_typeName);
        }
    }
}
