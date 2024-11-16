package com.example.testlogin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "UserTasks.db";
    private static final int DATABASE_VERSION = 1;

    // Table for user registration
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";

    // Table for tasks
    private static final String TABLE_TASKS = "tasks";
    private static final String COLUMN_TASK_ID = "task_id";
    private static final String COLUMN_TASK_NAME = "task_name";
    private static final String COLUMN_TASK_DESCRIPTION = "task_description";
    private static final String COLUMN_USER_ID_FK = "user_id_fk";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create users table
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_PASSWORD + " TEXT)";
        db.execSQL(CREATE_USERS_TABLE);

        // Create tasks table
        String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_TASKS + "("
                + COLUMN_TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TASK_NAME + " TEXT,"
                + COLUMN_TASK_DESCRIPTION + " TEXT,"
                + COLUMN_USER_ID_FK + " INTEGER,"
                + "FOREIGN KEY(" + COLUMN_USER_ID_FK + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + "))";
        db.execSQL(CREATE_TASKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }
    // User registration
    public boolean registerUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        long result = db.insert(TABLE_USERS, null, values);
        return result != -1; // Return true if inserted successfully
    }

    // User login
    public int login(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT user_id FROM users WHERE username = ? AND password = ?",
                new String[]{username, password});

        if (cursor != null && cursor.moveToFirst()) {
            int userId = cursor.getInt(cursor.getColumnIndex("user_id"));
            cursor.close();
            return userId;  // Trả về user_id nếu đăng nhập thành công
        }
        cursor.close();
        return -1;  // Trả về -1 nếu không tìm thấy người dùng
    }


    // Add task
    public boolean addTask(String taskName, String taskDescription, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_NAME, taskName);
        values.put(COLUMN_TASK_DESCRIPTION, taskDescription);
        values.put(COLUMN_USER_ID_FK, userId);
        long result = db.insert(TABLE_TASKS, null, values);
        return result != -1;
    }

    // Update task
    public boolean updateTask(int taskId, String taskName, String taskDescription) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK_NAME, taskName);
        values.put(COLUMN_TASK_DESCRIPTION, taskDescription);
        int result = db.update(TABLE_TASKS, values, COLUMN_TASK_ID + "=?",
                new String[]{String.valueOf(taskId)});
        return result > 0;
    }

    // Delete task
    public boolean deleteTask(int taskId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_TASKS, COLUMN_TASK_ID + "=?",
                new String[]{String.valueOf(taskId)});
        return result > 0;
    }

    // Get all tasks for a specific user
    public Cursor getTasks(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_TASKS, null, COLUMN_USER_ID_FK + "=?",
                new String[]{String.valueOf(userId)}, null, null, null);
    }
    // Add methods for registration, login, task management (code provided earlier)
}