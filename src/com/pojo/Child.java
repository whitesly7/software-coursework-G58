package com.pojo;

import com.annotation.JsonId;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Represents a child's account and related information.
 */
public class Child {
    private String childName;
    private String password;
    //活期存款
    private Double currentDeposit;
    //定期存款
    private Double savingDeposit;
    //父母性别
    private String parentName;

    private String goal;
    @JsonId
    private String cid;
    private String pid;

    /**
     * Constructs a new Child instance with specified details.
     *
     * @param childName      the child's name
     * @param password       the password for the child's account
     * @param currentDeposit the current deposit amount
     * @param savingDeposit  the saving deposit amount
     * @param parentName     the parent's name
     * @param cid            the child's unique identifier
     * @param pid            the parent's identifier
     */
    public Child(String childName, String password, Double currentDeposit, Double savingDeposit, String parentName, String cid, String pid) {
        this.childName = childName;
        this.password = password;
        this.currentDeposit = currentDeposit;
        this.savingDeposit = savingDeposit;
        this.parentName = parentName;
        this.cid = cid;
        this.pid = pid;
    }


    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    /**
     * Creates an instance of Child with no initial details.
     */
    public Child() {}

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getCurrentDeposit() {
        return currentDeposit;
    }

    public void setCurrentDeposit(Double currentDeposit) {
        this.currentDeposit = currentDeposit;
        fireDepositChanged(currentDeposit);
    }

    public Double getSavingDeposit() {
        return savingDeposit;
    }

    public void setSavingDeposit(Double savingDeposit) {
        this.savingDeposit = savingDeposit;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    /**
     * Returns a string representation of the Child object.
     * @return a string representation of the Child object.
     */
    @Override
    public String toString() {
        return "Child{" +
                "childName='" + childName + '\'' +
                ", password='" + password + '\'' +
                ", currentDeposit=" + currentDeposit +
                ", savingDeposit=" + savingDeposit +
                ", parentName='" + parentName + '\'' +
                ", goal='" + goal + '\'' +
                ", cid='" + cid + '\'' +
                ", pid='" + pid + '\'' +
                '}';
    }

    private List<Consumer<Double>> depositChangeListeners = new ArrayList<>();

    /**
     * Adds a listener that is notified when the current deposit changes.
     *
     * @param listener the consumer to notify
     */
    public void addDepositChangeListener(Consumer<Double> listener) {
        depositChangeListeners.add(listener);
    }

    /**
     * Removes a listener from the notification list.
     *
     * @param listener the consumer to remove
     */
    public void removeDepositChangeListener(Consumer<Double> listener) {
        depositChangeListeners.remove(listener);
    }

    /**
     * Notifies all registered listeners about a change in the deposit.
     *
     * @param newDeposit the new deposit amount
     */
    protected void fireDepositChanged(double newDeposit) {
        for (Consumer<Double> listener : depositChangeListeners) {
            listener.accept(newDeposit);
        }
    }

}
