package com.view.child;

import com.pojo.Child;
import com.pojo.Parent;
import com.pojo.Task;
import com.pojo.TaskWorkLog;
import com.service.TaskService;
import com.service.TaskWorkLogService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * This panel displays the task list for a child and allows them to work on tasks.
 */
public class ChildTaskListPanel extends JPanel {

    private DefaultTableModel tableModel;
    private JTable table;
    private TaskService taskService;
    private TaskWorkLogService taskWorkLogService;
    private Child child;
    private JButton workButton;
    private JButton updateButton;

    private final ImageIcon backgroundImage;//background object

    /**
     * Constructs a ChildTaskListPanel.
     * @param child The child for whom the tasks are displayed.
     * @throws IOException If an I/O error occurs.
     * @throws IllegalAccessException If access to a class or method is not allowed.
     */
    public ChildTaskListPanel(Child child) throws IOException, IllegalAccessException {
        this.child=child;
        taskWorkLogService=new TaskWorkLogService();
        taskService=new TaskService();

        // initialize table model
        tableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // 所有单元格都不可编辑
            }
        };
        tableModel.addColumn("Task Id");
        tableModel.addColumn("Task Name");
        tableModel.addColumn("Money Reward");
        tableModel.addColumn("Parent Name");
        workButton = new JButton("work");
        // Initialize work button
        workButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int[] selectedRows = table.getSelectedRows();
                if (selectedRows.length!=1) {
                    return;
                }
                int row= selectedRows[0];
                String taskname =  (String) tableModel.getValueAt(row, 1);
                String taskId =  (String) tableModel.getValueAt(row, 0);
                Object[] options = {"save", "cancel"}; // button


                int result = JOptionPane.showOptionDialog(
                        null,
                        "Are you sure you want to choose Task '" + taskname + "'?",
                        "Delete Task",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[1] // default:“cancel”
                );
                if (result == JOptionPane.YES_OPTION) {
                    TaskWorkLog taskWorkLog = new TaskWorkLog();
                    taskWorkLog.setTwid(UUID.randomUUID().toString());
                    taskWorkLog.setTaskName(taskname);
                    taskWorkLog.setTid(taskId);
                    taskWorkLog.setChildName(child.getChildName());
                    taskWorkLog.setCid(child.getCid());
                    taskWorkLog.setStatus("uncompleted");
                    taskWorkLog.setTime(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    try {
                        taskWorkLogService.save(taskWorkLog);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(null, "Add Successfully!","Message", JOptionPane.INFORMATION_MESSAGE);
                } else {
                }
            }
        });

        // Initialize update button
        updateButton = new JButton("Refresh");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    refreshTable();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (IllegalAccessException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // add the background pic
        //backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("../c.jpg")));
        backgroundImage = new ImageIcon("c.jpg");

        // Fetch tasks and filter out completed tasks
        List<Task> tasks = taskService.getListByKey("pid",child.getPid());
        List<Task> newTasks =new ArrayList<>();
        List<TaskWorkLog> taskWorkLogs = taskWorkLogService.getListByKey("cid", child.getCid());
        for (Task task : tasks) {
            boolean flag=false;
            for (TaskWorkLog taskWorkLog : taskWorkLogs) {
                if (taskWorkLog.getTid().equals(task.getTid())) {
                    flag=true;
                    break;
                }
            }
            if (flag){
                continue;
            }
            newTasks.add(task);
        }
        // Add new tasks to table model
        for (Task task : newTasks) {
            Object[] rowData = new Object[]{
                    task.getTid(),
                    task.getTaskName(),
                    task.getMoneyReward(),
                    task.getParentName(),
            };
            tableModel.addRow(rowData);
        }

        // Create and configure table
        table = new JTable(tableModel);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        table.setOpaque(false); // semitransparent table

        // set table transparency and word color
        table.setBackground(new Color(255, 255, 255, 128)); // semitransparent
        table.setForeground(Color.BLACK);
        table.getTableHeader().setOpaque(false);
        table.getTableHeader().setBackground(new Color(255, 255, 255, 128));
        table.getTableHeader().setForeground(Color.BLACK);

        // Create scroll pane to contain table
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setOpaque(false); // semitransparent scrollPane
        scrollPane.getViewport().setOpaque(false); // semitransparent scrollPane

        // Create input panel with work and refresh buttons
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        inputPanel.setOpaque(false); // semitransparent inputPanel
        inputPanel.add(workButton);
        inputPanel.add(updateButton);

        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // background
        g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
    }

    /**
     * Refreshes the task list displayed on the panel.
     * @throws IOException If an I/O error occurs.
     * @throws IllegalAccessException If access to a class or method is not allowed.
     */
    private void refreshTable() throws IOException, IllegalAccessException {
        tableModel.setRowCount(0); // clear table

        List<Task> tasks = taskService.getListByKey("pid",child.getPid());
        List<Task> newTasks =new ArrayList<>();
        List<TaskWorkLog> taskWorkLogs = taskWorkLogService.getListByKey("cid", child.getCid());
        for (Task task : tasks) {
            boolean flag=false;
            for (TaskWorkLog taskWorkLog : taskWorkLogs) {
                if (taskWorkLog.getTid().equals(task.getTid())) {
                    flag=true;
                    break;
                }
            }
            if (flag){
                continue;
            }
            newTasks.add(task);
        }
        for (Task task : newTasks) {
            Object[] rowData = new Object[]{
                    task.getTid(),
                    task.getTaskName(),
                    task.getMoneyReward(),
                    task.getParentName(),
            };
            tableModel.addRow(rowData);
        }
    }


    /**
     * Main method for testing GUI.
     * @param args Command line arguments.
     * @throws IOException If an I/O error occurs.
     * @throws IllegalAccessException If access to a class or method is not allowed.
     */
    public static void main(String[] args) throws IOException, IllegalAccessException {
        // 创建并设置GUI
        JFrame frame = new JFrame("Task Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ChildTaskListPanel(new Child()));
        frame.pack();
        frame.setVisible(true);
    }
}