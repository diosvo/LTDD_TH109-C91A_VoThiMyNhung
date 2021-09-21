package com.dv.grocery.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.dv.grocery.R;
import com.dv.grocery.models.ProductModel;

public class DetailsActivity extends AppCompatActivity {

    ImageView detail_img;
    TextView name, desc, price, quantity;
    Button plus, minus, add_to_cart;

    ProductModel productModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        final Object object = getIntent().getSerializableExtra("details");
        if (object instanceof ProductModel) {
            productModel = (ProductModel) object;
        }

        init();

        if (productModel != null) {
            Glide.with(getApplicationContext()).load(productModel.getImage()).into(detail_img);
            name.setText(productModel.getName());
            desc.setText(productModel.getDescription());
            price.setText(productModel.getPrice());
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
}