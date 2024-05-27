package com.service;

import com.dao.BaseDao;
import com.dao.TaskDao;
import com.pojo.Task;

/**
 * Service layer for handling business logic related to tasks.
 * This class extends {@link TaskDao}, inheriting its database access methods to interact with Task objects.
 */
public class TaskService extends TaskDao<Task> {

}
