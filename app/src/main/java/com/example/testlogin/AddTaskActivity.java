package com.example.testlogin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddTaskActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText edtTaskName, edtTaskDescription;
    private Button btnSaveTask;
    private int userId;  // Thêm biến để lưu user_id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        dbHelper = new DatabaseHelper(this);
        edtTaskName = findViewById(R.id.edtTaskName);
        edtTaskDescription = findViewById(R.id.edtTaskDescription);
        btnSaveTask = findViewById(R.id.btnSaveTask);

        // Nhận user_id từ Intent
        userId = getIntent().getIntExtra("user_id", -1);

        if (userId == -1) {
            Toast.makeText(this, "Không xác định được người dùng!", Toast.LENGTH_SHORT).show();
            return;
        }

        btnSaveTask.setOnClickListener(v -> {
            String taskName = edtTaskName.getText().toString().trim();
            String taskDescription = edtTaskDescription.getText().toString().trim();

            if (taskName.isEmpty() || taskDescription.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean isTaskAdded = dbHelper.addTask(taskName, taskDescription, userId);
            if (isTaskAdded) {
                Toast.makeText(this, "Thêm Task thành công!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddTaskActivity.this, TaskActivity.class);
                intent.putExtra("user_id", userId);  // Truyền user_id khi quay lại TaskActivity
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Thêm Task thất bại!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
