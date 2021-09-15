package com.dv.grocery;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    Button login;
    EditText email, password;
    TextView register;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initValues();
        onLogin();
        onRegister();
    }

    private void initValues() {
        auth = FirebaseAuth.getInstance();

        login = findViewById(R.id.btn_login);
        register = findViewById(R.id.txt_register);

        email = findViewById(R.id.email_register);
        password = findViewById(R.id.password_register);
    }

    private void onRegister() {
        register.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));
    }

    private void onLogin() {
        login.setOnClickListener(view -> {
            loginUser();
        });
    }

    private void loginUser() {
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

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

        // Login
        auth.signInWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this, "Error" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
    }
}