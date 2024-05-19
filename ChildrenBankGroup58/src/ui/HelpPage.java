import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class HelpPage extends JFrame {
    public int currentUserId;
    public HelpPage() {
        setTitle("Help Page");
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
            JLabel titleLabel = new JLabel("Help For Kids");
            titleLabel.setFont(new Font("Serif", Font.BOLD, 18));
            titleLabel.setForeground(Color.BLACK);
            yellowPanel.add(titleLabel);
            mainPanel.add(yellowPanel);

            JPanel questionAnswer = new JPanel();
            questionAnswer.setLayout(null);
            questionAnswer.setBackground(new Color(255, 255, 0, 64));
            questionAnswer.setBounds(200, 50, 250, 300);
            JLabel questionTitle = new JLabel("Questions & Answers");
            questionTitle.setFont(new Font("Serif", Font.BOLD, 18));
            questionTitle.setBounds(10, 10, 230, 30);
            questionAnswer.add(questionTitle);

            String[] questions = {
                "How to distinguish between current and death date?",
                "How is the annual interest rate calculated?",
                "How to withdraw money?",
                "etc."
            };

            for (int i = 0; i < questions.length; i++) {
                String question = questions[i];
                JLabel questionLabel = new JLabel((i + 1) + ". " + question);
                questionLabel.setForeground(Color.BLUE);
                questionLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                questionLabel.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        JOptionPane.showMessageDialog(null, question + " content goes here.");
                    }
                });
                questionLabel.setBounds(20, 50 + i * 30, 230, 30);
                questionAnswer.add(questionLabel);
            }

            mainPanel.add(questionAnswer);

            JPanel introduction = new JPanel();
            introduction.setLayout(null);
            introduction.setBackground(new Color(255, 255, 0, 64));
            introduction.setBounds(550, 50, 250, 300);
            JLabel introductionTitle = new JLabel("User Introduction");
            introductionTitle.setFont(new Font("Serif", Font.BOLD, 18));
            introductionTitle.setBounds(50, 10, 230, 30);
            introduction.add(introductionTitle);

            String[] introductionTasks = {
                "Current account and savings account",
                "Task acceptance and reward mechanism",
                "Parents set task Method",
                "Historical income and expenditure review",
                "etc."
            };

            for (int i = 0; i < introductionTasks.length; i++) {
                String task = introductionTasks[i];
                JLabel taskLabel = new JLabel((i + 1) + ". " + task);
                taskLabel.setForeground(Color.BLUE);
                taskLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                taskLabel.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        JOptionPane.showMessageDialog(null, task + " content goes here.");
                    }
                });
                taskLabel.setBounds(20, 50 + i * 30, 230, 30);
                introduction.add(taskLabel);
            }

            mainPanel.add(introduction);

            JButton returnButton = new JButton("Return to homepage");
            returnButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(null, "Returning to homepage.");
                    ((Window) SwingUtilities.getRoot(returnButton)).dispose();
                    // Add action to return to homepage
                    new Home(currentUserId);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new HelpPage();
        });
    }
}