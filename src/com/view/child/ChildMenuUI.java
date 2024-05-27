package com.view.child;

import com.pojo.Child;
import com.service.ChildService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;

/**
 * This class represents the graphical user interface for managing child-related tasks and information.
 */
public class ChildMenuUI extends JFrame {
    private JMenuBar menuBar;
    private JMenu taskMenu, childMenu, exitMenu;
    private JMenuItem taskListMenuItem, taskLogMenuItem, childListMenuItem, balanceLogenuItem;
    private JPanel cardPanel;
    private CardLayout cardLayout;

    /**
     * Constructs a ChildMenuUI object with the specified child information.
     *
     * @param child The Child object for whom the menu is being displayed.
     * @throws IOException        If an I/O error occurs.
     * @throws IllegalAccessException If access to the Child object is illegal.
     */
    public ChildMenuUI(Child child) throws IOException, IllegalAccessException {
        super("Child Menu");

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        menuBar = new JMenuBar();

        taskMenu = new JMenu("Task");
        taskListMenuItem = new JMenuItem("List");
        taskListMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "ChildTaskListPanel");
            }
        });

        taskLogMenuItem = new JMenuItem("Log");
        taskLogMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "ChildTaskWorkLogListPanel");
            }
        });

        taskMenu.add(taskListMenuItem);
        taskMenu.add(taskLogMenuItem);

        childMenu = new JMenu("Child");
        childListMenuItem = new JMenuItem("info");

        childListMenuItem = new JMenuItem("info");
        childListMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // initialize ChildPanel
                ChildService childService = new ChildService();
                ChildPanel newChildPanel = null;
                try {
                    newChildPanel = new ChildPanel(childService.getOneByKey("childName", child.getChildName()));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (IllegalAccessException ex) {
                    throw new RuntimeException(ex);
                }
                System.out.println(child.getCurrentDeposit());
                cardPanel.remove(newChildPanel);
                cardPanel.add(newChildPanel, "ChildPanel");
                cardLayout.show(cardPanel, "ChildPanel");
            }
        });
        childMenu.add(childListMenuItem);

        balanceLogenuItem = new JMenuItem("BalanceLog");
        balanceLogenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "ChildBalanceLogListPanel");
            }
        });
        childMenu.add(balanceLogenuItem);

        exitMenu = new JMenu("Exit");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        exitMenu.add(exitMenuItem);
        menuBar.add(taskMenu);
        menuBar.add(childMenu);
        menuBar.add(exitMenu);

        setJMenuBar(menuBar);

        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);

        JPanel wellcomeJPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // background
                //ImageIcon backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("../c.jpg")));
                ImageIcon backgroundImage = new ImageIcon("c.jpg"); // 替换为你的背景图路径
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        JLabel label1 = new JLabel("Welcome "+child.getChildName());
        Font font = new Font("Lucida Calligraphy", Font.BOLD, 36);// Set font to Lucida Calligraphy, bold, size 36
        label1.setFont(font);
        wellcomeJPanel.add(label1);
        cardPanel.add(wellcomeJPanel, "wellcomeJPanel");

        // Add child management panel
        ChildPanel childPanel = new ChildPanel(child);
        cardPanel.add(childPanel, "ChildPanel");
        // Add task management panel
        ChildTaskListPanel childTaskListPanel = new ChildTaskListPanel(child);
        cardPanel.add(childTaskListPanel, "ChildTaskListPanel");

        ChildTaskWorkLogListPanel childTaskWorkLogListPanel = new ChildTaskWorkLogListPanel(child);
        cardPanel.add(childTaskWorkLogListPanel, "ChildTaskWorkLogListPanel");

        ChildBalanceLogListPanel childBalanceLogListPanel = new ChildBalanceLogListPanel(child);
        cardPanel.add(childBalanceLogListPanel, "ChildBalanceLogListPanel");

        contentPane.add(cardPanel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Main method to launch the ChildMenuUI application.
     *
     * @param args Command line arguments.
     * @throws IOException        If an I/O error occurs.
     * @throws IllegalAccessException If access to the Child object is illegal.
     */
    public static void main(String[] args) throws IOException, IllegalAccessException {
        Child dummyChild = new Child();
        new ChildMenuUI(dummyChild);
    }
}
