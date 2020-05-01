package com.tasker.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author mtolstyh
 * @since 19.02.2016.
 */
public class LocalStorage {

    private static final List<Task> taskList = new ArrayList<>();

    public static List<Task> getTaskList() {
        return Collections.unmodifiableList(taskList);
    }

    public static void setTaskList(List<Task> taskListInput) {
        taskList.clear();
        if (taskListInput != null) {
            taskList.addAll(taskListInput);
        }
    }

    public static void put(Task task) {
        taskList.add(task);
    }
}
