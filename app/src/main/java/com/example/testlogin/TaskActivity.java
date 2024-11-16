package com.example.testlogin;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class TaskActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private ListView listViewTasks;
    private Button btnAddTask;
    private List<Task> taskList;
    private TaskAdapter taskAdapter;
    private int userId;  // Thêm biến userId

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        dbHelper = new DatabaseHelper(this);
        listViewTasks = findViewById(R.id.listViewTasks);
        btnAddTask = findViewById(R.id.btnAddTask);

        // Lấy userId từ Intent
        userId = getIntent().getIntExtra("user_id", -1);  // Lấy user_id từ Intent

        if (userId == -1) {
            Toast.makeText(this, "Lỗi: Không có user_id", Toast.LENGTH_SHORT).show();
            return;
        }

        // Initialize task list and adapter
        taskList = new ArrayList<>();
        taskAdapter = new TaskAdapter(this, taskList);
        listViewTasks.setAdapter(taskAdapter);

        // Load tasks from database
        loadTasks(userId);

        // Button to add a new task
        btnAddTask.setOnClickListener(v -> {
            Intent intent = new Intent(TaskActivity.this, AddTaskActivity.class);
            intent.putExtra("user_id", userId);  // Truyền userId khi thêm nhiệm vụ
            startActivity(intent);
        });

        // Handle task item click (optional)
        listViewTasks.setOnItemClickListener((parent, view, position, id) -> {
            Task task = taskList.get(position);
            Toast.makeText(this, "Chọn: " + task.getTaskName(), Toast.LENGTH_SHORT).show();
            // Add logic to edit or delete the task if needed
        });
    }

    public void loadTasks(int userId) {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tasks WHERE user_id_fk = ?", new String[]{String.valueOf(userId)});

        taskList.clear();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex("task_id"));
                String name = cursor.getString(cursor.getColumnIndex("task_name"));
                String description = cursor.getString(cursor.getColumnIndex("task_description"));
                taskList.add(new Task(id, name, description));
            }
            cursor.close();
        }
        taskAdapter.notifyDataSetChanged();
    }
}
