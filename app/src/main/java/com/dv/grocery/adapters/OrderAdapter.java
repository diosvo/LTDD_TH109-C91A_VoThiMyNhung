package com.dv.grocery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dv.grocery.R;
import com.dv.grocery.models.CartModel;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    Context context;
    List<CartModel> modelList;

    public OrderAdapter(Context context, List<CartModel> modelList) {
        this.context = context;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderAdapter.ViewHolder((LayoutInflater.from(parent.getContext())).inflate(R.layout.order_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
        holder.name.setText(modelList.get(position).getProductName());
        holder.totalQty.setText(modelList.get(position).getTotalQuantity());
        holder.totalPrice.setText("" + modelList.get(position).getTotalPrice());
        holder.currentDate.setText(modelList.get(position).getCurrentDate());
        holder.currentTime.setText(modelList.get(position).getCurrentTime());
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, totalQty, totalPrice, currentDate, currentTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.order_product_name);
            totalQty = itemView.findViewById(R.id.order_total_qty);
            totalPrice = itemView.findViewById(R.id.order_total_price);
            currentDate = itemView.findViewById(R.id.order_current_date);
            currentTime = itemView.findViewById(R.id.order_current_time);
        }
    }
}
