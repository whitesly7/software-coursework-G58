package com.view;

import com.pojo.Parent;
import com.pojo.Task;
import com.service.TaskService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

/**
 * Panel for inputting new tasks.
 */
public class TaskInputPanel extends JPanel {

    private JTextField taskNameField;
    private JTextField moneyRewardField;
    private JTextField parentNameField;
    private JButton addTaskButton;
    private TaskService taskService;
    private Parent parent;

    /**
     * Constructs a TaskInputPanel with the specified parent.
     *
     * @param parent The parent associated with the tasks.
     */
    public TaskInputPanel(Parent parent) {
        taskService=new TaskService();

        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        taskNameField = new JTextField(10);
        add(new JLabel("Task Name:"), c);
        c.gridx = 1;
        add(taskNameField, c);

        c.gridx = 0;
        c.gridy = 1;
        moneyRewardField = new JTextField(10);
        add(new JLabel("Money Reward:"), c);
        c.gridx = 1;
        add(moneyRewardField, c);

        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2; //Make the button span two columns
        addTaskButton = new JButton("Add Task");
        addTaskButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // add task logics
                Task newTask = new Task(UUID.randomUUID().toString(),taskNameField.getText(), Double.parseDouble(moneyRewardField.getText()),parent.getParentName(),parent.getPid());
                try {
                    taskService.save(newTask);
                    JOptionPane.showMessageDialog(null, "Add Successfully!","Message", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                taskNameField.setText("");
                moneyRewardField.setText("");
                //parentNameField.setText("");
            }
        });
        add(addTaskButton, c);
    }

    /**
     * Overrides the paintComponent method to paint the background image.
     *
     * @param g The Graphics context in which to paint.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //ImageIcon backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("p.jpg")));
        ImageIcon backgroundImage = new ImageIcon("p.jpg");
        Graphics2D g2d = (Graphics2D) g.create();
        float alpha = 0.5f;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2d.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
        g2d.dispose();
    }

    /**
     * Main method (optional) for testing the GUI.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Task Input Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new TaskInputPanel(new Parent()));
        frame.pack();
        frame.setVisible(true);
    }
}