/*
	javac -cp json-20210307.jar TBParent.java AcceptedTasks.java TBChild.java CreateTask.java LoginPage.java SignUp.java Home.java HelpPage.java SavingAccount.java HistoryPage.java
    java -cp .:json-20210307.jar TBChild
 	运行前提注意：你需要保证json库和TBChild在同一个文件下
 	parent view进入密码为123456
*/


import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.border.EmptyBorder;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.geom.Ellipse2D;

import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.event.ActionEvent;			
import java.awt.event.ActionListener;

/*方法概述
	Main class:TBChild
	main method-->execute

	public TBChild():creat a child-view taskboard (constructor)
	public void parentButtonClick(ActionEvent e): act to the parent view button
	
*/

public class TBChild extends JFrame {
    public int currentUserId;
    public TBChild() {
        setTitle("TBChild");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 500);

        try {
        // 加载背景图像
        BufferedImage backgroundImage = ImageIO.read(new File("background.jpg"));

        // 创建面板
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // 绘制背景图像
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                // 绘制半透明层
                g.setColor(new Color(255, 255, 255, 128));//白色矩形作为遮罩
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(new BorderLayout());

        // 创建黄色标题栏
        JPanel yellowPanel = new JPanel();
        yellowPanel.setBackground(Color.YELLOW);
        yellowPanel.setPreferredSize(new Dimension(getWidth(), 30));
        JLabel titleLabel = new JLabel("TASK BOARD");
        yellowPanel.add(titleLabel);


        // 创建上方区域面板
        JPanel upperPanel = new JPanel();
        upperPanel.setOpaque(false); // 设置面板透明
        upperPanel.setLayout(new GridBagLayout());

        // 创建左侧头像图标
		ImageIcon originalIcon = new ImageIcon("profilepic.jpg");
		Image originalImage = originalIcon.getImage();
		Image scaledImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);
		JLabel avatarLabel = new JLabel(scaledIcon);

        // 创建中间提示栏
        JLabel promptLabel = new JLabel("Find some tasks to do and earn yourself!");
        promptLabel.setFont(promptLabel.getFont().deriveFont(24.0f)); // 设置字体大小为24
		promptLabel.setHorizontalAlignment(SwingConstants.CENTER); // 设置水平居中对齐
		promptLabel.setOpaque(true);
		promptLabel.setBackground(new Color(255, 255, 0, 64)); // 设置背景颜色为黄色半透明

        // 创建右侧按钮
        JButton parentButton = new JButton("parent view");
        parentButton.setBackground(Color.PINK);
        // 实现parent button 跳转功能
        parentButton.addActionListener(this::parentButtonClick);


        // 将左侧头像、中间提示栏和右侧按钮添加到上方区域面板
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 10, 0, 10); // 设置左侧和右侧的边距
        upperPanel.add(avatarLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        upperPanel.add(promptLabel, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(0, 0, 0, 10); // 设置右侧的边距
        parentButton.setBackground(Color.PINK);
        upperPanel.add(parentButton, gbc);

        //task面板
        JPanel taskPanel = new JPanel();
        taskPanel.setLayout(new GridLayout(0, 3, 20, 20)); // 每行3个任务便签，水平和垂直间距为20像素
        taskPanel.setOpaque(false); // 设置面板透明

        try {
   			String jsonContent = new String(Files.readAllBytes(Paths.get("tasks.json")), "UTF-8");

   			// 解析JSON
    		JSONArray tasksArray = new JSONArray(jsonContent);
    		// 遍历任务数组并添加到橙色任务便签中
    		for (int i = 0; i < tasksArray.length(); i++) {
        		JSONObject taskObject = tasksArray.getJSONObject(i);
       			String taskTitle = taskObject.getString("taskName");
       			String taskDescription=taskObject.getString("requirement");
                String taskLabel=taskObject.getString("tag");
                String taskAward=taskObject.getString("award");

        		// 创建任务便签面板
        		JPanel taskCard = new JPanel() {
          		 @Override
            		protected void paintComponent(Graphics g) {
                		// 绘制圆角矩形
                	super.paintComponent(g);
            		Graphics2D g2d = (Graphics2D) g.create();
            		g2d.setColor(new Color(255, 165, 0, 128));
          		    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        		    int arcWidth = 20; // 圆角的宽度
      		        int arcHeight = 20; // 圆角的高度
        		    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);
        		    g2d.dispose();
            		}
        		};

        		//任务卡背景色
        		taskCard.setBackground(new Color(255, 165, 0, 128));
        		taskCard.setOpaque(false);
        		taskCard.setPreferredSize(new Dimension(60, 60));

        		//任务卡排版
        		taskCard.setLayout(new GridBagLayout());
        		JLabel taskLabel1 = new JLabel(taskTitle);
        		JLabel taskLabel2 = new JLabel(taskDescription);
        		taskLabel1.setFont(taskLabel1.getFont().deriveFont(18.0f)); // 设置字体大小为18
        		taskLabel2.setFont(taskLabel2.getFont().deriveFont(17.0f)); // 设置字体大小为17
				JButton acceptButton = new JButton("Accept");
				acceptButton.addActionListener(this::AcceptButtonClick);//为Accept button设置监听方法

        		GridBagConstraints gbc2 = new GridBagConstraints();
        		//taskLabel
				gbc2.gridx = 0;
       			gbc2.gridy = 0;
       			gbc2.weightx = 1.0;
        		gbc2.weighty = 0.5;
        		gbc2.anchor = GridBagConstraints.CENTER;
      			taskCard.add(taskLabel1, gbc2);

      			//taskLabel2
				gbc2.gridy = 1;
        		gbc2.weighty = 1;
        		taskCard.add(taskLabel2, gbc2);

        		//parent view Button
        		gbc2.gridy = 2;
     		    gbc2.weighty = 0.0;
     		    gbc2.insets = new Insets(0, 0, 10, 0); // 设置按钮底部的边距
     		    
      		    taskCard.add(acceptButton, gbc2);
        		
        		taskPanel.add(taskCard);
    		}
		} catch (IOException e) {
    		System.out.println("error");
		}

        // 创建下方区域面板（创建新的task以及返回child界面）
        JPanel lowerPanel = new JPanel();
        lowerPanel.setOpaque(false); // 设置面板透明
        lowerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbclow = new GridBagConstraints();
        
        JButton backButton = new JButton("Return to HomePage");
        backButton.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e){
            // 关闭窗口
            ((Window) SwingUtilities.getRoot(backButton)).dispose();
            // 打开主页面
            new Home(currentUserId);
            }
        });
        // 将button加入下方区域面板
        gbclow.gridx = 0;
        lowerPanel.add(backButton, gbclow);

        // 设置taskPanel在upperPanel中占据更多的比例
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3; //设置组件在网格布局中所占据的列数
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;  //组件在水平和垂直方向都填充格子。
        gbc.insets = new Insets(10, 10, 10, 10); // 设置上下左右的边距
        upperPanel.add(taskPanel, gbc);

        // 将黄色标题栏、上方区域面板添加到面板
        panel.add(yellowPanel, BorderLayout.NORTH);
        panel.add(upperPanel, BorderLayout.CENTER);
        panel.add(lowerPanel,BorderLayout.SOUTH);

        // 将面板添加到窗口
        add(panel);
    } catch (IOException e) {
        e.printStackTrace();
    }

    setLocationRelativeTo(null); // 将窗体显示在屏幕中央
    setVisible(true);
}



