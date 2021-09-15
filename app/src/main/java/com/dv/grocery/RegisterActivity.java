package com.dv.grocery;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dv.grocery.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    Button register;
    EditText fullName, email, password;
    TextView login;

    FirebaseAuth auth;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initValues();
        onRegister();
        onLogin();
    }

    private void initValues() {
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        register = findViewById(R.id.btn_register);
        login = findViewById(R.id.txt_login);

        fullName = findViewById(R.id.full_name);
        email = findViewById(R.id.email_register);
        password = findViewById(R.id.password_register);
    }

    private void onLogin() {
        login.setOnClickListener(view -> startActivity(new Intent(RegisterActivity.this, LoginActivity.class)));
    }

    private void onRegister() {
        register.setOnClickListener(view -> createUser());
    }

    private void createUser() {
        String full_name = fullName.getText().toString();
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

        if (TextUtils.isEmpty((full_name))) {
            Toast.makeText(this, "Full Name is required.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty((userEmail))) {
            Toast.makeText(this, "Email is required.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check password length
        if (TextUtils.isEmpty((userPassword))) {
            Toast.makeText(this, "Password is required.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (userPassword.length() < 6) {
            Toast.makeText(this, "Password must be at least 6 characters.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create User
        auth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                UserModel user = new UserModel(full_name, userEmail, userPassword);
                String id = task.getResult().getUser().getUid();
                database.getReference().child("Users").child(id).setValue(user);

                Toast.makeText(RegisterActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(RegisterActivity.this, "Error:" + task.getException(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}