package com.view;

import com.service.ParentService;
import com.pojo.Parent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * GUI for parent login.
 */
public class LoginGUI extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private ParentService parentService;

    /**
     * Constructs a LoginGUI.
     */
    public LoginGUI() {
        parentService = new ParentService();
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        //id
        JLabel usernameLabel = new JLabel("Parent Id:");
        panel.add(usernameLabel);
        usernameField = new JTextField();
        panel.add(usernameField);

        // password
        JLabel passwordLabel = new JLabel("Password:");
        panel.add(passwordLabel);
        passwordField = new JPasswordField();
        panel.add(passwordField);

        //button
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                try {
                    Parent parent = validateLogin(username, password);
                    if (parent != null) {
                        JOptionPane.showMessageDialog(null, "Login Successful!", "Message", JOptionPane.INFORMATION_MESSAGE);
                        ((Window) SwingUtilities.getRoot(loginButton)).dispose();
                        MenuUI menuUI = new MenuUI(parent);
                    } else {
                        JOptionPane.showMessageDialog(null, "Wrong password!", "Message", JOptionPane.INFORMATION_MESSAGE);

                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (IllegalAccessException illegalAccessException) {
                    illegalAccessException.printStackTrace();
                }
            }

        });
        panel.add(loginButton);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showRegisterDialog();
            }
        });
        panel.add(registerButton);

        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Validates user login.
     *
     * @param username The parent ID.
     * @param password The password.
     * @return The Parent object if login is successful, null otherwise.
     * @throws IOException            If an I/O error occurs.
     * @throws IllegalAccessException If access to a field is denied.
     */
    private Parent validateLogin(String username, String password) throws IOException, IllegalAccessException {
        Parent parentName = parentService.getOneByKey("pid", username);
        if (parentName != null && parentName.getPassword().equals(password)) {
            return parentName;//right
        } else {
            return null;//wrong
        }
    }

    /**
     * Displays the registration dialog.
     */
    private void showRegisterDialog() {
        JDialog registerDialog = new JDialog(this, "Register", true);
        registerDialog.setSize(300, 200);
        registerDialog.setLayout(new GridLayout(4, 2));

        JLabel pidLabel = new JLabel("New Parent Id:");
        registerDialog.add(pidLabel);
        JTextField newPidField = new JTextField();
        registerDialog.add(newPidField);

        JLabel usernameLabel = new JLabel("New Username:");
        registerDialog.add(usernameLabel);
        JTextField newUsernameField = new JTextField();
        registerDialog.add(newUsernameField);

        JLabel passwordLabel = new JLabel("New Password:");
        registerDialog.add(passwordLabel);
        JPasswordField newPasswordField = new JPasswordField();
        registerDialog.add(newPasswordField);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newPid = newPidField.getText();
                String newUsername = newUsernameField.getText();
                String newPassword = new String(newPasswordField.getPassword());
                if(newPid.equals("")  || newUsername.equals("") || newPassword.equals("")) {
                    JOptionPane.showMessageDialog(null, "You should enter full of your message!", "Message", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                    try {
                        Boolean save = parentService.save(new Parent(newPid, newUsername, newPassword));
                        if (save) {
                            JOptionPane.showMessageDialog(null, "Registration Successful!", "Message", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "Registration Failed!", "Message", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                registerDialog.dispose();
            }
        });
        registerDialog.add(confirmButton);

        registerDialog.setLocationRelativeTo(this);
        registerDialog.setVisible(true);
    }

    /**
     * Main method to launch the application.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        new LoginGUI();
    }
}
