package com.xd.think.fanout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitFanoutTest {
    @Autowired
    private FanoutProducer fanoutProducer;

    @PostMapping("/fanouttest")
    public void fanoutTest(){
        fanoutProducer.send();
    }
}
