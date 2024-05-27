package com.view.child;

import com.pojo.BalanceLog;
import com.pojo.Child;
import com.service.BalanceLogService;
import com.service.ChildService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Panel for displaying child's information and performing financial operations.
 */
public class ChildPanel extends JPanel {

    private JLabel childNameLabel;
    private JLabel passwordLabel;
    private JLabel currentDepositLabel;
    private JLabel savingDepositLabel;
    private JLabel parentNameLabel;

    private JLabel interestameLabel;

    private JLabel goalLabel;
    private ChildService childService;
    private BalanceLogService balanceLogService;

    private JTextField withdrawAmountField;

    private JTextField saveAmountField;
    private JTextField drawFixedMoneyField;
    private JTextField goalField;

    private JButton withdrawButton;

    private JButton saveButton;

    private JButton drawFixedMoneyButton;

    private JButton goalButton;
    private Child child;

    /**
     * Constructs a ChildPanel with the specified child.
     *
     * @param child1 The child whose information will be displayed and managed.
     */
    public ChildPanel(Child child1) {
        child = child1;
        childService = new ChildService();
        balanceLogService = new BalanceLogService();
        // initialize person information
        childNameLabel = new JLabel("Child Name: " + child.getChildName());
        passwordLabel = new JLabel("Password: "+child.getPassword()); // 通常不直接显示密码
        currentDepositLabel = new JLabel("Current Deposit: " + child.getCurrentDeposit());
        //upgrade Current Deposit
        child.addDepositChangeListener(newDeposit -> {
            currentDepositLabel.setText("Current Deposit: " + newDeposit);
        });
        savingDepositLabel = new JLabel("Saving Deposit: " + child.getSavingDeposit());
        parentNameLabel = new JLabel("Parent Name: " + child.getParentName());
        goalLabel = new JLabel("Plan of the Week:" + child.getGoal());
        interestameLabel = new JLabel("Saving Interest: 1.5% (Annual)");
        // initialize input
        withdrawAmountField = new JTextField(4);
        saveAmountField = new JTextField(4);
        drawFixedMoneyField = new JTextField(4);
        goalField = new JTextField(4);
        withdrawButton = new JButton("Withdraw");
        saveButton = new JButton("Save");
        drawFixedMoneyButton = new JButton("DrawFixedMoney");
        goalButton = new JButton("Goal");

        // add action listener
        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    child = childService.getOneByKey("cid", child.getCid());
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                double withdrawAmount = 0.0;
                try {
                    withdrawAmount = Double.parseDouble(withdrawAmountField.getText().trim());
                    if (withdrawAmount >= 0 && withdrawAmount <= child.getCurrentDeposit()) {
                        // TODO: withdraw logics
                        currentDepositLabel.setText("Current Deposit: " + (child.getCurrentDeposit() - withdrawAmount));

                        child.setCurrentDeposit(child.getCurrentDeposit() - withdrawAmount);
                        childService.updateById(child.getCid(), child);
                        BalanceLog balanceLog = new BalanceLog();
                        balanceLog.setCid(child.getCid());
                        balanceLog.setBid(UUID.randomUUID().toString());
                        balanceLog.setChildName(child.getChildName());
                        balanceLog.setTime(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                        balanceLog.setAmount(withdrawAmount);
                        balanceLog.setType("withdraw");
                        balanceLogService.save(balanceLog);
                        JOptionPane.showMessageDialog(null, "Successful withdrawal!");

                        // 清空输入框
                        withdrawAmountField.setText("");

                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid withdrawal amount!");
                    }
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid withdrawal amount!");
                }
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    child = childService.getOneByKey("cid", child.getCid());
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                double saveAmount = 0.0;
                try {
                    saveAmount = Double.parseDouble(saveAmountField.getText().trim());
                    if (saveAmount > 0 && saveAmount <= child.getCurrentDeposit()) {
                        // TODO: save logics
                        currentDepositLabel.setText("Current Deposit: " + (child.getCurrentDeposit() - saveAmount));
                        savingDepositLabel.setText("Saving Deposit: " + (child.getSavingDeposit() + saveAmount));

                        child.setCurrentDeposit(child.getCurrentDeposit() - saveAmount);
                        child.setSavingDeposit(child.getSavingDeposit() + saveAmount);
                        childService.updateById(child.getCid(), child);

                        BalanceLog balanceLog = new BalanceLog();
                        balanceLog.setCid(child.getCid());
                        balanceLog.setBid(UUID.randomUUID().toString());
                        balanceLog.setChildName(child.getChildName());
                        balanceLog.setTime(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                        balanceLog.setAmount(saveAmount);
                        balanceLog.setType("save");
                        balanceLogService.save(balanceLog);
                        JOptionPane.showMessageDialog(null, "Save successful!");

                        // 清空输入框
                        saveAmountField.setText("");

                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid saving amount!");
                    }
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid saving amount!");
                }
            }
        });
        drawFixedMoneyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    child = childService.getOneByKey("cid", child.getCid());
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

