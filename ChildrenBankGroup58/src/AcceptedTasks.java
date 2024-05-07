/*
	编译命令javac -cp json-20210307.jar AcceptedTasks.java
 	运行命令java -cp .:json-20210307.jar AcceptedTasks
 	运行前提注意：你需要保证json库和AcceptedTasks在同一个文件下
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

public class AcceptedTasks extends JFrame{
    
    public AcceptedTasks(){//创造AcceptedTasks实例
        setTitle("AcceptedTasks");
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
            JLabel titleLabel = new JLabel("Accepted Tasks");
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
            JLabel promptLabel = new JLabel("Here are your children's choices");
            promptLabel.setFont(promptLabel.getFont().deriveFont(24.0f)); // 设置字体大小为24
            promptLabel.setHorizontalAlignment(SwingConstants.CENTER); // 设置水平居中对齐
            promptLabel.setOpaque(true);
            promptLabel.setBackground(new Color(255, 255, 0, 64)); // 设置背景颜色为黄色半透明
            
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
        		JLabel taskLabel = new JLabel(taskTitle);
        		JLabel taskLabel2 = new JLabel(taskDescription);
        		taskLabel.setFont(taskLabel.getFont().deriveFont(18.0f)); // 设置字体大小为18
        		taskLabel2.setFont(taskLabel2.getFont().deriveFont(17.0f)); // 设置字体大小为17
				JButton finishButton = new JButton("Finished!");
			    finishButton.addActionListener(this::FinishButtonClick);
                //为Finish button设置监听方法

        		/*GridBagConstraints gbc2 = new GridBagConstraints();
        		//taskLabel
				gbc2.gridx = 0;
       			gbc2.gridy = 0;
       			gbc2.weightx = 1.0;
        		gbc2.weighty = 0.5;
        		gbc2.anchor = GridBagConstraints.CENTER;
      			taskCard.add(taskLabel, gbc2);

      			//taskLabel2
				gbc2.gridy = 1;
        		gbc2.weighty = 1;
        		taskCard.add(taskLabel2, gbc2);
                taskCard.add(finishButton, gbc2);
                //把card加入panel
                taskPanel.add(taskCard);*/
            GridBagConstraints gbc2 = new GridBagConstraints();
            // taskLabel
            gbc2.gridx = 0;
            gbc2.gridy = 0;
            gbc2.weightx = 1.0;
            gbc2.weighty = 0.5; // 将权重调整为0.5，使其上下有空间
            //gbc2.fill = GridBagConstraints.HORIZONTAL; // 填充整个水平空间
            gbc2.anchor = GridBagConstraints.CENTER; // 水平垂直居中
            taskCard.add(taskLabel, gbc2);

            // taskLabel2
            gbc2.gridy = 1;
            gbc2.weighty = 0.5; // 给予足够的垂直空间
            gbc2.anchor = GridBagConstraints.CENTER; // 保持居中对齐
            taskCard.add(taskLabel2, gbc2);

            // finishButton
            gbc2.gridy = 2;
            gbc2.weighty = 0; // 不需要额外的垂直空间扩展
            gbc2.anchor = GridBagConstraints.PAGE_END; // 锚定到页面底部
            taskCard.add(finishButton, gbc2);

// 将定制的card加入panel
taskPanel.add(taskCard);

    		}
		} catch (IOException e) {
    	//	e.printStackTrace();
    		System.out.println("opps，can't read anymore hahahaha");
		}


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

        // 将面板添加到窗口
        add(panel);
    } catch (IOException e) {
        e.printStackTrace();
    }

    setLocationRelativeTo(null); // 将窗体显示在屏幕中央
    setVisible(true);
}

public void FinishButtonClick(ActionEvent e) {
    JButton finishButton = (JButton) e.getSource();

    int result = JOptionPane.showConfirmDialog(this, "Once you click 'finished', your child has completed the task and the salary will be transferred directly to the current account. Areyou sure your child has completed the task", "Warning", JOptionPane.YES_NO_OPTION);
    if (result == JOptionPane.YES_OPTION) {
        // 弹出成功完成的对话框
        JOptionPane acceptSuccessDialog = new JOptionPane("Finished successfully");
        JDialog dialog = acceptSuccessDialog.createDialog(this, "Finished successfully");
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);

        // 延迟一秒关闭对话框
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                dialog.dispose(); // 关闭成功完成的对话框
                finishButton.setText("Have Finished"); // 修改按钮文本为"Have Finished"
                finishButton.setEnabled(false); // 设置按钮不可点击
            }
        });
        timer.setRepeats(false); //定时器不重复执行
        timer.start();
        }
    }
        public static void main(String[] args) {
            //确保在正确的线程上创建和操作Swing组件，以避免潜在的线程安全问题。
            SwingUtilities.invokeLater(() -> {
                new AcceptedTasks();
            });
    }
}
