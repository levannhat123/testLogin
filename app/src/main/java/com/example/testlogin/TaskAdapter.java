package com.example.testlogin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task> {

    public TaskAdapter(@NonNull Context context, @NonNull List<Task> tasks) {
        super(context, 0, tasks);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_task, parent, false);
        }

        Task task = getItem(position);

        TextView txtTaskName = convertView.findViewById(R.id.txtTaskName);
        TextView txtTaskDescription = convertView.findViewById(R.id.txtTaskDescription);

        if (task != null) {
            txtTaskName.setText(task.getTaskName());
            txtTaskDescription.setText(task.getTaskDescription());
        }

        return convertView;
    }}