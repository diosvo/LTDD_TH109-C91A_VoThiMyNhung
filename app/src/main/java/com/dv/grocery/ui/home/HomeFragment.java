package com.dv.grocery.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dv.grocery.R;
import com.dv.grocery.adapters.HomeCategoryAdapter;
import com.dv.grocery.adapters.PopularAdapter;
import com.dv.grocery.adapters.RecommendedAdapter;
import com.dv.grocery.adapters.ViewAllAdapter;
import com.dv.grocery.models.CategoryModel;
import com.dv.grocery.models.ProductModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {
    ScrollView scrollView;
    ProgressBar progressBar;

    RecyclerView popularRec, categoryRec, recommendedRec;
    FirebaseFirestore db;

    // Popular Products
    List<ProductModel> popularProductsList;
    PopularAdapter popularAdapter;

    // Category
    List<CategoryModel> categoryList;
    HomeCategoryAdapter categoryAdapter;

    // Recommend Products
    List<ProductModel> rcmProductsList;
    RecommendedAdapter recommendedAdapter;

    // Search View
    EditText searchText;
    private List<ProductModel> searchList;
    private RecyclerView searchRec;
    private ViewAllAdapter viewAllAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        popularRec = root.findViewById(R.id.popular_products);
        categoryRec = root.findViewById(R.id.home_category);
        recommendedRec = root.findViewById(R.id.recommend_products);

        scrollView = root.findViewById(R.id.home_scroll_view);
        progressBar = root.findViewById(R.id.home_progress_bar);
        showProgressBar();

        db = FirebaseFirestore.getInstance();

        getPopularProducts();
        getHomeCategory();
        getRecommendProducts();
        searchProducts(root);

        return root;
    }

    private void getPopularProducts() {
        popularRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        popularProductsList = new ArrayList<>();
        popularAdapter = new PopularAdapter(getActivity(), popularProductsList);
        popularRec.setAdapter(popularAdapter);

        db.collection(getString(R.string.popular_products))
            .get()
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                        ProductModel popularProductModel = document.toObject(ProductModel.class);
                        popularProductsList.add(popularProductModel);
                        popularAdapter.notifyDataSetChanged();
                        hideProgressBar();
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

        db.collection(getString(R.string.home_category))
            .get()
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                        CategoryModel categoryModel = document.toObject(CategoryModel.class);
                        categoryList.add(categoryModel);
                        categoryAdapter.notifyDataSetChanged();
                        hideProgressBar();
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

        db.collection(getString(R.string.recommended_products))
            .get()
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                        ProductModel rcmProductModel = document.toObject(ProductModel.class);
                        rcmProductsList.add(rcmProductModel);
                        recommendedAdapter.notifyDataSetChanged();
                        hideProgressBar();
                    }
                } else {
                    Toast.makeText(getActivity(), "Error: " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            });
    }

    private void searchProducts(View root) {
        searchText = root.findViewById(R.id.search_box);
        searchRec = root.findViewById(R.id.search_rec);
        searchList = new ArrayList<>();
        viewAllAdapter = new ViewAllAdapter(getContext(), searchList);

        searchRec.setLayoutManager(new LinearLayoutManager(getContext()));
        searchRec.setAdapter(viewAllAdapter);
        searchRec.setHasFixedSize(true);

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String name = editable.toString();
                if (name.isEmpty()) {
                    searchList.clear();
                    viewAllAdapter.notifyDataSetChanged();
                } else {
                    if (!name.isEmpty()) {
                        db.collection(getString(R.string.popular_products))
                            .whereEqualTo("group", name)
                            .get()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful() && task.getResult() != null) {
                                    searchList.clear();
                                    viewAllAdapter.notifyDataSetChanged();

                                    for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                                        ProductModel productModel = doc.toObject(ProductModel.class);
                                        searchList.add(productModel);
                                        viewAllAdapter.notifyDataSetChanged();
                                    }
                                }
                            });
                    }
                }
            }
        });
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        scrollView.setVisibility(View.VISIBLE);
    }
}