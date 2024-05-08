import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Home extends JFrame {
    public Home() {
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
            JButton leftButton = new JButton("Help? Look here!");
            leftButton.setPreferredSize(new Dimension(80, 40)); // 设置按钮尺寸
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

            // 创建中间区域面板
            JPanel centerPanel = new JPanel(new GridLayout(4, 1));

            JButton moneyButton = new JButton("00.00 RMB");
            centerPanel.add(moneyButton);

            JPanel centerLowerPanel1 = new JPanel();
            centerLowerPanel1.setLayout(new GridLayout(1, 2));
            JButton currentAccountButton = new JButton("CurrentAccount: 0.00");
            currentAccountButton.setPreferredSize(new Dimension(120, 30)); // 设置按钮尺寸
            JButton transfer1Button = new JButton("Transfer");
            transfer1Button.setPreferredSize(new Dimension(80, 30)); // 设置按钮尺寸

            centerLowerPanel1.add(currentAccountButton);
            centerLowerPanel1.add(transfer1Button);
            centerPanel.add(centerLowerPanel1);

            JPanel centerLowerPanel2 = new JPanel();
            centerLowerPanel2.setLayout(new GridLayout(1, 2));
            JButton savingAccountButton = new JButton("SavingAccount: 0.00");
            savingAccountButton.setPreferredSize(new Dimension(120, 30)); // 设置按钮尺寸
            JButton transfer2Button = new JButton("Transfer");
            transfer2Button.setPreferredSize(new Dimension(80, 30)); // 设置按钮尺寸

            transfer2Button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // 关闭窗口
                ((Window) SwingUtilities.getRoot(transfer2Button)).dispose();
                // 打开帮助页面
                new SavingAccount();
                }
            });

            centerLowerPanel2.add(savingAccountButton);
            centerLowerPanel2.add(transfer2Button);
            centerPanel.add(centerLowerPanel2);

            //任务跳转
            JPanel centerLowerPanel3 = new JPanel();
            centerLowerPanel3.setLayout(new GridLayout(1, 2));
            JButton receiveTaskButton = new JButton("Receive Tasks");
            receiveTaskButton.setPreferredSize(new Dimension(120, 30)); // 设置按钮尺寸
            receiveTaskButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    // 关闭窗口
                    ((Window) SwingUtilities.getRoot(receiveTaskButton)).dispose();
    
                     // 打开孩子页面
                    new TBChild();
                    }
            });

            JButton checkHistoryButton = new JButton("Check History");
            checkHistoryButton.setPreferredSize(new Dimension(120, 30)); // 设置按钮尺寸
            centerLowerPanel3.add(receiveTaskButton);
            centerLowerPanel3.add(checkHistoryButton);
            centerPanel.add(centerLowerPanel3);

            JButton logoutButton = new JButton("Log out");
            logoutButton.setPreferredSize(new Dimension(80, 40)); // 设置按钮尺寸

            logoutButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // 关闭窗口
                ((Window) SwingUtilities.getRoot(logoutButton)).dispose();
                // 打开帮助页面
                new LoginPage();
                }
            });
            // 创建右方区域面板
            JPanel rightPanel = new JPanel();
            rightPanel.add(logoutButton);

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Home();
        });
    }
}