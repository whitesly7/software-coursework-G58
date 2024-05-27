package com;

import com.pojo.BalanceLog;
import com.pojo.Child;
import com.service.BalanceLogService;
import com.service.ChildService;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Moneytest {

    private ChildService childService =new ChildService();
    private BalanceLogService balanceLogService =new BalanceLogService();

    /**
     * save: currentAccount has money
     * @throws IOException
     * @throws IllegalAccessException
     */
    @Test
    public void  saveMoneyTest1() throws IOException {
        Child child=new Child();
        child.setChildName("1");
        child.setCid("7c077872-4c7a-4049-b90e-f0104137e276");
        child.setCurrentDeposit(20.0);
        child.setSavingDeposit(0.0);
        saveMoney(10.0,child);
    }
    /**
     * save: currentAccount doesn't have money
     * @throws IOException
     * @throws IllegalAccessException
     */
    @Test
    public void  saveMoneyTest2() throws IOException {
        Child child=new Child();
        child.setChildName("1");
        child.setCid("7c077872-4c7a-4049-b90e-f0104137e276");
        child.setCurrentDeposit(0.0);
        child.setSavingDeposit(0.0);
        saveMoney(10.0,child);
    }
    /**
     * save: same difference
     * @throws IOException
     * @throws IllegalAccessException
     */
    @Test
    public void  saveMoneyTest3() throws IOException {
        Child child=new Child();
        child.setChildName("1");
        child.setCid("7c077872-4c7a-4049-b90e-f0104137e276");
        child.setCurrentDeposit(10.0);
        child.setSavingDeposit(0.0);
        saveMoney(10.0,child);
    }
    /**
     * save :Saving Account is 0.0
     * @throws IOException
     * @throws IllegalAccessException
     */
    @Test
    public void  saveMoneyTest4() throws IOException {
        Child child=new Child();
        child.setChildName("1");
        child.setCid("7c077872-4c7a-4049-b90e-f0104137e276");
        child.setCurrentDeposit(10.0);
        child.setSavingDeposit(0.0);
        saveMoney(0.0,child);
    }
    /**
     * save:save 0 RMB
     * @throws IOException
     * @throws IllegalAccessException
     */
    @Test
    public void  saveMoneyTest5() throws IOException {
        Child child=new Child();
        child.setChildName("1");
        child.setCid("7c077872-4c7a-4049-b90e-f0104137e276");
        child.setCurrentDeposit(10.0);
        child.setSavingDeposit(10.0);
        saveMoney(0.0,child);
    }
    /**
     * save: don't have cid
     * @throws IOException
     * @throws IllegalAccessException
     */
    @Test
    public void  saveMoneyTest6() throws IOException {
        Child child=new Child();
        child.setChildName("1");
        child.setCid(null);
        child.setCurrentDeposit(10.0);
        child.setSavingDeposit(10.0);
        saveMoney(0.0,child);
    }
    /**
     * withdraw: CurrentBalance = SavingBalance
     * @throws IOException
     * @throws IllegalAccessException
     */
    public void  drawFixedMoneyTest1(){
        Child child=new Child();
        child.setChildName("1");
        child.setCid("7c077872-4c7a-4049-b90e-f0104137e276");
        child.setCurrentDeposit(10.0);
        child.setSavingDeposit(10.0);
        drawFixedMoney(child,10.0);
    }
    /**
     * withdraw: more than currentBalance
     * @throws IOException
     * @throws IllegalAccessException
     */
    public void  drawFixedMoneyTest2(){
        Child child=new Child();
        child.setChildName("1");
        child.setCid("7c077872-4c7a-4049-b90e-f0104137e276");
        child.setCurrentDeposit(10.0);
        child.setSavingDeposit(10.0);
        drawFixedMoney(child,20.0);
    }
    /**
     * withdraw: all in currentBalance
     * @throws IOException
     * @throws IllegalAccessException
     */
    public void  drawFixedMoneyTest3(){
        Child child=new Child();
        child.setChildName("1");
        child.setCid("7c077872-4c7a-4049-b90e-f0104137e276");
        child.setCurrentDeposit(10.0);
        child.setSavingDeposit(20.0);
        drawFixedMoney(child,20.0);
    }
    /**
     * withdraw:0
     * @throws IOException
     * @throws IllegalAccessException
     */
    public void  drawFixedMoneyTest4(){
        Child child=new Child();
        child.setChildName("1");
        child.setCid("7c077872-4c7a-4049-b90e-f0104137e276");
        child.setCurrentDeposit(10.0);
        child.setSavingDeposit(20.0);
        drawFixedMoney(child,0.0);
    }


    public void saveMoney(Double saveAmount, Child child) throws IOException {
        if (saveAmount > 0 && saveAmount <= child.getCurrentDeposit()) {
            // TODO: save logics

            child.setCurrentDeposit(child.getCurrentDeposit() - saveAmount);
            child.setSavingDeposit(child.getSavingDeposit() + saveAmount);
            childService.updateById(child.getCid(),child);


            BalanceLog balanceLog=new BalanceLog();
            balanceLog.setCid(child.getCid());
            balanceLog.setBid(UUID.randomUUID().toString());
            balanceLog.setChildName(child.getChildName());
            balanceLog.setTime(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            balanceLog.setAmount(saveAmount);
            balanceLog.setType("save");
            balanceLogService.save(balanceLog);
            System.out.println("Withdrawal successful!");
            System.out.println(child.toString());
        } else {
            System.out.println("Invalid withdrawal amount!");
        }
    }



    public void  drawFixedMoney(Child child,Double drawFixedMoney ) {
        Double savingDeposit = child.getSavingDeposit();
        Double currentDeposit = child.getCurrentDeposit();
        if (savingDeposit>currentDeposit) {
            savingDeposit = savingDeposit - drawFixedMoney;
            currentDeposit = currentDeposit+ drawFixedMoney;
            child.setCurrentDeposit(currentDeposit);
            child.setSavingDeposit(savingDeposit);
            try {
                childService.updateById(child.getCid(),child);
                System.out.println(child.toString());
            } catch (IOException ex) {
                System.out.println("ERROR");
            }
        }else {
            System.out.println("ERROR");
        }
    }
}
