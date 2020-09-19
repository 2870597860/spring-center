package com.xd.think.fanout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FanoutProducer {
    private static final Logger log = LoggerFactory.getLogger(FanoutProducer.class);

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(){
        String msg = "fanoutProducer : hello i am fanout";
        log.info(msg);
        amqpTemplate.convertAndSend("fanoutExchange","sbdh.ee",msg);
    }
}

