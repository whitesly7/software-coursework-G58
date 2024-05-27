package com.view;

import com.pojo.Child;
import com.pojo.Parent;
import com.service.ChildService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * Panel displaying a list of children associated with a parent.
 */
public class ChildListPanel extends JPanel {

    private DefaultTableModel tableModel;
    private JTable table;
    private ChildService childService;

    private final ImageIcon backgroundImage;//background object

    /**
     * Constructs a ChildListPanel with the specified parent.
     *
     * @param parent The parent object whose children will be displayed.
     * @throws IOException            If an I/O error occurs.
     * @throws IllegalAccessException If access to a field is denied.
     */
    public ChildListPanel(Parent parent) throws IOException, IllegalAccessException {
        childService=new ChildService();

        // add the background pic
        //backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("p.jpg")));
        backgroundImage = new ImageIcon("p.jpg");

        // Initialize table model
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Child Name");
        tableModel.addColumn("Password");
        tableModel.addColumn("Current Deposit");
        tableModel.addColumn("Saving Deposit");
        tableModel.addColumn("Parent Name");
        tableModel.addColumn("blotter");



        List<Child> children = childService.getListByKey("pid",parent.getPid());
        // Add Child objects to the table model
        for (Child child : children) {
            Object[] rowData = new Object[]{child.getChildName(), "*****", child.getCurrentDeposit(), child.getSavingDeposit(), child.getParentName(),Buttons(child)};
            // security protection
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

        // Create a scroll pane to contain the table
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setOpaque(false); // semitransparent scrollPane
        scrollPane.getViewport().setOpaque(false); // semitransparent scrollPane

        // Set layout and add the scroll pane to the panel
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Creates a panel with action buttons for the specified child.
     *
     * @param child The child object for which action buttons are created.
     * @return The panel containing action buttons for the child.
     */
    public JPanel Buttons(Child child) {
        JPanel actionPanel = new JPanel(); // 使用FlowLayout作为默认布局
        JButton showButton = new JButton("show");

        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {}});
        actionPanel.add(showButton);
        return actionPanel;
    }

    /**
     * Overrides the paintComponent method to paint the background image.
     *
     * @param g The Graphics context in which to paint.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // background
        g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
    }

    /**
     * Main method for testing the GUI.
     *
     * @param args Command line arguments (not used).
     * @throws IOException            If an I/O error occurs.
     * @throws IllegalAccessException If access to a field is denied.
     */
    public static void main(String[] args) throws IOException, IllegalAccessException {
        JFrame frame = new JFrame("Child Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ChildListPanel(new Parent()));
        frame.pack();
        frame.setVisible(true);
    }
}