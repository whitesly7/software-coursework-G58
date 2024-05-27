package com.dao;

import com.pojo.Task;

/**
 * Data access object for managing Task entities.
 * Extends generic BaseDao for Task-specific functionality.
 */
public class TaskDao<T>  extends BaseDao<Task>{
    /**
     * Constructs a TaskDao with a predefined JSON file path.
     */
    public TaskDao() {
        super("Task.json");
    }
}
