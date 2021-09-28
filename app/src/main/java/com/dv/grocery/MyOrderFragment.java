package com.dv.grocery;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dv.grocery.adapters.OrderAdapter;
import com.dv.grocery.models.CartModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MyOrderFragment extends Fragment {

    private static final String TAG = "MyOrderFragment";
    FirebaseFirestore db;
    FirebaseAuth auth;
    ProgressBar progressBar;
    RecyclerView orderRec;
    OrderAdapter orderAdapter;
    List<CartModel> modelList;

    public MyOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_my_order, container, false);

        orderRec = root.findViewById(R.id.rec_order_item);
        progressBar = root.findViewById(R.id.order_progress_bar);
        showProgressBar();

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        orderRec.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        modelList = new ArrayList<>();
        orderAdapter = new OrderAdapter(getActivity(), modelList);
        orderRec.setAdapter(orderAdapter);

        db.collection("CurrentUser")
            .document(auth.getCurrentUser().getUid())
            .collection("MyOrder")
            .get()
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        CartModel orderModel = document.toObject(CartModel.class);
                        modelList.add(orderModel);
                        orderAdapter.notifyDataSetChanged();
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
        orderRec.setVisibility(View.GONE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        orderRec.setVisibility(View.VISIBLE);
    }
}