package com.view;

import com.pojo.Parent;
import com.pojo.Task;
import com.service.TaskService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Panel for displaying a list of tasks associated with a parent.
 */
public class TaskListPanel extends JPanel {

    private DefaultTableModel tableModel;
    private JTable table;
    private TaskService taskService;
    private Parent parent;
    private JButton updateButton; // 新增的输入框
    private JButton deleteButton; // 新增的删除按钮

    private JButton refreshButton;

    private final ImageIcon backgroundImage;//background object

    /**
     * Constructs a TaskListPanel with the specified parent.
     *
     * @param parent The parent object whose tasks will be displayed.
     * @throws IOException            If an I/O error occurs.
     * @throws IllegalAccessException If access to a field is denied.
     */
    public TaskListPanel(Parent parent) throws IOException, IllegalAccessException {
        this.parent=parent;
        taskService=new TaskService();

        // initialize table model
        tableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //not editable
            }
        };
        tableModel.addColumn("Task Id");
        tableModel.addColumn("Task Name");
        tableModel.addColumn("Money Reward");
        tableModel.addColumn("Parent Name");

        updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int[] selectedRows = table.getSelectedRows();
                if (selectedRows.length!=1) {
                    return;
                }
                int row= selectedRows[0];
                String tid =  (String) tableModel.getValueAt(row, 0);
                String valueAt =  (String) tableModel.getValueAt(row, 1);

                Object[] options = {"save", "cancel"};
                JTextField textField1 = new JTextField(10);
                JTextField textField2 = new JTextField(10);


                int option = JOptionPane.showOptionDialog(
                        null, // parentComponent，default null
                        new Object[]{"Task Name:", textField1, "Money Reward:", textField2},
                        "Updata "+valueAt,
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        options,
                        options[0]
                );

                // check button choose
                if (option == JOptionPane.YES_OPTION) {
                    String taskName = textField1.getText();
                    String moneyReward = textField2.getText();
                    // process data
                    System.out.println("save：" + taskName + ", " + moneyReward);
                    try {
                        taskService.updateById(tid,new Task(tid,taskName,Double.parseDouble(moneyReward),parent.getParentName(),parent.getPid()));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                } else {
                    // choose cancel
                    System.out.println("cancel");
                }
            }
        });
        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] selectedRows = table.getSelectedRows();
                int row= selectedRows[0];
                String tid =  (String) tableModel.getValueAt(row, 0);
                try {
                    taskService.removeById(tid);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                for (int i = selectedRows.length - 1; i >= 0; i--) {
                    tableModel.removeRow(selectedRows[i]);
                }


            }
        });
        refreshButton =new JButton("Refresh");
        refreshButton.addActionListener(new ActionListener() {
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
        //backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("p.jpg")));
        backgroundImage = new ImageIcon("p.jpg");

        List<Task> tasks = taskService.getListByKey("pid",parent.getPid());;
        // add object to table
        for (Task task : tasks) {
            Object[] rowData = new Object[]{
                    task.getTid(),
                    task.getTaskName(),
                    task.getMoneyReward(),
                    task.getParentName(),
            };
            tableModel.addRow(rowData);
        }

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

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setOpaque(false); // semitransparent scrollPane
        scrollPane.getViewport().setOpaque(false); // semitransparent scrollPane

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        inputPanel.setOpaque(false); // semitransparent inputPanel
        inputPanel.add(deleteButton);
        inputPanel.add(updateButton);
        inputPanel.add(refreshButton);

        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
    }






    /**
     * Refreshes the table with updated data.
     *
     * @throws IOException            If an I/O error occurs.
     * @throws IllegalAccessException If access to a field is denied.
     */
    private void refreshTable() throws IOException, IllegalAccessException {
        tableModel.setRowCount(0);

        List<Task> tasks = taskService.getListByKey("pid",parent.getPid());
        for (Task task : tasks) {
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
     * Main method to launch the application.
     *
     * @param args Command line arguments (not used).
     * @throws IOException            If an I/O error occurs.
     * @throws IllegalAccessException If access to a field is denied.
     */
    public static void main(String[] args) throws IOException, IllegalAccessException {
        JFrame frame = new JFrame("Task Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new TaskListPanel(new Parent()));
        frame.pack();
        frame.setVisible(true);
    }
}