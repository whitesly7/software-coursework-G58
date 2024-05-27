package com.service;

import com.dao.TaskWorkLogDao;
import com.pojo.TaskWorkLog;

/**
 * Service class for managing task work logs.
 * This class extends TaskWorkLogDao to utilize its data access methods specifically for TaskWorkLog objects.
 *
 * Use this service class to interface between the application and the data layer handling TaskWorkLog entities.
 * It inherits common data access operations from TaskWorkLogDao and can be customized with additional behaviors
 * needed specifically for task work logs management.
 */
public class TaskWorkLogService extends TaskWorkLogDao<TaskWorkLog> {
}
