package com.dv.grocery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    Button register, login;
    ProgressBar progressBar;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initValues();
        onLogin();
        onRegister();
    }

    void initValues() {
        auth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        if (auth.getCurrentUser() != null) {
            progressBar.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Please wait! You are already logged in.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
            finish();
        }

        register = findViewById(R.id.btn_register_welcome);
        login = findViewById(R.id.btn_login_welcome);
    }

    void onLogin() {
        login.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, LoginActivity.class)));
    }

    void onRegister() {
        register.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, RegisterActivity.class)));
    }
}