package com.xd.think.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitOneToManyTest {

    @Autowired
    private HelloProducer helloProducer;

    @PostMapping("/hello")
    public void hello(){
        for (int i = 0; i < 10; i++) {
            helloProducer.produce("hello mag："+i);
        }
    }
}
