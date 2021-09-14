package com.dv.grocery;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button register, login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initValues();
        onLogin();
        onRegister();
    }

    void initValues() {
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