public void parentButtonClick(ActionEvent e) {
    // 创建对话框
    JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Parent View", true);
    dialog.setSize(300, 200);
    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    dialog.setLocationRelativeTo(null); // 设置对话框居中显示

    // 创建密码标签
    JLabel passwordLabel = new JLabel("Enter Password");

    // 创建密码输入框
    JPasswordField passwordField = new JPasswordField();
    passwordField.setColumns(20);

    // 创建显示密码复选框
        JCheckBox showPasswordCheckbox = new JCheckBox("Show Password");
        showPasswordCheckbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBox checkbox = (JCheckBox) e.getSource();
                if (checkbox.isSelected()) {
                    passwordField.setEchoChar('\0'); // 设置回显字符为空字符
                } else {
                    passwordField.setEchoChar('\u2022'); // 设置回显字符为圆点
                }
            }
        });

    // 创建提交按钮
    JButton submitButton = new JButton("Submit");

    // 创建面板，并将密码标签、密码输入框和提交按钮添加到面板中
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 10, 0); // 设置间距
        panel.add(passwordLabel, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 20, 0); // 设置间距
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(passwordField, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 10, 0);
        panel.add(showPasswordCheckbox, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 0, 0);
        panel.add(submitButton, gbc);

    // 添加提交按钮的点击事件监听器
    submitButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 获取输入的密码
            char[] password = passwordField.getPassword();
            String enteredPassword = new String(password);

            // 进行密码验证
            if (enteredPassword.equals("123456")) {
                // 密码正确，执行相应操作
                System.out.println("Password correct. Parent view accessed.");
                // 弹出parent view界面……
                new TBParent();
                // 关闭对话框
                dialog.dispose();
            } else {
                // 密码错误，显示错误提示
                JOptionPane.showMessageDialog(dialog, "Incorrect password. Try again.", "Error", JOptionPane.ERROR_MESSAGE);
                // 清空密码输入框
                passwordField.setText("");
            }
        }
    });

    // 将面板添加到对话框中
    dialog.add(panel);

    // 显示对话框
    dialog.setVisible(true);
}


public void AcceptButtonClick(ActionEvent e) {
    JButton acceptButton = (JButton) e.getSource();

    int result = JOptionPane.showConfirmDialog(this, "Accept this task?", "accept confirm", JOptionPane.YES_NO_OPTION);
    if (result == JOptionPane.YES_OPTION) {
        // 弹出接受成功的对话框
        JOptionPane acceptSuccessDialog = new JOptionPane("Accept successfully");
        JDialog dialog = acceptSuccessDialog.createDialog(this, "Accept successfully");
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);

        // 延迟一秒关闭对话框
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                dialog.dispose(); // 关闭接受成功的对话框
                acceptButton.setText("Have Accepted"); // 修改按钮文本为"Have Accepted"
                acceptButton.setEnabled(false); // 设置按钮不可点击
            }
        });
        timer.setRepeats(false); //定时器不重复执行
        timer.start();
    }
}

public static void main(String[] args) {
	//确保在正确的线程上创建和操作Swing组件，以避免潜在的线程安全问题。
    SwingUtilities.invokeLater(() -> {
        new TBChild();
    });
}
}