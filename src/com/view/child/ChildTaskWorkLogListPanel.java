package com.view.child;

import com.pojo.BalanceLog;
import com.pojo.Child;
import com.pojo.Task;
import com.pojo.TaskWorkLog;
import com.service.BalanceLogService;
import com.service.ChildService;
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
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Panel displaying the list of task work logs for a child.
 */
public class ChildTaskWorkLogListPanel extends JPanel {

    private DefaultTableModel tableModel;
    private JTable table;
    private TaskService taskService;
    private TaskWorkLogService taskWorkLogService;
    private Child child;
    private BalanceLogService balanceLogService;
    private ChildService childService;
    private JButton updateButton;
    private JButton refreshButton;

    private final ImageIcon backgroundImage;//background object

    /**
     * Constructs a ChildTaskWorkLogListPanel.
     * @param child The child for whom the task work logs are displayed.
     * @throws IOException If an I/O error occurs.
     */
    public ChildTaskWorkLogListPanel(Child child) throws IOException {
        this.child=child;
        balanceLogService=new BalanceLogService();
        taskService=new TaskService();
        taskWorkLogService=new TaskWorkLogService();
        childService =new ChildService();

        // Initialize table model
        tableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // not editable
            }
        };
        tableModel.addColumn("Twid");
        tableModel.addColumn("Task Name");
        tableModel.addColumn("Child Name");
        tableModel.addColumn("status");
        tableModel.addColumn("Time");
        tableModel.addColumn("Tid");

        updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int[] selectedRows = table.getSelectedRows();
                if (selectedRows.length!=1) {
                    return;
                }
                int row= selectedRows[0];
                String taskName =  (String) tableModel.getValueAt(row, 1);
                String twid =  (String) tableModel.getValueAt(row, 0);
                String tid =  (String) tableModel.getValueAt(row, 5);
                Object[] options = {"save", "cancel"};

                int result = JOptionPane.showOptionDialog(
                        null,
                        "Are you sure you want to finish the task '" + taskName + "'?",
                        "Finish Task",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[1] // default “cancel”
                );

                // check button chosen
                if (result == JOptionPane.YES_OPTION) {

                    String status= (String) tableModel.getValueAt(row, 3);
                    if (status.equals("completed")){
                        JOptionPane.showMessageDialog(
                                null,
                                "This task has already been completed.",
                                "Task Completed",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                        return;
                    }

                    TaskWorkLog taskWorkLog = new TaskWorkLog();
                    taskWorkLog.setTid(tid);
                    taskWorkLog.setTaskName(taskName);
                    taskWorkLog.setTwid(twid);
                    taskWorkLog.setChildName(child.getChildName());
                    taskWorkLog.setStatus("completed");
                    taskWorkLog.setCid(child.getCid());
                    taskWorkLog.setTime((String) tableModel.getValueAt(row, 4));

                    try {
                        taskWorkLogService.updateById(twid,taskWorkLog);
                        Task task = taskService.getOneByKey("tid", tid);
                        Double moneyReward = task.getMoneyReward();
                        child.setCurrentDeposit(child.getCurrentDeposit()+moneyReward);
                        childService.updateById(child.getCid(),child);

                        BalanceLog balanceLog=new BalanceLog();
                        balanceLog.setChildName(child.getChildName());
                        balanceLog.setCid(child.getCid());
                        balanceLog.setBid(UUID.randomUUID().toString());
                        balanceLog.setTime(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                        balanceLog.setAmount(moneyReward);
                        balanceLog.setType("work");
                        balanceLogService.save(balanceLog);

                        taskService.removeById(tid);

                    } catch (Exception ioException) {
                        ioException.printStackTrace();
                    }
                } else {
                    // choose cancel
                    System.out.println("cancel");
                }
            }
        });

        // Initialize refresh button
        refreshButton=new JButton("Refresh");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    refreshTable();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // add the background pic
        //backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("../p.jpg")));
        backgroundImage = new ImageIcon("p.jpg");

        // Retrieve task work logs for the child
        List<TaskWorkLog> tasks = taskWorkLogService.getAll();
        List<TaskWorkLog> tasks2 = tasks.stream().filter(c -> {
            return c.getCid().equals(child.getCid());
        }).collect(Collectors.toList());
        // TaskWorkLog objects to the table model
        for (TaskWorkLog task : tasks2) {
            Object[] rowData = new Object[]{
                    task.getTwid(),
                    task.getTaskName(),
                    task.getChildName(),
                    task.getStatus(),
                    task.getTime(),
                    task.getTid(),
            };
            tableModel.addRow(rowData);
        }

        // Create and configure table
        table = new JTable(tableModel);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        table.setOpaque(false); // semitransparent table

        // Create scroll pane to contain table
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setOpaque(false); // semitransparent scrollPane
        scrollPane.getViewport().setOpaque(false); // semitransparent scrollPane

        // set table transparency and word color
        table.setBackground(new Color(255, 255, 255, 128)); // semitransparent
        table.setForeground(Color.BLACK);
        table.getTableHeader().setOpaque(false);
        table.getTableHeader().setBackground(new Color(255, 255, 255, 128));
        table.getTableHeader().setForeground(Color.BLACK);


        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        inputPanel.setOpaque(false); // semitransparent inputPanel
        inputPanel.add(updateButton);
        inputPanel.add(refreshButton);

        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Refreshes the table with updated data.
     * @throws IOException If an I/O error occurs.
     */
    private void refreshTable() throws IOException {
        tableModel.setRowCount(0); // clear table
        List<TaskWorkLog> tasks = taskWorkLogService.getAll();
        List<TaskWorkLog> tasks2 = tasks.stream().filter(c -> {
            return c.getCid().equals(child.getCid());
        }).collect(Collectors.toList());

        for (TaskWorkLog task : tasks2) {
            Object[] rowData = new Object[]{
                    task.getTwid(),
                    task.getTaskName(),
                    task.getChildName(),
                    task.getStatus(),
                    task.getTime(),
                    task.getTid(),
            };
            tableModel.addRow(rowData);
        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // background
        g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
    }

    /**
     * Main method to test GUI.
     * @param args Command-line arguments.
     * @throws IOException If an I/O error occurs.
     */
    public static void main(String[] args) throws IOException {

        JFrame frame = new JFrame("Task Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ChildTaskWorkLogListPanel(new Child()));
        frame.pack();
        frame.setVisible(true);
    }
}