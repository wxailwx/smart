package com.smart.smart;

import com.smart.smart.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SmartApplicationTests {
    @Test
    void contextLoads() {
    }
    @Test
    public void testInsert(){
        User user=new User();
        user.setName("王鑫");
        user.setCollege("计算机");
        user.setNumber("2017141461017");
        user.setPassword("12345678");
        System.out.println("成功");
    }

}
