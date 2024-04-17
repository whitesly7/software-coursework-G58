package model;

public class User {
    // Attributes
    private int userId;
    private String userName;
    private String phoneNum;
    private String password;
    
    // Association with Account and Task
    private Account account;
    private Task task;
    
    // Methods
    public void login() { /* implementation */ }
    public void logout() { /* implementation */ }
    public void createAccount() { /* implementation */ }
    public String getUserName() {
		return password; /* implementation */ }
    public String getPhoneNum() {
		return password; /* implementation */ }
}
