package com.pojo;

import com.annotation.JsonId;

/**
 * Represents a task entity with details about the task and associated parent.
 */
public class Task {
    @JsonId
    private String tid;
    private String taskName;
    private Double moneyReward;
    private String parentName;

    private String pid;

    /**
     * Constructs an empty Task object.
     */
    public Task() {
    }

    /**
     * Constructs a Task with specified details.
     *
     * @param tid         The task identifier.
     * @param taskName    The name of the task.
     * @param moneyReward The monetary reward for completing the task.
     * @param parentName  The name of the parent who assigned the task.
     * @param pid         The parent identifier.
     */
    public Task(String tid, String taskName, Double moneyReward, String parentName, String pid) {
        this.tid = tid;
        this.taskName = taskName;
        this.moneyReward = moneyReward;
        this.parentName = parentName;
        this.pid = pid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }


    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Double getMoneyReward() {
        return moneyReward;
    }

    public void setMoneyReward(Double moneyReward) {
        this.moneyReward = moneyReward;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}
