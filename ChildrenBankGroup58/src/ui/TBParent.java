/*
	编译命令javac -cp json-20210307.jar TBParent.java
 	运行命令java -cp .;json-20210307.jar TBParent
 	运行前提注意：你需要保证json库和TBChild在同一个文件下
*/


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

public class TBParent extends JFrame{
    //TBCParentMethod tbc; // 创建TBCMethod的实例
    public TBParent() {
        setTitle("TBCParent");
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


        // 创建上方区域面板(主区域)
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

        // 创建右侧按钮
        JButton button = new JButton("Accepted Tasks");

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
        upperPanel.add(button, gbc);

        // 创建下方区域面板（创建新的task以及返回child界面）
        JPanel lowerPanel = new JPanel();
        //lowerPanel.setLayout(new BorderLayout());
        lowerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbclow = new GridBagConstraints();
        // 创建两个button
        JButton createNewTaskButton = new JButton("Create New!");
        JButton backButton = new JButton("Back to Children's View");
        // 将button加入下方区域面板
        gbclow.gridx = 0;
        lowerPanel.add(createNewTaskButton, gbclow);
        gbclow.gridx = 1;
        lowerPanel.add(backButton, gbclow);



        //task面板
        JPanel taskPanel = new JPanel();
        taskPanel.setLayout(new GridLayout(0, 3, 20, 20)); // 每行3个任务便签，水平和垂直间距为20像素
        taskPanel.setOpaque(false); // 设置面板透明

        try {
   			String jsonContent = new String(Files.readAllBytes(Paths.get("tasks.json")), "UTF-8");

   			// 解析JSON
    		JSONObject jsonObject = new JSONObject(jsonContent);
    		JSONArray tasksArray = jsonObject.getJSONArray("tasks");

    		// 遍历任务数组并添加到橙色任务便签中
    		for (int i = 0; i < tasksArray.length(); i++) {
        		JSONObject taskObject = tasksArray.getJSONObject(i);
       			String taskTitle = taskObject.getString("title");
       			String taskDescription=taskObject.getString("description");
                String taskLabel=taskObject.getString("labels");

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
        		JLabel taskT = new JLabel(taskTitle);
        		JLabel taskContent = new JLabel(taskDescription);
                JLabel taskL = new JLabel(taskLabel);
				JButton editButton = new JButton("Edit");
                JButton deleteButton = new JButton("Delete");

        		GridBagConstraints gbc2 = new GridBagConstraints();
        		//taskLabel
				gbc2.gridx = 0;
       			gbc2.gridy = 0;
                gbc.gridwidth = 2;//横跨两列，确保居中
                gbc2.fill = GridBagConstraints.HORIZONTAL;
       			gbc2.weightx = 1.0;
        		gbc2.weighty = 0.5;
        		gbc2.anchor = GridBagConstraints.CENTER;
      			taskCard.add(taskT, gbc2);
      			//taskLabel2
				gbc2.gridy = 1;
        		gbc2.weighty = 1;
        		taskCard.add(taskContent, gbc2);
                //label
                gbc2.gridy = 2;
                gbc.insets = new Insets(0, 0, 10, 5);
                taskCard.add(taskL, gbc2);
        		//acceptButton
        		gbc2.gridy = 3;
                gbc2.gridx = 0;
     		    gbc2.weighty = 0.0;
     		    gbc2.insets = new Insets(0, 0, 10, 5); // 设置按钮底部的边距
      		    taskCard.add(editButton, gbc2);
                //deleteButton
                gbc2.gridy = 3;
                gbc2.gridx = 1;
                gbc2.insets = new Insets(0, 5, 10, 0); // 设置按钮底部的边距
                taskCard.add(deleteButton, gbc2);
        		
        		taskPanel.add(taskCard);

                //调用method类里的弹窗方法（失败）
                editButton.addMouseListener(new MouseListener(){
                    @Override
                    public void mouseClicked(MouseEvent e){
                       System.out.println("已打开编辑任务页面");
                       //tbc = new TBCParentMethod();
                       //tbc.setVisible(true);
                       //tbcMethod.navigateToNewPage();
                       JFrame newFrame = new JFrame("EditTasks");  
                        // 设置新窗口的属性，如大小、位置等  
                        newFrame.setSize(1000, 500); 
                        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
                        newFrame.setVisible(true);
                    } 
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        
                    }
         
                    @Override
                    public void mouseExited(MouseEvent e) {
                    }
         
                    @Override
                    public void mousePressed(MouseEvent e) {
                    }
         
                    @Override
                    public void mouseReleased(MouseEvent e) {
                    }
                });

                //点击delete按钮，跳转到删除界面
                deleteButton.addMouseListener(new MouseListener(){
                    @Override
                    public void mouseClicked(MouseEvent e){
                       System.out.println("已打开删除任务页面");
                       JFrame newFrame = new JFrame("DeleteTasks");  
                        // 设置新窗口的属性，如大小、位置等  
                        newFrame.setSize(1000, 500);  
                        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
                        newFrame.setVisible(true);
                    } 
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        
                    }
         
                    @Override
                    public void mouseExited(MouseEvent e) {
                    }
         
                    @Override
                    public void mousePressed(MouseEvent e) {
                    }
         
                    @Override
                    public void mouseReleased(MouseEvent e) {
                    }
                });
                
    		}
		} catch (IOException e) {
    	//	e.printStackTrace();
    		System.out.println("很好，数据卡在文件里了，读不到java里");
		}

        
        //点击右上方accepted按钮，跳转到已经接受的任务界面
        button.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e){
               System.out.println("已打开接取任务页面");
               //tbcMethod.navigateToNewPage();
               JFrame newFrame = new JFrame("AcceptedTasks");  
                // 设置新窗口的属性，如大小、位置等  
                newFrame.setSize(1000, 500);  
                newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
                newFrame.setVisible(true);
            } 
            @Override
            public void mouseEntered(MouseEvent e) {
                
            }
 
            @Override
            public void mouseExited(MouseEvent e) {
            }
 
            @Override
            public void mousePressed(MouseEvent e) {
            }
 
            @Override
            public void mouseReleased(MouseEvent e) {
            }
        });

        // 设置taskPanel在upperPanel中占据更多的比例
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3; //设置组件在网格布局中所占据的列数
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10); // 设置上下左右的边距
        upperPanel.add(taskPanel, gbc);

        // 将黄色标题栏、上方区域、下方区域面板添加到面板
        panel.add(yellowPanel, BorderLayout.NORTH);
        panel.add(upperPanel, BorderLayout.CENTER);
        panel.add(lowerPanel, BorderLayout.SOUTH);

        // 将面板添加到窗口
        add(panel);
    } catch (IOException e) {
        e.printStackTrace();
    }

    setLocationRelativeTo(null); // 将窗体显示在屏幕中央
    setVisible(true);
}

public static void main(String[] args) {
    
    SwingUtilities.invokeLater(() -> {
        new TBParent();
    });
}
}
