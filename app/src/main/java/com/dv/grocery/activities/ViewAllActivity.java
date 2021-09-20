package com.dv.grocery.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dv.grocery.R;
import com.dv.grocery.adapters.ViewAllAdapter;
import com.dv.grocery.models.ProductModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ViewAllActivity extends AppCompatActivity {
    TextView title;

    FirebaseFirestore db;
    RecyclerView recyclerView;
    ViewAllAdapter viewAllAdapter;
    List<ProductModel> productModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        title = findViewById(R.id.view_all_title);
        db = FirebaseFirestore.getInstance();

        String group = getIntent().getStringExtra("group");
        recyclerView = findViewById(R.id.view_all_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productModelList = new ArrayList<>();
        viewAllAdapter = new ViewAllAdapter(this, productModelList);
        recyclerView.setAdapter(viewAllAdapter);

        getMeat(group);
        getCleaningHouse(group);
    }

    private void getMeat(String group) {
        if (group != null && group.equalsIgnoreCase(getString(R.string.group_meat))) {
            db.collection(getString(R.string.popular_products))
                .whereEqualTo("group", getString(R.string.group_meat))
                .get()
                .addOnCompleteListener(task -> getResult(task));
        }
    }

    private void getCleaningHouse(String group) {
        if (group != null && group.equalsIgnoreCase(getString(R.string.group_cleaning_house))) {
            db.collection(getString(R.string.popular_products))
                .whereEqualTo("group", getString(R.string.group_cleaning_house))
                .get()
                .addOnCompleteListener(task -> getResult(task));
        }
    }

    private void getResult(Task<QuerySnapshot> task) {
        for (DocumentSnapshot documentSnapshot : Objects.requireNonNull(task.getResult()).getDocuments()) {
            ProductModel productModel = documentSnapshot.toObject(ProductModel.class);
            productModelList.add(productModel);
            title.setText(productModel.getType_name());
            viewAllAdapter.notifyDataSetChanged();
        }
    }
}