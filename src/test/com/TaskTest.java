package com;

import com.pojo.Task;
import com.service.TaskService;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.UUID;

public class TaskTest {
    public TaskService taskService =new TaskService();

    /**
     * search: wrong value
     * @throws IOException
     * @throws IllegalAccessException
     */

    @Test
    public void  Testseach() throws IOException, IllegalAccessException {
        taskService.getListByKey("taskName","1212");
    }

    /**
     * search: correct value
     * @throws IOException
     * @throws IllegalAccessException
     */
    @Test
    public void  Testseach2() throws IOException, IllegalAccessException {
        taskService.getListByKey("taskName","1");
    }
    /**
     * search: null
     * @throws IOException
     * @throws IllegalAccessException
     */
    @Test
    public void  Testseach3() throws IOException, IllegalAccessException {
        taskService.getListByKey("taskName",null);
    }

    @Test
    public void  TestAdd() throws IOException, IllegalAccessException {
        Task task = new Task();
        task.setTaskName("1");
        task.setMoneyReward(111111111111111.0);
        task.setTid(UUID.randomUUID().toString());
        task.setParentName("123456");
        taskService.save(task);
    }

    @Test
    public void  TestAdd2() throws IOException, IllegalAccessException {
        Task task = new Task();
        task.setTaskName("1");
        task.setMoneyReward(111111111111111.0);
        task.setTid(UUID.randomUUID().toString());
        task.setParentName("");
        taskService.save(task);
    }

}
