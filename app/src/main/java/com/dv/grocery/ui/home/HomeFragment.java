package com.dv.grocery.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dv.grocery.R;
import com.dv.grocery.adapters.HomeCategoryAdapter;
import com.dv.grocery.adapters.PopularAdapter;
import com.dv.grocery.adapters.RecommendedAdapter;
import com.dv.grocery.models.HomeCategoryModel;
import com.dv.grocery.models.ProductModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {
    RecyclerView popularRec, categoryRec, recommendedRec;
    FirebaseFirestore db;

    // Popular Products
    List<ProductModel> popularProductsList;
    PopularAdapter popularAdapter;

    // Category
    List<HomeCategoryModel> categoryList;
    HomeCategoryAdapter categoryAdapter;

    // Recommend Products
    List<ProductModel> rcmProductsList;
    RecommendedAdapter recommendedAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        popularRec = root.findViewById(R.id.popular_products);
        categoryRec = root.findViewById(R.id.home_category);
        recommendedRec = root.findViewById(R.id.recommend_products);

        db = FirebaseFirestore.getInstance();

        getPopularProducts();
        getHomeCategory();
        getRecommendProducts();

        return root;
    }

    private void getPopularProducts() {
        popularRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        popularProductsList = new ArrayList<>();
        popularAdapter = new PopularAdapter(getActivity(), popularProductsList);
        popularRec.setAdapter(popularAdapter);

        db.collection("PopularProducts")
            .get()
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                        ProductModel popularProductModel = document.toObject(ProductModel.class);
                        popularProductsList.add(popularProductModel);
                        popularAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(getActivity(), "Error: " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            });
    }

    private void getHomeCategory() {
        categoryRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        categoryList = new ArrayList<>();
        categoryAdapter = new HomeCategoryAdapter(getActivity(), categoryList);
        categoryRec.setAdapter(categoryAdapter);

        db.collection("HomeCategory")
            .get()
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                        HomeCategoryModel homeCategoryModel = document.toObject(HomeCategoryModel.class);
                        categoryList.add(homeCategoryModel);
                        categoryAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(getActivity(), "Error: " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            });
    }

    private void getRecommendProducts() {
        recommendedRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        rcmProductsList = new ArrayList<>();
        recommendedAdapter = new RecommendedAdapter(getActivity(), rcmProductsList);
        recommendedRec.setAdapter(recommendedAdapter);

        db.collection("RecommendProducts")
            .get()
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                        ProductModel rcmProductModel = document.toObject(ProductModel.class);
                        rcmProductsList.add(rcmProductModel);
                        recommendedAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(getActivity(), "Error: " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            });
    }
}