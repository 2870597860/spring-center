package com.xd.think.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitManyToManyTest {

    @Autowired
    private HelloProducer helloProducer;
    @Autowired
    private HelloProducer0 helloProducer0;

    @PostMapping("/hello")
    public void hello(){
        for (int i = 0; i < 10; i++) {
            helloProducer.produce("hello mag："+i);
            helloProducer0.produce("hello0 mag："+i);
        }
    }
}
