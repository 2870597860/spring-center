package com.xd.think.userentity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitUserTest {
    @Autowired
    private UserProducer userProducer;

    @PostMapping("/usertest")
    public void userTest(){
        userProducer.producer();
    }
}
