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
import com.dv.grocery.models.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    Button register;
    EditText fullName, email, password;
    TextView login;

    FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressBar progressBar;

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

        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

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
        register.setOnClickListener(view -> {
            createUser();
            if (TextUtils.isEmpty(fullName.getText().toString())
                && TextUtils.isEmpty(email.getText().toString())
                && TextUtils.isEmpty(password.getText().toString())
            ) {
                progressBar.setVisibility((View.VISIBLE));
            }
        });
    }

    private void createUser() {
        String full_name = fullName.getText().toString();
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

        if (TextUtils.isEmpty((full_name))) {
            Toast.makeText(this, "Trường Tên của bạn là bắt buộc.", Toast.LENGTH_SHORT).show();
            return;
        }

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

        // Create User
        auth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                UserModel userModel = new UserModel(full_name, userEmail, userPassword);
                String id = task.getResult().getUser().getUid();
                database.getReference().child("Users").child(id).setValue(userModel);

                progressBar.setVisibility((View.GONE));
                Toast.makeText(RegisterActivity.this, "Bạn đã đăng ký thành công!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            } else {
                progressBar.setVisibility((View.GONE));
                Toast.makeText(RegisterActivity.this, "Đã có lỗi xảy ra: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
