import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginPage extends JFrame {
    private JTextField phoneNumField;
    private JPasswordField passwordField;
    
    public LoginPage() {

        // 设置窗体的标题和默认关闭操作
        setTitle("Login Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 500);
        setLocationRelativeTo(null);  // 居中显示窗体

        try{
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
        JLabel titleLabel = new JLabel("Login Page");
        yellowPanel.add(titleLabel);

        // 创建上方区域面板
        JPanel upperPanel = new JPanel();
        upperPanel.setOpaque(false); // 设置面板透明
        upperPanel.setLayout(new GridBagLayout());

        //创建中间logo
        ImageIcon originalIcon = new ImageIcon("logo.jpg");
		Image originalImage = originalIcon.getImage();
		Image scaledImage = originalImage.getScaledInstance(400, 200, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);
		JLabel avatarLabel = new JLabel(scaledIcon);

        //将中间logo添加到上方区域面板
        GridBagConstraints gbc=new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor=GridBagConstraints.CENTER;
        upperPanel.add(avatarLabel, gbc);

        //中间区域面板
        JPanel taskPanel = new JPanel();
        taskPanel.setLayout(new GridLayout(0, 1, 20, 20)); // 每行1个便签，水平和垂直间距为20像素
        taskPanel.setOpaque(false); // 设置面板透明
        placeComponents(taskPanel);
        
        // 设置taskPanel在upperPanel中占据更多的比例
        GridBagConstraints gbcTask = new GridBagConstraints();
            gbcTask.gridx = 0;
            gbcTask.gridy = 1;
            gbcTask.weightx = 1.0;
            gbcTask.weighty = 1.0;
            gbcTask.fill = GridBagConstraints.BOTH;
            gbcTask.insets = new Insets(10, 20, 10, 20);
            upperPanel.add(taskPanel, gbcTask);

        // 将黄色标题栏、上方区域面板、中间区域面板添加到面板
        panel.add(yellowPanel, BorderLayout.NORTH);
        panel.add(upperPanel, BorderLayout.CENTER);
        panel.add(taskPanel,BorderLayout.SOUTH);//放在靠下位置
        add(panel);
        
    }catch (IOException e) {
    	//	e.printStackTrace();
    		System.out.println("cant find pic");
		}
        
        // 显示窗体
        setLocationRelativeTo(null); 
        setVisible(true);
    }

    public void placeComponents(JPanel panel) {
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        
        // 公共的GridBagConstraints设置
        gbc.gridwidth = GridBagConstraints.REMAINDER; // 每个组件占据一行
        gbc.fill = GridBagConstraints.HORIZONTAL;     // 水平扩展以填充区域
        gbc.insets = new Insets(10, 0, 10, 0); // 设置组件之间的间距
        
        // 创建一个包含所有组件的面板
        JPanel formPanel = new JPanel(){
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
    
        // 设置整个面板的背景为透明orange色
        Color backgroundColor = new Color(255, 165, 0, 0); // 透明橙色
        formPanel.setBackground(backgroundColor);
        formPanel.setPreferredSize(new Dimension(500,250));

        // 创建手机号标签和文本域
        JPanel phoneNumPanel=new JPanel(new BorderLayout());
        JLabel phoneNumLabel = new JLabel("Phone Number:");
        gbc.anchor = GridBagConstraints.CENTER;       // 居中对齐
        phoneNumField = new JTextField(20);

        phoneNumPanel.add(phoneNumLabel, BorderLayout.WEST);
        phoneNumPanel.add(phoneNumField, BorderLayout.CENTER);
        phoneNumPanel.setBackground(new Color(255, 165, 0, 0));
    
        // 创建密码标签和密码域
        JPanel passworPanel=new JPanel(new BorderLayout());
        JLabel passwordLabel = new JLabel("Password:");
        gbc.anchor = GridBagConstraints.CENTER;
        
        passwordField = new JPasswordField(20);
        //panel.add(passwordField, gbc);panel.add(passwordLabel, gbc);
    
        passworPanel.add(passwordLabel, BorderLayout.WEST);
        passworPanel.add(passwordField, BorderLayout.CENTER);
        passworPanel.setBackground(new Color(255, 165, 0, 0));

        // 创建按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);

        // 创建登录按钮
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(255, 165, 0, 0)); 
        loginButton.setForeground(Color.GREEN);            // 设置字体颜色为白色
        loginButton.setFont(new Font("Tahoma", Font.BOLD, 24)); // 设置字体为加粗
        
        //点击登陆按钮，跳转到主界面
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                // 关闭登录窗口
            ((Window) SwingUtilities.getRoot(loginButton)).dispose();
            System.out.println("opened");
             // 打开主页面
            //new HomePage();//正在写
            }
        });

       
        // 创建注册按钮
        JButton registerButton = new JButton("Register");
        registerButton.setBackground(new Color(255, 165, 0, 0));
        registerButton.setFont(new Font("Tahoma", Font.BOLD, 24)); // 设置字体为加粗
        
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                // 关闭登录窗口
            ((Window) SwingUtilities.getRoot(loginButton)).dispose();

             // 打开主页面
            new SignUp();
            }
        });

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        formPanel.add(phoneNumPanel, gbc);
        formPanel.add(passworPanel, gbc);
        formPanel.add(buttonPanel,gbc);      
        // 将表单面板添加到主面板中
        panel.add(formPanel, gbc);

    }
    
    public static void main(String[] args) {
    // 为确保线程安全，使用invokeLater方法来启动GUI
    SwingUtilities.invokeLater(()-> {
            new LoginPage();
    });
}
}