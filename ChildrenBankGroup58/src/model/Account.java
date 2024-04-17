package model;

import java.util.List;

public class Account {
    // Attributes
    private int accountId;
    private String accountType;
    private double balance;
    
    // Association with Transaction (one-to-many)
    private List<Transaction> transactions;
    
    // Methods
    public void deposit(double amount) { /* implementation */ }
    public void withdraw(double amount) { /* implementation */ }
    public double getBalance() {
		return accountId; /* implementation */ }
}
