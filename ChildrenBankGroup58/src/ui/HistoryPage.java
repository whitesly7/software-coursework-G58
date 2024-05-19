import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.awt.Font;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Window;

public class HistoryPage extends JFrame {
	public int currentUserId;
	private JLabel contentLabel;
	private String currentSection;
	private BufferedImage backgroundImage;
	private JTextField textField;

	public HistoryPage() {
		try {
			backgroundImage = ImageIO.read(new File("background.jpg"));
		} catch (Exception e) {
			System.out.println("Unable to load image: " + e.getMessage());
		}
		setTitle("History Page");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1058, 621);
		setLocationRelativeTo(null);

		JPanel homePanel = new JPanel();
		homePanel.setOpaque(false);

		JPanel dataPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d = (Graphics2D) g.create();
				g2d.setColor(Color.white);
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				int arcWidth = 20; // 圆角的宽度
				int arcHeight = 20; // 圆角的高度
				g2d.fillRoundRect(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);
				g2d.dispose();
			}
		};
		dataPanel.setLayout(new BorderLayout());
		dataPanel.setBackground(new Color(255, 165, 0, 128));
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		JPanel centerPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
				g.setColor(new Color(255, 255, 255, 128));
				g.fillRect(0, 0, getWidth(), getHeight());
			}
		};
		centerPanel.setLayout(new BorderLayout());

		JPanel sidebarPanel = new JPanel();
		sidebarPanel.setBackground(Color.darkGray);
		sidebarPanel.setPreferredSize(new Dimension(200, getHeight()));
		JButton overviewButton = createSidebarButton("Overview");
		JButton incomeButton = createSidebarButton("Income");
		JButton expenseButton = createSidebarButton("Expense");
		sidebarPanel.add(overviewButton);
		sidebarPanel.add(incomeButton);
		sidebarPanel.add(expenseButton);

		JPanel yellowPanel = new JPanel();
		yellowPanel.setBackground(Color.YELLOW);
		yellowPanel.setPreferredSize(new Dimension(getWidth(), 30));
		JLabel titleLabel = new JLabel("Check History");
		yellowPanel.add(titleLabel);

		CardLayout cardLayout = new CardLayout();
		JPanel taskPanel = new JPanel(cardLayout);
		taskPanel.setOpaque(false);

		JPanel formPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
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
		formPanel.setLayout(new BorderLayout());

		Color backgroundColor = Color.WHITE;
		formPanel.setBackground(backgroundColor);
		formPanel.setPreferredSize(new Dimension(500, 250));

		contentLabel = new JLabel();
		contentLabel.setBackground(Color.WHITE);
		contentLabel.setForeground(Color.BLACK); // 设置文本颜色为黑色
		contentLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentLabel.setVerticalAlignment(SwingConstants.CENTER);

		overviewButton.addActionListener(e -> {
			cardLayout.show(taskPanel, "form");
			currentSection = "Overview";
			updateContent();
		});

		incomeButton.addActionListener(e -> {
			cardLayout.show(taskPanel, "form");
			currentSection = "Income";
			updateContent();
		});

		expenseButton.addActionListener(e -> {
			cardLayout.show(taskPanel, "form");
			currentSection = "Expense";
			updateContent();
		});

		getContentPane().add(mainPanel);
		mainPanel.add(yellowPanel, BorderLayout.NORTH);
		mainPanel.add(sidebarPanel, BorderLayout.WEST);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		homePanel.setLayout(null);

		JLabel lblNewLabel_8 = createRoundLabel(60, new Color(238, 208, 88, 255), Color.WHITE,
				new Color(229, 102, 96, 255), "Welcome~~");
		lblNewLabel_8.setBounds(10, 13, 174, 53);
		homePanel.add(lblNewLabel_8);

		JLabel lblNewLabel_11 = createRoundLabel(90, null, new Color(243, 209, 83, 255), Color.GRAY,
				"<html>00.00<br>&nbsp;RMB</html>");
		lblNewLabel_11.setBounds(257, 19, 90, 90);
		homePanel.add(lblNewLabel_11);
