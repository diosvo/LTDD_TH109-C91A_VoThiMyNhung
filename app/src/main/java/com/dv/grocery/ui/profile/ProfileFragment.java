package com.dv.grocery.ui.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.dv.grocery.R;
import com.dv.grocery.models.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    EditText name, email, address, phone;
    CircleImageView image;
    Button updateBtn;

    FirebaseDatabase db;
    FirebaseAuth auth;
    FirebaseStorage storage;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        db = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();

        init(root);
        uploadImage();
        onUpdateProfile();
        updateFromDatabase();

        return root;
    }

    private void init(View root) {
        image = root.findViewById(R.id.profile_image);
        updateBtn = root.findViewById(R.id.profile_update_btn);

        name = root.findViewById(R.id.profile_name);
        email = root.findViewById(R.id.profile_email);
        phone = root.findViewById(R.id.profile_phone);
        address = root.findViewById(R.id.profile_address);
    }

    private void uploadImage() {
        image.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, 33);
        });
    }

    private void onUpdateProfile() {
        updateBtn.setOnClickListener(view -> {

        });
    }

    private void updateFromDatabase() {
        db.getReference()
            .child("Users")
            .child(auth.getUid())
            .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    UserModel userModel = snapshot.getValue(UserModel.class);
                    Glide.with(getContext()).load(userModel.getProfileImage()).into(image);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data.getData() != null) {
            Uri profileUri = data.getData();
            image.setImageURI(profileUri);

            final StorageReference storageRef = storage.getReference()
                .child("profile_image")
                .child(auth.getUid());

            storageRef.putFile(profileUri).addOnSuccessListener(taskSnapshot ->
                storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    db.getReference()
                        .child("Users")
                        .child(auth.getUid())
                        .child("profileImage")
                        .setValue(uri.toString());

                    Toast.makeText(getContext(), "Hình ảnh đã được cập nhật.", Toast.LENGTH_SHORT).show();
                }));
        }
    }
}