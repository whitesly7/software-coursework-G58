package com;

import com.pojo.Child;
import com.pojo.Parent;
import com.service.ChildService;
import com.service.ParentService;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.io.IOException;
import java.util.UUID;

public class ChildRegisterTest {


    ChildService childService=new ChildService();
    ParentService parentService=new ParentService();

    /**
     * register: no pid
     * @throws IOException
     * @throws IllegalAccessException
     */
    @Test
    public void rtest1() throws IOException, IllegalAccessException {
        Child child=new Child();
        child.setChildName("12121");
        child.setPassword("123456");
        child.setPid(null);
        child.setParentName("parentName");
        child.setCid(UUID.randomUUID().toString());
        child.setCurrentDeposit(0.0);
        child.setSavingDeposit(0.0);
        a1(child,"12121");
    }
    /**
     * register : no password
     * @throws IOException
     * @throws IllegalAccessException
     */

    @Test
    public void rtest2() throws IOException, IllegalAccessException {
        Child child=new Child();
        child.setChildName("12121");
        child.setPassword("");
        child.setPid("123456");
        child.setParentName("parentName");
        child.setCid(UUID.randomUUID().toString());
        child.setCurrentDeposit(0.0);
        child.setSavingDeposit(0.0);
        a1(child,"12121");
    }
    /**
     * register: no account
     * @throws IOException
     * @throws IllegalAccessException
     */
    @Test
    public void rtest3() throws IOException, IllegalAccessException {
        Child child=new Child();
        child.setChildName("");
        child.setPassword("123456");
        child.setPid("123456");
        child.setParentName("parentName");
        child.setCid(UUID.randomUUID().toString());
        child.setCurrentDeposit(0.0);
        child.setSavingDeposit(0.0);
        a1(child,"12121");
    }
    /**
     * register: error
     * @throws IOException
     * @throws IllegalAccessException
     */
    @Test
    public void rtest4() throws IOException, IllegalAccessException {
        Child child=new Child();
        child.setChildName("21");
        child.setPassword("21");
        child.setPid("21");
        child.setParentName("parentName");
        child.setCid(UUID.randomUUID().toString());
        child.setCurrentDeposit(0.0);
        child.setSavingDeposit(0.0);
        a1(child,"12121");
    }
    /**
     * register: account correct,password correct，pid correct
     * @throws IOException
     * @throws IllegalAccessException
     */
    @Test
    public void rtest5() throws IOException, IllegalAccessException {
        Child child=new Child();
        child.setChildName("1");
        child.setPassword("1");
        child.setPid("123456");
        child.setParentName("parentName");
        child.setCid(UUID.randomUUID().toString());
        child.setCurrentDeposit(0.0);
        child.setSavingDeposit(0.0);
        a1(child,"12121");
    }
    private void a1(Child child,String childName) throws IOException, IllegalAccessException {
        Parent parent = parentService.getOneByKey("pid", child.getPid());
        if(parent==null){
           return;
        }
        String childName1 = child.getChildName();
        String password = child.getPassword();
        if (childName1==null||childName1.equals("")){
            return;
        }
        if (password==null||password.equals("")){
            return;
        }
        Child one = childService.getOneByKey("childName", childName);
        if (one!=null&& one.getChildName().equals(childName)){
            return;
        }
        Boolean save = childService.save(child);
        System.out.println(save);
    }
}
