package com.example.testlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class sigup extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText edtUsername, edtPassword;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sigup);

        dbHelper = new DatabaseHelper(this);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> {
            String username = edtUsername.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(sigup.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            } else {
                boolean isRegistered = dbHelper.registerUser(username, password);
                if (isRegistered) {
                    Toast.makeText(sigup.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(sigup.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(sigup.this, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}