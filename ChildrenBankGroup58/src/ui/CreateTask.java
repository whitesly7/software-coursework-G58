import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.geom.Ellipse2D;

import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.nio.file.StandardOpenOption;

public class CreateTask extends JFrame {
    private JTextField taskNameField;
    private JTextField requirementField;
    private JTextField tagsField;
    private JTextField awardField;

    public CreateTask() {
        setTitle("Create Task");
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
            JLabel titleLabel = new JLabel("Create Task");
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
        // 创建任务名称面板
        JPanel taskNamePanel = new JPanel(new BorderLayout());
        JLabel taskNameLabel = new JLabel("Task Name:");
        taskNameField = new JTextField(20);
        taskNamePanel.add(taskNameLabel, BorderLayout.WEST);
        taskNamePanel.add(taskNameField, BorderLayout.CENTER);
        taskNamePanel.setBackground(backColor);
    
        // 创建任务要求面板
        JPanel requirementPanel = new JPanel(new BorderLayout());
        JLabel requirementLabel = new JLabel("Requirement:");
        requirementField = new JTextField(20);
        requirementPanel.add(requirementLabel, BorderLayout.WEST);
        requirementPanel.add(requirementField, BorderLayout.CENTER);
        requirementPanel.setBackground(backColor);
    
        // 创建标签面板
        JPanel tagsPanel = new JPanel(new BorderLayout());
        JLabel tagsLabel = new JLabel("Tags:");
        tagsField = new JTextField(20);
        tagsPanel.add(tagsLabel, BorderLayout.WEST);
        tagsPanel.add(tagsField, BorderLayout.CENTER);
        tagsPanel.setBackground(backColor);
    
        // 创建奖励面板
        JPanel awardPanel = new JPanel(new BorderLayout());
        JLabel awardLabel = new JLabel("Award:");
        awardField = new JTextField(20);
        awardPanel.add(awardLabel, BorderLayout.WEST);
        awardPanel.add(awardField, BorderLayout.CENTER);
        awardPanel.setBackground(backColor);
    
        // 将所有面板添加到formPanel中
        formPanel.add(taskNamePanel, gbc);
        formPanel.add(requirementPanel, gbc);
        formPanel.add(tagsPanel, gbc);
        formPanel.add(awardPanel, gbc);
    
        // 将表单面板添加到主面板中
        panel.add(formPanel, gbc);
    
        // 创建按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
    
        // 创建提交按钮
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //获取输入数据
                String taskName=taskNameField.getText();
                String requirement=requirementField.getText();
                String tag=tagsField.getText();
                String award=awardField.getText();
                Boolean accept=false;
                Boolean finish=false;

                //创建Task的JSON对象
                JSONObject task=new JSONObject();
                task.put("taskName",taskName);
                task.put("requirement",requirement);
                task.put("tag",tag);
                task.put("award",award);
                task.put("accept",accept);
                task.put("finish",finish);
                
                //读取现有task信息
                JSONArray taskList=new JSONArray();
                 // 保存用户信息到JSON文件
                try{
                    String jsonContent=new String(Files.readAllBytes(Paths.get("/Users/kimtan/Desktop/try/tasks.json")));
                    taskList=new JSONArray(jsonContent);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Failed to save task data", "Error", JOptionPane.ERROR_MESSAGE);
                }
                
                taskList.put(task);
                // 将更新后的用户列表写入JSON文件
                try (FileWriter fileWriter = new FileWriter("/Users/kimtan/Desktop/try/tasks.json")) {
                    fileWriter.write(taskList.toString());
                    JOptionPane.showMessageDialog(null, "User registered successfully");
                    // 关闭窗口
                    ((Window) SwingUtilities.getRoot(submitButton)).dispose();
                    // 打开parent页面
                    new TBParent();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Failed to save tasks data", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonPanel.add(submitButton);
    
        // 创建取消按钮
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the window
                new TBParent();
            }
        });
        buttonPanel.add(cancelButton);
    
        // 将按钮面板添加到主面板中
        formPanel.add(buttonPanel, gbc);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CreateTask();
        });
    }
}