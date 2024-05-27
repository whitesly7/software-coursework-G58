package com.pojo;

import com.annotation.JsonId;

/**
 * Represents a parent entity with associated properties.
 * This class models a parent's basic information including their unique identifier,
 * name, and password.
 */
public class Parent {

    /**
     * Constructs an empty Parent instance.
     */
    public Parent() {
    }

    /**
     * Constructs a Parent instance with specified details.
     *
     * @param pid        The unique identifier for the parent.
     * @param parentName The name of the parent.
     * @param password   The password of the parent.
     */
    public Parent(String pid, String parentName, String password) {
        this.pid = pid;
        this.parentName = parentName;
        this.password = password;
    }
    @JsonId
    private String pid;
    private String parentName;

    private String password;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
