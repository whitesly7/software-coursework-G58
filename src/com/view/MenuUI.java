package com.view;

import com.pojo.Child;
import com.pojo.Parent;
import com.service.ChildService;
import com.view.child.ChildTaskWorkLogListPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;

/**
 * GUI for parent's menu options.
 */
public class MenuUI extends JFrame {
    private JMenuBar menuBar;
    private JMenu taskMenu, childMenu, exitMenu;
    private JMenuItem taskListMenuItem, taskAddMenuItem, childListMenuItem,childWorkMenuItem;
    private JButton switchButton;
    private ChildService childService;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private Child child;

    /**
     * Constructs a MenuUI for the specified parent.
     *
     * @param parent The parent object.
     * @throws IOException            If an I/O error occurs.
     * @throws IllegalAccessException If access to a field is denied.
     */

    public MenuUI(Parent parent) throws IOException, IllegalAccessException {
        super("Parent Menu");
        childService=new ChildService();
        child = childService.getOneByKey("parentName", parent.getParentName());

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        menuBar = new JMenuBar();

        taskMenu = new JMenu("Task");
        taskListMenuItem = new JMenuItem("List");
        taskListMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "TaskListPanel");
            }
        });

        taskAddMenuItem = new JMenuItem("Add");
        taskAddMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "TaskInputPanel");
            }
        });


        taskMenu.add(taskListMenuItem);
        taskMenu.add(taskAddMenuItem);

        childMenu = new JMenu("Child");
        childListMenuItem = new JMenuItem("List");
        childListMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "ChildPanel");
            }
        });
        childMenu.add(childListMenuItem);
        childWorkMenuItem=new JMenuItem("Work Log");
        childWorkMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "ChildTaskWorkLogListPanel");
            }
        });
        childMenu.add(childWorkMenuItem);

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


        JPanel wellcomeJPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // background
                //ImageIcon backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("p.jpg")));
                ImageIcon backgroundImage = new ImageIcon("p.jpg");
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        /*JLabel label1 = new JLabel("welcome");
        wellcomeJPanel.add(label1);
        cardPanel.add(wellcomeJPanel, "wellcomeJPanel");*/

        JLabel label1 = new JLabel("Welcome "+parent.getParentName());
        Font font = new Font("Lucida Calligraphy", Font.BOLD, 36);
        label1.setFont(font);
        wellcomeJPanel.add(label1);
        cardPanel.add(wellcomeJPanel, "wellcomeJPanel");

        //childPanel
        ChildListPanel childPanel = new ChildListPanel(parent);
        cardPanel.add(childPanel, "ChildPanel");
        //Task management
        TaskListPanel taskListPanel = new TaskListPanel(parent);
        cardPanel.add(taskListPanel, "TaskListPanel");

        //task input management
        TaskInputPanel taskInputPanel=new TaskInputPanel(parent);
        cardPanel.add(taskInputPanel, "TaskInputPanel");




        ChildTaskWorkLogListPanel childTaskWorkLogListPanel=new ChildTaskWorkLogListPanel(child);
        cardPanel.add(childTaskWorkLogListPanel, "ChildTaskWorkLogListPanel");


        contentPane.add(cardPanel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) throws IOException {

    }
}