/* 
		JPanel withDrawPanel = new JPanel();
		withDrawPanel.setBounds(0, 0, 0, 0);
		withDrawPanel.setPreferredSize(new Dimension(50, 50));
//		gbc_panel.gridwidth = 7;
//		gbc_panel.gridheight = 3;
		homePanel.add(withDrawPanel);
*/
		JLabel lblNewLabel_9 = new JLabel("Current Account: 0.00 RMB");
		lblNewLabel_9 = createRoundLabel(40, null, new Color(243, 209, 83, 255), new Color(65, 150, 95, 255),
				"Current Account: 0.00 RMB");
		lblNewLabel_9.setBounds(109, 160, 217, 50);
		homePanel.add(lblNewLabel_9);

		JLabel lblNewLabel_10 = new JLabel("Saving Account: 0.00 RMB");
		lblNewLabel_10 = createRoundLabel(40, null, new Color(243, 209, 83, 255), new Color(65, 150, 95, 255),
				"Saving Account: 0.00 RMB");
		lblNewLabel_10.setBounds(109, 231, 217, 38);
		homePanel.add(lblNewLabel_10);
//
		JButton btnNewButton_2 = new JButton("Receive Tasks");
		btnNewButton_2 = createRoundButton(new Color(65,150,95, 255), "Receive Tasks");
		btnNewButton_2.setBounds(133, 296, 129, 40);
		homePanel.add(btnNewButton_2);

		JButton btnNewButton_3 = new JButton("Check History");
		btnNewButton_3 = createRoundButton(new Color(65,150,95, 255), "Check History");
		btnNewButton_3.setBounds(317, 296, 113, 40);
		homePanel.add(btnNewButton_3);

		JButton btnNewButton_4 = new JButton("Log out");
		btnNewButton_4 = createRoundButton(new Color(230,95,91, 255), "Log out");
		btnNewButton_4.setBounds(249, 363, 77, 40);
		homePanel.add(btnNewButton_4);
		centerPanel.add(taskPanel, BorderLayout.CENTER);
		formPanel.add(dataPanel, BorderLayout.CENTER);
		dataPanel.add(contentLabel, BorderLayout.CENTER); // 将contentLabel面板添加到formPanel面板中

		JButton buttonReturn = new JButton("Return to homepage");//createRoundButton(new Color(80, 161, 242, 255), "Return to homepage");
		buttonReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 关闭窗口
			((Window) SwingUtilities.getRoot(buttonReturn)).dispose();
			// 打开帮助页面
			new Home(currentUserId);
			}
		});

		JLabel lblNewLabel_4 = new JLabel("HISTORY OF XX.XX(EG.2024.02)");
		formPanel.add(lblNewLabel_4, BorderLayout.NORTH);
		lblNewLabel_4.setPreferredSize(new Dimension(50, lblNewLabel_4.getPreferredSize().height * 3));
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel lblNewLabel_5 = new JLabel(" ");
		formPanel.add(lblNewLabel_5, BorderLayout.SOUTH);
		lblNewLabel_5.setPreferredSize(new Dimension(50, lblNewLabel_5.getPreferredSize().height * 3));

		JLabel lblNewLabel_6 = new JLabel(" ");
		formPanel.add(lblNewLabel_6, BorderLayout.WEST);
		lblNewLabel_6.setPreferredSize(new Dimension(30, lblNewLabel_6.getPreferredSize().height * 3));

		JLabel lblNewLabel_7 = new JLabel(" ");
		formPanel.add(lblNewLabel_7, BorderLayout.EAST);
		lblNewLabel_7.setPreferredSize(new Dimension(30, lblNewLabel_7.getPreferredSize().height * 3));

		JLabel lblNewLabel = new JLabel("    ");
		centerPanel.add(lblNewLabel, BorderLayout.NORTH);
		lblNewLabel.setPreferredSize(new Dimension(200, lblNewLabel.getPreferredSize().height * 5));

		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
		buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
		buttonPanel.setPreferredSize(new Dimension(50, lblNewLabel_4.getPreferredSize().height + 10));
		formPanel.add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.add(buttonReturn);

		JLabel lblNewLabel_1 = new JLabel(" ");
		centerPanel.add(lblNewLabel_1, BorderLayout.SOUTH);
		lblNewLabel_1.setPreferredSize(new Dimension(200, lblNewLabel_1.getPreferredSize().height * 5));

		JLabel lblNewLabel_2 = new JLabel(" ");
		centerPanel.add(lblNewLabel_2, BorderLayout.WEST);
		lblNewLabel_2.setPreferredSize(new Dimension(lblNewLabel_2.getPreferredSize().height * 5,
				lblNewLabel_2.getPreferredSize().height * 5));

		JLabel lblNewLabel_3 = new JLabel(" ");
		centerPanel.add(lblNewLabel_3, BorderLayout.EAST);
		lblNewLabel_3.setPreferredSize(new Dimension(lblNewLabel_3.getPreferredSize().height * 5,
				lblNewLabel_3.getPreferredSize().height * 5));

		taskPanel.add(homePanel, "home");

		JButton btnNewButton = new JButton("Help? Look here!");
		 btnNewButton = createRoundButton(new Color(229,104,100,255), "Help? Look here!");
		btnNewButton.setBounds(20, 76, 157, 50);
		homePanel.add(btnNewButton);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(409, 23, 262, 130);
		homePanel.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel_14 = new JLabel("How much would you want to withdraw?");
		lblNewLabel_14.setBounds(10, 25, 242, 15);
		panel.add(lblNewLabel_14);

		textField = new JTextField();
		textField.setBounds(20, 55, 210, 21);
		panel.add(textField);
		textField.setColumns(10);

		JButton btnNewButton_1 = new JButton("Cancel");
		btnNewButton_1.setBounds(30, 95, 95, 25);
		panel.add(btnNewButton_1);

		JButton btnNewButton_5 = new JButton("Withdraw");
		btnNewButton_5.setBounds(135, 95, 95, 25);
		panel.add(btnNewButton_5);

		JButton btnNewButton_6 = new JButton("transfer");
		btnNewButton_6 = createRoundButton(new Color(236, 146, 70, 255), "transfer");
		btnNewButton_6.setBounds(336, 173, 100, 30);
		homePanel.add(btnNewButton_6);

		JButton btnNewButton_7 = new JButton("transfer");
		btnNewButton_7 = createRoundButton(new Color(236, 146, 70, 255), "transfer");
		btnNewButton_7.setBounds(336, 230, 100, 30);
		homePanel.add(btnNewButton_7);
		taskPanel.add(formPanel, "form");
		cardLayout.show(taskPanel, "form");
		overviewButton.doClick();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private JButton createSidebarButton(String text) {
		JButton button = new JButton(text);
		button.setForeground(Color.WHITE); // 修改侧边栏按钮文本颜色为白色
		button.setBackground(Color.darkGray);
		button.setBorderPainted(false);
		button.setFocusPainted(false);
		button.setPreferredSize(new Dimension(180, 40));
		return button;
	}

	private JButton createRoundButton(Color color, String text) {
		JButton button = new JButton() {
			protected void paintComponent(Graphics g) {
				if (getModel().isArmed()) {
					g.setColor(Color.GRAY);
				} else {
					g.setColor(color);
				}
				g.fillRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 30, 30);
				super.paintComponent(g);
			}
		};
		button.setText(text);
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.setPreferredSize(
				new Dimension(button.getPreferredSize().width, (int) (button.getPreferredSize().height * 1.5)));
		return button;
	}

	private JLabel createRoundLabel(int arc, Color borderColor, Color bgcolor, Color fgcolor, String text) {
		JLabel label = new JLabel() {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2d = (Graphics2D) g.create();
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.setColor(bgcolor);
				g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);
				super.paintComponent(g);
				g2d.dispose();
			}

			@Override
			protected void paintBorder(Graphics g) {
				if (borderColor == null) {
					return;
				}
				g.setColor(borderColor);
				Stroke thickStroke = new BasicStroke(5.0f);
				Graphics2D g2d = (Graphics2D) g;
				g2d.setStroke(thickStroke);
				g2d.drawRoundRect(0, 0, getSize().width - 1, getSize().height - 1, arc, arc);
			}
		};
		label.setFont(new Font("normal", Font.PLAIN, 16));
		label.setText(text);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(fgcolor);
		return label;
	}

	private void updateContent() {
		switch (currentSection) {
		case "Overview":
			contentLabel.setText("01/02/2024 +XXX for... -XXX for...");
			break;
		case "Income":
			contentLabel.setText("01/02/2024 +XXX for...");
			break;
		case "Expense":
			contentLabel.setText("01/02/2024 -XXX for...");
			break;
		default:
			break;
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new HistoryPage();
		});
	}
}
