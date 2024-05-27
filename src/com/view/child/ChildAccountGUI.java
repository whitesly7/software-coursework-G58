package com.view.child;

import com.pojo.Child;
import com.pojo.Parent;
import com.service.ChildService;
import com.service.ParentService;

import javax.swing.*;
import java.awt.*;  
import java.awt.event.ActionEvent;  
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.UUID;

/**
 * This class represents the graphical user interface (GUI) for child account Login and Register.
 */
public class ChildAccountGUI {  
  
    private JFrame frame;  
    private JTextField childNameField;  
    private JTextField passwordField;
    private JTextField parentIdField;
    private JButton registerButton;  
    private JButton loginButton;
    private ChildService childService;
    private ParentService parentService;

    /**
     * Launches the application.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {  
        EventQueue.invokeLater(new Runnable() {  
            public void run() {  
                try {  
                    ChildAccountGUI window = new ChildAccountGUI();  
                    window.frame.setVisible(true);  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }  
        });  
    }

    /**
     * Constructs the child account GUI.
     */
    public ChildAccountGUI() {  
        initialize();  
    }

    /**
     * Initializes the GUI components.
     */
    private void initialize() {
        parentService=new ParentService();
        childService =new ChildService();
        frame = new JFrame();  
        frame.setBounds(100, 100, 450, 300);  
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        frame.setLayout(new BorderLayout());  

        JPanel panel = new JPanel();  
        frame.add(panel, BorderLayout.CENTER);  
  
        childNameField = new JTextField(10);  
        passwordField = new JPasswordField(10);
        parentIdField = new JTextField(10);
  
        JLabel childNameLabel = new JLabel("Child Name:");  
        JLabel passwordLabel = new JLabel("Password:");  
        JLabel parentIdLabel = new JLabel("Parent ID:");
  
        registerButton = new JButton("Register");  
        loginButton = new JButton("Login");  
  
        // add actionlistener for register button
        registerButton.addActionListener(new ActionListener() {  
            @Override  
            public void actionPerformed(ActionEvent e) {  
                // add register logics
                String childName = childNameField.getText();
                String password = passwordField.getText();
                String pid = parentIdField.getText();
                String parentName;
                if(pid.equals("")||password.equals("")||childName.equals("")){
                    JOptionPane.showMessageDialog(frame, "You should enter full information!");
                }else {
                    try {
                        Parent one = parentService.getOneByKey("pid", pid);
                        if (one == null) {
                            JOptionPane.showMessageDialog(frame, "Pid Error!");
                        }
                        parentName = one.getParentName();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }

                    Child child = new Child();
                    child.setChildName(childName);
                    child.setPassword(password);
                    child.setPid(pid);
                    child.setParentName(parentName);
                    child.setCid(UUID.randomUUID().toString());
                    child.setCurrentDeposit(0.0);
                    child.setSavingDeposit(0.0);
                    try {
                        Child one = childService.getOneByKey("childName", childName);
                        if (one != null && one.getChildName().equals(childName)) {
                            JOptionPane.showMessageDialog(frame, "Registered Error!");
                            return;
                        }
                        childService.save(child);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    } catch (IllegalAccessException ex) {
                        throw new RuntimeException(ex);
                    }

                    // TODO: register
                    JOptionPane.showMessageDialog(frame, "Registered successfully!");
                }
            }  
        });  
  
        // 登录按钮的监听器
        loginButton.addActionListener(new ActionListener() {  
            @Override  
            public void actionPerformed(ActionEvent e) {  
                // 登录逻辑，从文件验证用户名和密码
                String childName = childNameField.getText();  
                String password = passwordField.getText();
                try {
                    Child child = childService.getOneByKey("childName", childName);
                    if (child!=null&&child.getPassword().equals(password)) {
                        JOptionPane.showMessageDialog(frame, "Login successful!");
                        // 关闭窗口
                        ((Window) SwingUtilities.getRoot(loginButton)).dispose();
                        new ChildMenuUI(child);
                    }else {
                        JOptionPane.showMessageDialog(frame, "Login fail!");
                    }
                } catch (Exception ioException) {
                }
                // TODO: login

            }  
        });  
  
        // panel
        panel.setLayout(new GridLayout(4, 2));  
        panel.add(childNameLabel);  
        panel.add(childNameField);  
        panel.add(passwordLabel);  
        panel.add(passwordField);  
        panel.add(parentIdLabel);
        panel.add(parentIdField);
        panel.add(new JPanel()); // Spacer panel
        JPanel buttonsPanel = new JPanel();  
        buttonsPanel.add(registerButton);  
        buttonsPanel.add(loginButton);  
        panel.add(buttonsPanel);  
    }  
}