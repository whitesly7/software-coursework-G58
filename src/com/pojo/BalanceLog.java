package com.pojo;

import com.annotation.JsonId;

public class BalanceLog {
    @JsonId
    private String bid;      // Unique identifier for the balance log entry.
    private String childName; // Name of the child associated with the balance log.
    private String type;     // Type of transaction (e.g., "withdraw", "save").
    private Double amount;   // Amount of money involved in the transaction.
    private String time;     // Timestamp of when the transaction occurred.
    private String cid;      // Identifier for the child.

    /**
     * Constructs an empty BalanceLog.
     */
    public BalanceLog() {
    }

    /**
     * Constructs a BalanceLog with specified details.
     *
     * @param bid       the unique identifier for the balance log entry.
     * @param childName the name of the child.
     * @param type      the type of transaction.
     * @param amount    the amount involved in the transaction.
     * @param time      the timestamp of the transaction.
     * @param cid       the identifier of the child.
     */
    public BalanceLog(String bid, String childName, String type, Double amount, String time, String cid) {
        this.bid = bid;
        this.childName = childName;
        this.type = type;
        this.amount = amount;
        this.time = time;
        this.cid = cid;
    }
    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
