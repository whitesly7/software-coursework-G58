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
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Panel to display balance logs for a specific child.
 */
public class ChildBalanceLogListPanel extends JPanel {

    private DefaultTableModel tableModel;
    private JTable table;
    private TaskService taskService;
    private TaskWorkLogService taskWorkLogService;
    private BalanceLogService balanceLogService;
    private Child child;
    private ChildService childService;
    private JButton updateButton;

    private final ImageIcon backgroundImage;//background object
    /**
     * Constructs a ChildBalanceLogListPanel object.
     *
     * @param child The child for whom the balance logs are displayed
     * @throws IOException if an I/O error occurs
     */
    public ChildBalanceLogListPanel(Child child) throws IOException {
        this.child=child;
        balanceLogService =new BalanceLogService();
        taskService=new TaskService();
        taskWorkLogService=new TaskWorkLogService();
        childService =new ChildService();

        // add the background pic
        //backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("../c.jpg")));
        backgroundImage = new ImageIcon("c.jpg");

        // initialize table model
        tableModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // cells are not editable
            }
        };

        tableModel.addColumn("Id");
        tableModel.addColumn("Child Name");
        tableModel.addColumn("Type");
        tableModel.addColumn("Amount");
        tableModel.addColumn("Time");
        List<BalanceLog> balanceLogs = balanceLogService.getAll();
        List<BalanceLog> balanceLogList = balanceLogs.stream().filter(c -> {
            return c.getCid().equals(child.getCid());
        }).collect(Collectors.toList());

        // Add BalanceLog objects to the table model
        for (BalanceLog balanceLog : balanceLogList) {
            Object[] rowData = new Object[]{
                    balanceLog.getBid(),
                    balanceLog.getChildName(),
                    balanceLog.getType(),
                    balanceLog.getAmount(),
                    balanceLog.getTime(),
            };
            tableModel.addRow(rowData);
        }

        // Create and configure the table
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

        updateButton = new JButton("Refresh");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    refreshTable(); // Refresh table data when the button is clicked
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        // Create a scroll pane to contain the table
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setOpaque(false); // semitransparent scrollPane
        scrollPane.getViewport().setOpaque(false); // semitransparent scrollPane

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        inputPanel.add(updateButton);
        inputPanel.setOpaque(false); // semitransparent inputPanel

        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Refreshes the table with updated balance logs.
     *
     * @throws IOException if an I/O error occurs
     */
    private void refreshTable() throws IOException {
        tableModel.setRowCount(0); // clear Table
        List<BalanceLog> balanceLogs = balanceLogService.getAll();
        List<BalanceLog> balanceLogList = balanceLogs.stream().filter(c -> c.getCid().equals(child.getCid())).collect(Collectors.toList());
        for (BalanceLog balanceLog : balanceLogList) {
            Object[] rowData = new Object[]{
                    balanceLog.getBid(),
                    balanceLog.getChildName(),
                    balanceLog.getType(),
                    balanceLog.getAmount(),
                    balanceLog.getTime(),
            };
            tableModel.addRow(rowData); // Add new data to the table model
        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //background
        g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
    }

    /**
     * Main method for testing the GUI.
     *
     * @param args command-line arguments
     * @throws IOException if an I/O error occurs
     */
    public static void main(String[] args) throws IOException {
        // create and set up GUI
        JFrame frame = new JFrame("BalanceLog Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ChildBalanceLogListPanel(new Child()));
        frame.pack();
        frame.setVisible(true);
    }
}