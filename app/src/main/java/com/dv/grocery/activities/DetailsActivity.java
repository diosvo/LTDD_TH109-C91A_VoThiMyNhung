package com.dv.grocery.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.dv.grocery.R;
import com.dv.grocery.models.ProductModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailsActivity extends AppCompatActivity {

    ImageView detail_img;
    TextView name, desc, price, quantity;
    int totalQty = 1;
    int totalPrice = 0;

    Button plus, minus, add_to_cart;

    ProductModel productModel = null;
    FirebaseFirestore db;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        final Object object = getIntent().getSerializableExtra("details");
        if (object instanceof ProductModel) {
            productModel = (ProductModel) object;
        }

        init();
        updateQty();
        addToCart();

        if (productModel != null) {
            Glide.with(getApplicationContext()).load(productModel.getImage()).into(detail_img);
            name.setText(productModel.getName());
            desc.setText(productModel.getDescription());
            price.setText(productModel.getPrice());

            totalPrice = Integer.parseInt(productModel.getPrice()) * totalQty;
        }
    }

    private void init() {
        detail_img = findViewById(R.id.detail_img);
        name = findViewById(R.id.detail_name);
        desc = findViewById(R.id.detail_desc);
        price = findViewById(R.id.detail_price);

        quantity = findViewById(R.id.detail_qty);
        plus = findViewById(R.id.detail_plus);
        minus = findViewById(R.id.detail_minus);
        add_to_cart = findViewById(R.id.detail_add_to_cart);
    }

    private void updateQty() {
        plus.setOnClickListener(view -> {
            if (totalQty < 10) {
                totalQty++;
                quantity.setText(String.valueOf(totalQty));
                totalPrice = Integer.parseInt(productModel.getPrice()) * totalQty;
            }
        });

        minus.setOnClickListener(view -> {
            if (totalQty > 1) {
                totalQty--;
                quantity.setText(String.valueOf(totalQty));
                totalPrice = Integer.parseInt(productModel.getPrice()) * totalQty;
            }
        });
    }

    private void addToCart() {
        add_to_cart.setOnClickListener(view -> {
            String saveCurrentDate, saveCurrentTime;
            Calendar calendar = Calendar.getInstance();

            SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
            saveCurrentDate = currentDate.format(calendar.getTime());


            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss");
            saveCurrentTime = currentTime.format(calendar.getTime());

            final HashMap<String, Object> cartMap = new HashMap<>();
            cartMap.put("productName", productModel.getName());
            cartMap.put("productPrice", productModel.getPrice());
            cartMap.put("currentDate", saveCurrentDate);
            cartMap.put("currentTime", saveCurrentTime);
            cartMap.put("totalQuantity", quantity.getText().toString());
            cartMap.put("totalPrice", totalPrice);
            cartMap.put("productImage", productModel.getImage());

            db.collection("AddToCart")
                .document(auth.getCurrentUser().getUid())
                .collection("CurrentUser")
                .add(cartMap)
                .addOnCompleteListener(task -> {
                    Toast.makeText(DetailsActivity.this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                });
        });

    }
}