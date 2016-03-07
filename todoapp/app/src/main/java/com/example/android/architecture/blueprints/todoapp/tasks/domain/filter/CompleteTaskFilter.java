package com.example.android.architecture.blueprints.todoapp.tasks.domain.filter;

import com.example.android.architecture.blueprints.todoapp.data.Task;

import java.util.ArrayList;
import java.util.List;

class CompleteTaskFilter implements TaskFilter {
    @Override
    public List<Task> filter(List<Task> tasks) {
        List<Task> filteredTasks = new ArrayList<>();

        for (Task task : tasks) {
            if (task.isCompleted()) {
                filteredTasks.add(task);
            }
        }
        return filteredTasks;
    }
}
