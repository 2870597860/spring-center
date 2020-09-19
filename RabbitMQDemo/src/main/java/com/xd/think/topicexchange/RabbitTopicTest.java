package com.xd.think.topicexchange;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitTopicTest {
    @Autowired
    private TopicProducer topicProducer;

    @PostMapping("/topicTest")
    public void topicTest(){
        topicProducer.send();
    }
}
