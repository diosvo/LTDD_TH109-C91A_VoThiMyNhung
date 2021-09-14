package com.dv.grocery;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    Button login;
    EditText email, password;
    TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initValues();
        onLogin();
        onRegister();
    }

    private void initValues() {
        login = findViewById(R.id.btn_login);
        register = findViewById(R.id.txt_register);

        email = findViewById(R.id.email_register);
        password = findViewById(R.id.password_register);
    }


    void onRegister() {
        register.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));
    }

    void onLogin() {
        login.setOnClickListener(view -> {
        });
    }
}