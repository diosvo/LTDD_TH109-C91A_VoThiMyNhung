package com.dv.grocery;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dv.grocery.activities.PlacedOrderActivity;
import com.dv.grocery.adapters.CartAdapter;
import com.dv.grocery.models.CartModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MyCartFragment extends Fragment {
    FirebaseFirestore db;
    FirebaseAuth auth;

    TextView total;
    Button payment;
    ProgressBar progressBar;
    LinearLayout cart_bottom_bar;

    RecyclerView recyclerView;
    CartAdapter cartAdapter;
    List<CartModel> cartModelList;

    public MyCartFragment() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_my_cart, container, false);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        recyclerView = root.findViewById(R.id.rec_cart_item);
        progressBar = root.findViewById(R.id.cart_progress_bar);
        cart_bottom_bar = root.findViewById(R.id.cart_bottom_bar);
        payment = root.findViewById(R.id.cart_payment);

        showProgressBar();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        cartModelList = new ArrayList<>();
        cartAdapter = new CartAdapter(getActivity(), cartModelList);
        recyclerView.setAdapter(cartAdapter);

        total = root.findViewById(R.id.cart_total_price);

        db.collection("CurrentUser")
            .document(auth.getCurrentUser().getUid())
            .collection("AddToCart")
            .get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (DocumentSnapshot documentSnapshot : task.getResult()) {
                    CartModel cartModel = documentSnapshot.toObject(CartModel.class);
                    String documentId = documentSnapshot.getId();

                    cartModel.setDocumentId(documentId);
                    cartModelList.add(cartModel);
                    cartAdapter.notifyDataSetChanged();
                    hideProgressBar();
                }

                calculateTotal(cartModelList);
            }
        });
        Pay();

        return root;
    }

    private void calculateTotal(List<CartModel> cartModelList) {
        int totalAmount = 0;
        for (CartModel model : cartModelList) {
            totalAmount += model.getTotalPrice();
        }

        total.setText("" + totalAmount);
    }

    private void Pay() {
        payment.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), PlacedOrderActivity.class);
            intent.putExtra("productList", (Serializable) cartModelList);
            startActivity(intent);
        });
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        cart_bottom_bar.setVisibility(View.GONE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        cart_bottom_bar.setVisibility(View.VISIBLE);
    }
}