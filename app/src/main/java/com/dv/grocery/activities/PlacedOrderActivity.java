package com.dv.grocery.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dv.grocery.R;
import com.dv.grocery.models.CartModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlacedOrderActivity extends AppCompatActivity {
    FirebaseFirestore db;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placed_order);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        List<CartModel> list = (ArrayList<CartModel>) getIntent().getSerializableExtra("productList");

        if (list != null && list.size() > 0) {
            for (CartModel model : list) {
                final HashMap<String, Object> cartMap = new HashMap<>();
                cartMap.put("productName", model.getProductName());
                cartMap.put("productPrice", model.getProductPrice());
                cartMap.put("currentDate", model.getCurrentDate());
                cartMap.put("currentTime", model.getCurrentTime());
                cartMap.put("totalQuantity", model.getTotalQuantity());
                cartMap.put("totalPrice", model.getTotalQuantity());

                db.collection("CurrentUser")
                    .document(auth.getCurrentUser().getUid())
                    .collection("MyOrder")
                    .add(cartMap)
                    .addOnCompleteListener(task -> {
                        Toast.makeText(PlacedOrderActivity.this, "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();
                    });
            }
        }
    }
}