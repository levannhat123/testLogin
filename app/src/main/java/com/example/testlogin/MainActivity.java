package com.example.testlogin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText edtUsername, edtPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        edtUsername = findViewById(R.id.edtUsername1);
        edtPassword = findViewById(R.id.edtPassword2);
        btnLogin = findViewById(R.id.btnRegister3);

        // Khi người dùng nhấn nút đăng nhập
        btnLogin.setOnClickListener(v -> {
            String username = edtUsername.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(MainActivity.this, "Vui lòng nhập tên đăng nhập và mật khẩu", Toast.LENGTH_SHORT).show();
                return;
            }

            loginUser(username, password);  // Gọi phương thức loginUser
        });
    }

    // Phương thức đăng nhập
    public void loginUser(String username, String password) {
        int userId = dbHelper.login(username, password);
        if (userId != -1) {
            // Đăng nhập thành công, chuyển đến TaskActivity
            Intent intent = new Intent(MainActivity.this, TaskActivity.class);
            intent.putExtra("user_id", userId);  // Truyền user_id qua Intent
            startActivity(intent);
            finish();  // Đóng LoginActivity
        } else {
            Toast.makeText(MainActivity.this, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
        }
    }
}
