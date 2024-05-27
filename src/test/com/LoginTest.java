package com;

import com.pojo.Parent;
import com.service.ParentService;
import org.junit.jupiter.api.Test;


import java.io.IOException;

public class LoginTest {

    private ParentService parentService=new ParentService();
    /**
     * login:account correct,password correct
     * @throws IOException
     * @throws IllegalAccessException
     */
    @Test
    public void Test1() throws IOException, IllegalAccessException {
        String username ="123456";
        String password ="123456";
        Parent parent = validateLogin(username, password);
        System.out.println(parent);
    }
    /**
     * login :account error, password error
     * @throws IOException
     * @throws IllegalAccessException
     */
    @Test
    public void Test2() throws IOException, IllegalAccessException {
        String username ="sasasasa";
        String password ="1111111111111111111111111111111111";
        Parent parent = validateLogin(username, password);
        System.out.println(parent);
    }
    /**
     * login:password error
     * @throws IOException
     * @throws IllegalAccessException
     */
    @Test
    public void Test3() throws IOException, IllegalAccessException {
        String username ="123456";
        String password ="1";
        Parent parent = validateLogin(username, password);
        System.out.println(parent);
    }

    private Parent validateLogin(String username, String password) throws IOException, IllegalAccessException {
        // validate of account and password
        // return true means pass the test
        Parent parentName = parentService.getOneByKey("pid", username);
        if (parentName != null && parentName.getPassword().equals(password)) {
            return parentName; // both correct
        } else {
            return null; // either wrong
        }
    }

}
