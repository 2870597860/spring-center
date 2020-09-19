package com.xd.think.hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class HelloProducer0 {
    private static final Logger log = LoggerFactory.getLogger(HelloProducer0.class);

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void produce(String message){
        String content = message + " " + new Date();
        log.info("Producer0:" + content);
        amqpTemplate.convertAndSend("hello",content);
    }

}
