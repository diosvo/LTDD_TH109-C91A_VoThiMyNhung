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
import com.dv.grocery.adapters.PopularAdapters;
import com.dv.grocery.models.PopularProductModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {
    RecyclerView popularRec;
    FirebaseFirestore db;

    List<PopularProductModel> popularProductModelList;
    PopularAdapters popularAdapters;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        db = FirebaseFirestore.getInstance();
        popularRec = root.findViewById(R.id.popular_products);

        popularRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        popularProductModelList = new ArrayList<>();
        popularAdapters = new PopularAdapters(getActivity(), popularProductModelList);
        popularRec.setAdapter(popularAdapters);

        db.collection("PopularProducts")
            .get()
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                        PopularProductModel popularProductModel = document.toObject(PopularProductModel.class);
                        popularProductModelList.add(popularProductModel);
                        popularAdapters.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(getActivity(), "Error: " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            });

        return root;
    }
}