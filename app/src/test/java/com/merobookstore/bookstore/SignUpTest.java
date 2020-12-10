package com.merobookstore.bookstore;

import com.merobookstore.bookstore.bll.SignUpBLL;

import static org.junit.Assert.*;
import org.junit.Test;

public class SignUpTest {
    @Test
    public void registerTest(){
        SignUpBLL signUpBLL =  new SignUpBLL();
        boolean result = signUpBLL.signupUser("sushant maharjan","ktm","sushantmzjn123","male","1234","imageFile-1580932447543.jpg");
        assertEquals(true,result);
    }
}
