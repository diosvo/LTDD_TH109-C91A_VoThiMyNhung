package com.dv.grocery.ui.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dv.grocery.R;
import com.dv.grocery.adapters.NavCategoryAdapter;
import com.dv.grocery.models.CategoryModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CategoryFragment extends Fragment {
    RecyclerView categoryRec;
    List<CategoryModel> categoryList;
    NavCategoryAdapter categoryAdapter;

    FirebaseFirestore db;
    ProgressBar progressBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_category, container, false);

        categoryRec = root.findViewById(R.id.nav_rec_category);
        progressBar = root.findViewById(R.id.nav_category_progress_bar);
        showProgressBar();

        db = FirebaseFirestore.getInstance();

        categoryRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        categoryList = new ArrayList<>();
        categoryAdapter = new NavCategoryAdapter(getActivity(), categoryList);
        categoryRec.setAdapter(categoryAdapter);

        db.collection(getString(R.string.nav_category))
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
                    Toast.makeText(getActivity(), "Đã có lỗi xảy ra: " + task.getException(), Toast.LENGTH_LONG).show();
                }
            });

        return root;
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        categoryRec.setVisibility(View.GONE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        categoryRec.setVisibility(View.VISIBLE);
    }
}