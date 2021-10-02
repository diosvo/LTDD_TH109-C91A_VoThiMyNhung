package com.dv.grocery.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dv.grocery.R;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    Button login;
    EditText email, password;
    TextView register;

    FirebaseAuth auth;
    ProgressBar progressBar;

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
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        login = findViewById(R.id.btn_login);
        register = findViewById(R.id.txt_register);

        email = findViewById(R.id.email_login);
        password = findViewById(R.id.password_login);
    }

    private void onRegister() {
        register.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));
    }

    private void onLogin() {
        login.setOnClickListener(view -> {
            loginUser();
            if (TextUtils.isEmpty(email.getText().toString())
                && TextUtils.isEmpty(password.getText().toString())
            ) {
                progressBar.setVisibility((View.VISIBLE));
            }
        });
    }

    private void loginUser() {
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

        if (TextUtils.isEmpty((userEmail))) {
            Toast.makeText(this, "Trường Email là bắt buộc.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check password length
        if (TextUtils.isEmpty((userPassword))) {
            Toast.makeText(this, "Trường Mật khẩu là bắt buộc.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (userPassword.length() < 6) {
            Toast.makeText(this, "Mật khẩu phải có tối thiểu 6 ký tự.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Login
        auth.signInWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "Đã có lỗi xảy ra: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }
}
