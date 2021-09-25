package com.dv.grocery.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dv.grocery.R;
import com.dv.grocery.models.CartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    Context context;
    List<CartModel> cartModelList;
    int totalPrice = 0;

    FirebaseFirestore db;
    FirebaseAuth auth;

    public CartAdapter(Context context, List<CartModel> cartModelList) {
        this.context = context;
        this.cartModelList = cartModelList;

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder((LayoutInflater.from(parent.getContext())).inflate(R.layout.cart_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(cartModelList.get(position).getProductImage()).into(holder.image);
        holder.name.setText(cartModelList.get(position).getProductName());
        holder.price.setText(cartModelList.get(position).getProductPrice());
        holder.quantity.setText(cartModelList.get(position).getTotalQuantity());
        onDeleteItem(holder, position);

        // Passing total to Cart Fragment
        totalPrice = totalPrice + cartModelList.get(position).getTotalPrice();
        Intent intent = new Intent("totalAmount");
        intent.putExtra("totalAmount", totalPrice);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }

    private void onDeleteItem(ViewHolder holder, int position) {
        holder.deleteBtn.setOnClickListener(view ->
            db.collection("CurrentUser")
            .document(auth.getCurrentUser().getUid())
            .collection("AddToCart")
            .document(cartModelList.get(position).getDocumentId())
            .delete()
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        cartModelList.remove(cartModelList.get(position));
                        notifyDataSetChanged();
                        Toast.makeText(context, "Đã xóa sản phẩm ra khỏi giỏ hàng.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context, "Đã có lỗi: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image, deleteBtn;
        TextView name, price, quantity, totalPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.cart_product_img);
            deleteBtn = itemView.findViewById(R.id.cart_delete_item);

            name = itemView.findViewById(R.id.cart_product_name);
            price = itemView.findViewById(R.id.cart_product_price);
            quantity = itemView.findViewById(R.id.cart_total_qty);
            totalPrice = itemView.findViewById(R.id.cart_total_price);
        }
    }
}
