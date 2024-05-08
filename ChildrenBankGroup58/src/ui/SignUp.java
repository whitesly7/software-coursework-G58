//javac -cp json-20210307.jar SignUp.java
//java -cp .:json-20210307.jar SignUp
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.geom.Ellipse2D;

import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.nio.file.StandardOpenOption;

public class SignUp extends JFrame {
    private JTextField nameField;
    private JTextField phoneNumField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    public SignUp() {
        setTitle("Sign Up Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);

        try {
            BufferedImage backgroundImage = ImageIO.read(new File("background.jpg"));

            JPanel panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                    g.setColor(new Color(255, 255, 255, 128));
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            };
            panel.setLayout(new BorderLayout());

            // 创建黄色标题栏
            JPanel yellowPanel = new JPanel();
            yellowPanel.setBackground(Color.yellow);
            yellowPanel.setPreferredSize(new Dimension(getWidth(), 30));
            JLabel titleLabel = new JLabel("SignUp Page");
            yellowPanel.add(titleLabel);

            JPanel upperPanel = new JPanel();
            upperPanel.setOpaque(false);
            upperPanel.setLayout(new GridBagLayout());

            ImageIcon profileIcon = new ImageIcon("profilepic.jpg");
            Image scaledProfileImage = profileIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            ImageIcon scaledProfileIcon = new ImageIcon(scaledProfileImage);
            JLabel profileLabel = new JLabel(scaledProfileIcon);

            GridBagConstraints gbcProfile = new GridBagConstraints();
            gbcProfile.gridx = 0;
            gbcProfile.gridy = 0;
            gbcProfile.anchor = GridBagConstraints.CENTER;
            upperPanel.add(profileLabel, gbcProfile);

            JPanel taskPanel = new JPanel();
            taskPanel.setLayout(new GridLayout(0, 2, 20, 20));
            taskPanel.setOpaque(false);

            placeComponents(taskPanel);

            GridBagConstraints gbcTask = new GridBagConstraints();
            gbcTask.gridx = 0;
            gbcTask.gridy = 1;
            gbcTask.weightx = 1.0;
            gbcTask.weighty = 1.0;
            gbcTask.fill = GridBagConstraints.BOTH;
            gbcTask.insets = new Insets(10, 20, 10, 20);
            upperPanel.add(taskPanel, gbcTask);

            panel.add(upperPanel, BorderLayout.CENTER);
            panel.add(yellowPanel, BorderLayout.NORTH);
            add(panel);

        } catch (IOException e) {
            System.out.println("Unable to load image: " + e.getMessage());
        }

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void placeComponents(JPanel panel) {
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
    
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);
    
        // 创建一个包含所有组件的面板
        JPanel formPanel = new JPanel(new GridBagLayout());
    
        // 设置整个面板的背景为橙色
        Color backgroundColor = new Color(255, 165, 0, 128); 
        formPanel.setBackground(backgroundColor);

        Color backColor = new Color(255, 165, 0, 128);
        // 创建名字面板
        JPanel namePanel = new JPanel(new BorderLayout());
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField(20);
        namePanel.add(nameLabel, BorderLayout.WEST);
        namePanel.add(nameField, BorderLayout.CENTER);
        namePanel.setBackground(backColor);
    
        // 创建电话号码面板
        JPanel phoneNumPanel = new JPanel(new BorderLayout());
        JLabel phoneNumLabel = new JLabel("Phone Number:");
        phoneNumField = new JTextField(20);
        phoneNumPanel.add(phoneNumLabel, BorderLayout.WEST);
        phoneNumPanel.add(phoneNumField, BorderLayout.CENTER);
        phoneNumPanel.setBackground(backColor);
    
        // 创建密码面板
        JPanel passwordPanel = new JPanel(new BorderLayout());
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        passwordPanel.add(passwordLabel, BorderLayout.WEST);
        passwordPanel.add(passwordField, BorderLayout.CENTER);
        passwordPanel.setBackground(backColor);
    
        // 创建确认密码面板
        JPanel confirmPasswordPanel = new JPanel(new BorderLayout());
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordField = new JPasswordField(20);
        confirmPasswordPanel.add(confirmPasswordLabel, BorderLayout.WEST);
        confirmPasswordPanel.add(confirmPasswordField, BorderLayout.CENTER);
        
        confirmPasswordPanel.setBackground(backColor);
    
        // 将所有面板添加到formPanel中
        formPanel.add(namePanel, gbc);
        formPanel.add(phoneNumPanel, gbc);
        formPanel.add(passwordPanel, gbc);
        formPanel.add(confirmPasswordPanel, gbc);
    
        // 将表单面板添加到主面板中
        panel.add(formPanel, gbc);
    
        // 创建按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
    
        // 创建提交按钮并实现json保存
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        // 获取输入数据
            String name = nameField.getText();
            String phoneNum = phoneNumField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

        // 验证密码是否一致
            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(null, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

        // 创建用户JSON对象
            JSONObject user = new JSONObject();
            user.put("name", name);
            user.put("phoneNum", phoneNum);
            user.put("password", password);  // 注意：在实际应用中，应对密码进行加密处理

        // 保存用户信息到JSON文件
            try {
                File file = new File("user.json");
                JSONArray usersArray;
                if (file.exists()) {
                // 文件存在，读取现有数据并转换为JSONArray
                    String content = new String(Files.readAllBytes(Paths.get("user.json")));
                    usersArray = new JSONArray(content);
                } else {
                // 文件不存在，创建新的JSONArray
                    usersArray = new JSONArray();
                }
            // 添加新用户信息
                usersArray.put(user);
            // 写回文件
                Files.write(Paths.get("user.json"), usersArray.toString(4).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                JOptionPane.showMessageDialog(null, "User registered successfully");
            } catch (IOException ioException) {
                ioException.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to save user data", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    });

        buttonPanel.add(submitButton);
    
        // 创建重置按钮
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Implement reset action
                nameField.setText("");
                phoneNumField.setText("");
                passwordField.setText("");
                confirmPasswordField.setText("");
            }
        });
        buttonPanel.add(resetButton);
    
        // 将按钮面板添加到主面板中
        formPanel.add(buttonPanel, gbc);
    }
    
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SignUp();
        });
    }
}