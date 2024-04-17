package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginPage extends JFrame {
    private JTextField phoneNumField;
    private JPasswordField passwordField;
    
    public LoginPage() {
        // 设置窗体的标题和默认关闭操作
        setTitle("Login Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);  // 居中显示窗体
        
     // 创建背景面板并添加图片
        BackgroundPanel backgroundPanel = new BackgroundPanel("background.jpg");
        setContentPane(backgroundPanel);

        // 创建标题栏
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.YELLOW);  // 设置黄色背景
        headerPanel.setPreferredSize(new Dimension(getWidth(), 50)); // 设置标题栏大小
        backgroundPanel.add(headerPanel, BorderLayout.NORTH);
        
        // 创建中间的登录面板
        JPanel loginPanel = new JPanel();
        loginPanel.setOpaque(false);  // 使面板透明以显示背景图片
        loginPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        backgroundPanel.add(loginPanel, BorderLayout.CENTER);

        // 将登录组件添加到loginPanel中
        placeComponents(loginPanel);
        
        // 显示窗体
        setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        // 创建手机号标签和文本域
        JLabel phoneNumLabel = new JLabel("Phone Number:");
        phoneNumLabel.setBounds(10, 10, 160, 25);
        panel.add(phoneNumLabel);

        phoneNumField = new JTextField(20);
        phoneNumField.setBounds(180, 10, 165, 25);
        panel.add(phoneNumField);

        // 创建密码标签和密码域
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 45, 160, 25);
        panel.add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(180, 45, 165, 25);
        panel.add(passwordField);

        // 创建登录按钮
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(10, 80, 160, 25);
        loginButton.setBackground(new Color(34, 139, 34)); // 设置为深绿色背景
        loginButton.setForeground(Color.WHITE); // 设置字体颜色为白色
        loginButton.setFont(new Font("Tahoma", Font.BOLD, 12)); // 设置字体为加粗
        panel.add(loginButton);

        // 创建注册按钮
        JButton registerButton = new JButton("Register");
        registerButton.setBounds(180, 80, 165, 25);
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 此处可以添加打开注册页面的代码
                JOptionPane.showMessageDialog(null, "Register button clicked");
            }
        });
        panel.add(registerButton);
    }

    public static void main(String[] args) {
    // 为确保线程安全，使用invokeLater方法来启动GUI
    SwingUtilities.invokeLater(new Runnable() {
        @Override
        public void run() {
            new LoginPage();
        }
    });
}
}

class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    public BackgroundPanel(String fileName) {
        backgroundImage = new ImageIcon(fileName).getImage();
        setLayout(new BorderLayout());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}
