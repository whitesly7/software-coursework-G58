import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class SavingAccount extends JFrame{
    public SavingAccount(){
        setTitle("Saving Account");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);

        try {
            BufferedImage backgroundImage = ImageIO.read(new File("background.jpg"));
            JPanel mainPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                    g.setColor(new Color(255, 255, 255, 128));
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            };
            mainPanel.setLayout(null);

            JPanel yellowPanel = new JPanel();
            yellowPanel.setBackground(new Color(255, 241, 67));
            yellowPanel.setBounds(0, 0, getWidth(), 30);
            JLabel titleLabel = new JLabel("Saving Account");
            titleLabel.setFont(new Font("Serif", Font.BOLD, 18));
            titleLabel.setForeground(Color.BLACK);
            yellowPanel.add(titleLabel);
            mainPanel.add(yellowPanel);

            JPanel Saving1 = new JPanel();
            Saving1.setLayout(null);
            Saving1.setBackground(new Color(255, 255, 0, 64));
            Saving1.setBounds(200, 50, 250, 300);

            String[] saving1 = {
                "The minimum deposit:",
                "1 year& 100RMB",
                "The annual interest rate is 1.51%."
                
            };
                
            
            for (int i = 0; i < saving1.length; i++) {
                String saving = saving1[i];
                JLabel savingLabel = new JLabel(saving);
                savingLabel.setForeground(Color.BLACK);
                savingLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                savingLabel.setBounds(20, 50 + i * 30, 230, 30);
                Saving1.add(savingLabel);
            }

            JButton chooseButton=new JButton("Choose!");
            Saving1.add(chooseButton);
           
            mainPanel.add(Saving1);

            JPanel Saving2 = new JPanel();
            Saving2.setLayout(null);
            Saving2.setBackground(new Color(255, 255, 0, 64));
            Saving2.setBounds(550, 50, 250, 300);

            String[] saving2 = {
                "The minimum deposit:",
                "2 year& 500RMB",
                "The annual interest rate is 3.11%"
            };

            for (int i = 0; i < saving2.length; i++) {
                String saving = saving2[i];
                JLabel savingLabel = new JLabel(saving);
                savingLabel.setForeground(Color.BLACK);
                savingLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                savingLabel.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        JOptionPane.showMessageDialog(null, saving + " content goes here.");
                    }
                });
                savingLabel.setBounds(20, 50 + i * 30, 230, 30);
                Saving2.add(savingLabel);
            }

            JButton chooseButton2=new JButton("Choose!");
            chooseButton2.addActionListener(this::FinishButtonClick);
            Saving2.add(chooseButton2);
            mainPanel.add(Saving2);

            JButton returnButton = new JButton("Return to homepage");
            returnButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(null, "Returning to homepage.");
                    // Add action to return to homepage
                    
                }
            });
            returnButton.setBounds(350, 380, 300, 50);
            returnButton.setBackground(new Color(82, 161, 242));
            returnButton.setForeground(Color.black);
            returnButton.setFont(new Font("Serif", Font.BOLD, 20));
            mainPanel.add(returnButton);

            add(mainPanel);

        } catch (IOException e) {
            e.printStackTrace();
        }

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void FinishButtonClick(ActionEvent e) {
        JButton finishButton = (JButton) e.getSource();
    
        int result = JOptionPane.showConfirmDialog(this, "Are you sure?", "Warning", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            // 弹出成功完成的对话框
            JOptionPane acceptSuccessDialog = new JOptionPane("Choose successfully");
            JDialog dialog = acceptSuccessDialog.createDialog(this, "Choose successfully");
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
    
            // 延迟一秒关闭对话框
            Timer timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    dialog.dispose(); // 关闭成功完成的对话框
                    finishButton.setText("Choosen"); // 修改按钮文本为"Have Finished"
                    finishButton.setEnabled(false); // 设置按钮不可点击
                }
            });
            timer.setRepeats(false); //定时器不重复执行
            timer.start();
            }
        }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SavingAccount();
        });
    }
}
