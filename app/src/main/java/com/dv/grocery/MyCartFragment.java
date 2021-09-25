package com.dv.grocery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dv.grocery.adapters.CartAdapter;
import com.dv.grocery.models.CartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MyCartFragment extends Fragment {
    FirebaseFirestore db;
    FirebaseAuth auth;

    TextView total;
    ProgressBar progressBar;
    LinearLayout cart_bottom_bar;

    RecyclerView recyclerView;
    CartAdapter cartAdapter;
    List<CartModel> cartModelList;

    public MyCartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_my_cart, container, false);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        recyclerView = root.findViewById(R.id.rec_cart_item);
        progressBar = root.findViewById(R.id.cart_progress_bar);
        cart_bottom_bar = root.findViewById(R.id.cart_bottom_bar);
        showProgressBar();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        cartModelList = new ArrayList<>();
        cartAdapter = new CartAdapter(getActivity(), cartModelList);
        recyclerView.setAdapter(cartAdapter);

        total = root.findViewById(R.id.cart_total_price);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(MessageReceiver, new IntentFilter("totalAmount"));

        db.collection("AddToCart")
            .document(auth.getCurrentUser().getUid())
            .collection("CurrentUser")
            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        CartModel cartModel = documentSnapshot.toObject(CartModel.class);
                        cartModelList.add(cartModel);
                        cartAdapter.notifyDataSetChanged();
                        hideProgressBar();
                    }
                }
            }
        });

        return root;
    }

    private BroadcastReceiver MessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int totalBill = intent.getIntExtra("totalAmount", 0);
            total.setText("" + totalBill);
        }
    };

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