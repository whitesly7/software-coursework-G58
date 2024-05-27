package com.dao;

import com.pojo.TaskWorkLog;

/**
 * Data access object for handling operations on {@link TaskWorkLog} entities.
 */
public class TaskWorkLogDao<T>  extends BaseDao<TaskWorkLog>{
    /**
     * Constructs a TaskWorkLogDao for managing TaskWorkLog entities.
     * Initializes the DAO with "TaskWorkLog.json" as the JSON file for data storage.
     */
    public TaskWorkLogDao() {
        super("TaskWorkLog.json");
    }
}
