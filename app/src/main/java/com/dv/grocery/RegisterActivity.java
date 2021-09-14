package com.dv.grocery;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    Button register;
    EditText fullName, email, password;
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initValues();
        onRegister();
        onLogin();
    }

    void initValues() {
        register = findViewById(R.id.btn_register);
        login = findViewById(R.id.txt_login);

        fullName = findViewById(R.id.full_name);
        email = findViewById(R.id.email_register);
        password = findViewById(R.id.password_register);
    }

    void onLogin() {
        login.setOnClickListener(view -> startActivity(new Intent(RegisterActivity.this, LoginActivity.class)));
    }

    void onRegister() {
        register.setOnClickListener(view -> {

        });
    }
}