                Double drawFixedMoney = Double.parseDouble(drawFixedMoneyField.getText().trim());
                Double savingDeposit = child.getSavingDeposit();
                Double currentDeposit = child.getCurrentDeposit();

                if (savingDeposit >= drawFixedMoney) {
                    if(drawFixedMoney > 100){
                        savingDeposit = savingDeposit - drawFixedMoney;
                        currentDeposit = currentDeposit + drawFixedMoney;
                        currentDepositLabel.setText("Current Deposit: " + (child.getCurrentDeposit() + drawFixedMoney));
                        savingDepositLabel.setText("Saving Deposit: " + (child.getSavingDeposit() - drawFixedMoney));
                        child.setCurrentDeposit(currentDeposit);
                        child.setSavingDeposit(savingDeposit);
                        try {
                            childService.updateById(child.getCid(), child);
                            BalanceLog balanceLog = new BalanceLog();
                            balanceLog.setCid(child.getCid());
                            balanceLog.setBid(UUID.randomUUID().toString());
                            balanceLog.setChildName(child.getChildName());
                            balanceLog.setTime(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                            balanceLog.setAmount(drawFixedMoney);
                            balanceLog.setType("draw fixed");
                            balanceLogService.save(balanceLog);
                            JOptionPane.showMessageDialog(null, "Withdrawal to Current successful!");
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        //清空输入框
                        drawFixedMoneyField.setText("");

                    }else{
                        JOptionPane.showMessageDialog(null, "Sorry, it has to be more than 100 RMB");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Sorry, you don't have enough money in saving account");
                }
            }
        });
        goalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String goal = goalField.getText();
                child.setGoal(goal);
                try {
                    childService.updateById(child.getCid(), child);
                    goalLabel.setText("Plan of the Week:" + child.getGoal());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                //清空输入框
                goalField.setText("");
            }
        });
        //set position
        setLayout(new GridLayout(2, 1));
        JPanel topPanel = new JPanel(new GridLayout(4, 2));
        //setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        topPanel.setOpaque(false);
        topPanel.add(childNameLabel);
        topPanel.add(passwordLabel);
        topPanel.add(currentDepositLabel);
        topPanel.add(savingDepositLabel);
        topPanel.add(parentNameLabel);
        topPanel.add(goalLabel);
        topPanel.add(interestameLabel);
        add(topPanel);

        JPanel bottomPanel = new JPanel(new GridLayout(2, 2));
        bottomPanel.setOpaque(false);
        JPanel withdrawPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        withdrawPanel.setOpaque(false);
        withdrawPanel.setBackground(new Color(0, 0, 0, 0));
        withdrawPanel.add(new JLabel("Withdraw Amount:"));
        withdrawPanel.add(withdrawAmountField);
        withdrawPanel.add(withdrawButton);
        bottomPanel. add(withdrawPanel);

        JPanel withdrawPanel3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        withdrawPanel3.setOpaque(false);
        withdrawPanel3.setBackground(new Color(0, 0, 0, 0));

        withdrawPanel3.add(new JLabel("Save Amount:"));


        withdrawPanel3.add(saveAmountField);
        withdrawPanel3.add(saveButton);
        bottomPanel.add(withdrawPanel3);


        JPanel withdrawPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        withdrawPanel2.setOpaque(false);
        withdrawPanel2.setBackground(new Color(0, 0, 0, 0));
        withdrawPanel2.add(new JLabel("Draw Fixed Money:"));

        withdrawPanel2.add(drawFixedMoneyField);
        withdrawPanel2.add(drawFixedMoneyButton);

        bottomPanel.add(withdrawPanel2);

        JPanel withdrawPanel4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        withdrawPanel4.setOpaque(false);
        withdrawPanel4.setBackground(new Color(0, 0, 0, 0));

        withdrawPanel4.add(new JLabel("Goal:"));

        withdrawPanel4.add(goalField);
        withdrawPanel4.add(goalButton);
        bottomPanel.add(withdrawPanel4);
        add(bottomPanel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //ImageIcon backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("../c.jpg")));
        ImageIcon backgroundImage = new ImageIcon("c.jpg");
        Graphics2D g2d = (Graphics2D) g.create();
        float alpha = 0.5f;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2d.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
        g2d.dispose();
    }

    /**
     * Main method to launch the application for testing purposes.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Child Information");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(2000, 500);
            Child child = new Child();
            ChildPanel childPanel = new ChildPanel(child);
            frame.add(childPanel);
            frame.pack();
            frame.setLocationRelativeTo(null);

            frame.setVisible(true);
        });
    }
}
