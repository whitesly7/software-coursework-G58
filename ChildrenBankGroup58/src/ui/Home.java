import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.FileWriter;
import java.awt.geom.Ellipse2D;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Home extends JFrame {
    public int currentUserId;
    public Home(int currentUserId) {
        try {
            setTitle("HOME");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(1000, 500);

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
                    g.setColor(new Color(255, 255, 255, 128)); //白色矩形作为遮罩
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            };
            panel.setLayout(new BorderLayout());

            // 创建左方区域面板
            JPanel leftPanel = new JPanel();
            leftPanel.setOpaque(false);
            leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

            // 创建左侧头像图标
            ImageIcon originalIcon = new ImageIcon("profilepic.jpg");
            Image originalImage = originalIcon.getImage();
            Image scaledImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            JLabel avatarLabel = new JLabel(scaledIcon);
            avatarLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            // 创建左侧帮助按钮
            final JButton leftButton = createRoundButton(new Color(229,104,100,255), "Help? Look here!");
            leftButton.setBounds(20, 76, 157, 50);
            leftButton.setAlignmentX(Component.CENTER_ALIGNMENT);

            leftButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // 关闭窗口
                ((Window) SwingUtilities.getRoot(leftButton)).dispose();
                // 打开帮助页面
                new HelpPage();
                }
            });
 
            leftPanel.add(avatarLabel);
            leftPanel.add(leftButton);

            //读取当前用户数据
            JSONArray userList = new JSONArray();
            try {
                String jsonContent = new String(Files.readAllBytes(Paths.get("/Users/kimtan/Desktop/try/user.json")));
                userList = new JSONArray(jsonContent);
            } catch (IOException ioException) {
                ioException.printStackTrace();
                JOptionPane.showMessageDialog(null, "Failed to read user data", "Error", JOptionPane.ERROR_MESSAGE);
            }

            // 根据id获取当前用户数据
            JSONObject currentUser = null;

            float fullBalance = 0;
            float currentBalance = 0;
            float savingBalance = 0;
           
            for (int i = 0; i < userList.length(); i++) {
                JSONObject user = userList.getJSONObject(i);
                if (user.getInt("id") == currentUserId) {
                    currentUser = user;
                    break;
                }
            }

            if (currentUser == null) {
                JOptionPane.showMessageDialog(null, "Current user not found", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // 获取当前用户数据
                fullBalance = currentUser.getFloat("fullBalance");
                currentBalance = currentUser.getFloat("currentBalance");
                savingBalance = currentUser.getFloat("savingBalance");
            }

            // 创建中间区域面板
            JPanel centerPanel = new JPanel(new GridLayout(4, 1));
            centerPanel.setOpaque(false);
            JLabel lblNewLabel_11 = createRoundLabel(90, null, new Color(243, 209, 83, 255), Color.GRAY,
				"<html>"+ fullBalance +"</html>");
		    lblNewLabel_11.setBounds(257, 19, 90, 90);
            centerPanel.add(lblNewLabel_11);
            
            //创建中间中下部区域面板1
            JPanel centerLowerPanel1 = new JPanel();
            centerLowerPanel1.setLayout(new GridLayout(1, 2));
            centerLowerPanel1.setOpaque(false);
            
            JLabel lblNewLabel_1 = new JLabel("Current Account:"+ currentBalance + "RMB");
		    lblNewLabel_1 = createRoundLabel(40, null, new Color(243, 209, 83, 255), new Color(65, 150, 95, 255),"Current Account: 0.00 RMB");
		    lblNewLabel_1.setBounds(109, 160, 217, 50);
		    centerLowerPanel1.add(lblNewLabel_1);
            
            JButton btnNewButton_1 = new JButton("transfer");
            btnNewButton_1 = createRoundButton(new Color(236, 146, 70, 255), "transfer");
            btnNewButton_1.setBounds(336, 173, 100, 30);
            centerLowerPanel1.add(btnNewButton_1);
            centerPanel.add(centerLowerPanel1);

            //创建中间中下部区域面板2
            JPanel centerLowerPanel2 = new JPanel();
            centerLowerPanel2.setLayout(new GridLayout(1, 2));
            centerLowerPanel2.setOpaque(false);

		    JLabel lblNewLabel_2 = new JLabel("Saving Account:"+ savingBalance +"RMB");
		    lblNewLabel_2 = createRoundLabel(40, null, new Color(243, 209, 83, 255), new Color(65, 150, 95, 255),"Saving Account: 0.00 RMB");
		    lblNewLabel_2.setBounds(109, 231, 217, 38);
		    centerLowerPanel2.add(lblNewLabel_2);
            
            JButton btnNewButton_2 = createRoundButton(new Color(236, 146, 70, 255), "transfer");
		    btnNewButton_2.setBounds(336, 230, 100, 30);
		    centerLowerPanel2.add(btnNewButton_2);
            centerPanel.add(centerLowerPanel2);

            btnNewButton_2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // 关闭窗口
                ((Window) SwingUtilities.getRoot(btnNewButton_2)).dispose();
                // 打开帮助页面
                new SavingAccount();
                }
            });

            //创建中间中下部区域面板3
            JPanel centerLowerPanel3 = new JPanel();
            centerLowerPanel3.setLayout(new GridLayout(1, 2));
            centerLowerPanel3.setOpaque(false);

            final  JButton btnNewButton_3 = createRoundButton(new Color(65,150,95, 255), "Receive Tasks");
            btnNewButton_3.setBounds(133, 296, 129, 40);

            btnNewButton_3.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    // 关闭窗口
                    ((Window) SwingUtilities.getRoot(btnNewButton_3)).dispose();
    
                     // 打开孩子页面
                    new TBChild();
                    }
            });
            centerLowerPanel3.add(btnNewButton_3);
        
            JButton btnNewButton_4 = createRoundButton(new Color(65,150,95, 255), "Check History");
            btnNewButton_4.setBounds(317, 296, 113, 40);

            btnNewButton_4.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // 关闭窗口
                ((Window) SwingUtilities.getRoot(btnNewButton_4)).dispose();
                // 打开帮助页面
                new HistoryPage();
                }
            });
            centerLowerPanel3.add(btnNewButton_4);
            centerPanel.add(centerLowerPanel3);

            // 创建右方区域面板
            JPanel rightPanel = new JPanel();
            rightPanel.setOpaque(false);
            
            final JButton btnNewButton5 = createRoundButton(new Color(230,95,91, 255), "Log out");
		    btnNewButton5.setBounds(249, 363, 100, 100);
            
            btnNewButton5.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // 关闭窗口
                ((Window) SwingUtilities.getRoot(btnNewButton5)).dispose();
                // 打开帮助页面
                new LoginPage();
                }
            });

             rightPanel.add(btnNewButton5);
           
            // 将左、中、右部分添加到面板
            panel.add(leftPanel, BorderLayout.WEST);
            panel.add(centerPanel, BorderLayout.CENTER);
            panel.add(rightPanel, BorderLayout.EAST);

            
            // 将面板添加到窗口
            add(panel);

            setLocationRelativeTo(null); // 将窗体显示在屏幕中央
            setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private JButton createRoundButton(Color color, String text) {
		JButton button = new JButton() {
			protected void paintComponent(Graphics g) {
				if (getModel().isArmed()) {
					g.setColor(Color.GRAY);
				} else {
					g.setColor(color);
				}
				g.fillRoundRect(0, 0, getSize().width - 15, getSize().height - 15, 20, 20);
				super.paintComponent(g);
			}
		};
        button.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		button.setText(text);
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.setPreferredSize(
				new Dimension(button.getPreferredSize().width , (int) (button.getPreferredSize().height * 0.5)));
		return button;
	}

    private JLabel createRoundLabel(int arc, Color borderColor, Color bgcolor, Color fgcolor, String text) {
		JLabel label = new JLabel() {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g.create();
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.setColor(bgcolor);
				g2d.fillRoundRect(0, 0, getWidth() - 15, getHeight() - 15, arc, arc);
				super.paintComponent(g);
				g2d.dispose();
			}

			@Override
			protected void paintBorder(Graphics g) {
				if (borderColor == null) {
					return;
				}
				g.setColor(borderColor);
				Stroke thickStroke = new BasicStroke(0.0f);
				Graphics2D g2d = (Graphics2D) g;
				g2d.setStroke(thickStroke);
				g2d.drawRoundRect(0, 0, getSize().width - 15, getSize().height - 15, arc, arc);
			}
		};
		label.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		label.setText(text);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(fgcolor);
		return label;
	}
}