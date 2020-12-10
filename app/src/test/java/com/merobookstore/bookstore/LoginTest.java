package com.merobookstore.bookstore;

import com.merobookstore.bookstore.bll.LoginBll;

import org.junit.Test;
import static org.junit.Assert.*;

public class LoginTest {
    @Test
    public void LoginTest() {

        LoginBll loginBll = new LoginBll();
        boolean result = loginBll.checkUser("sushant","1234");
        assertEquals(true,result);
    }
}
