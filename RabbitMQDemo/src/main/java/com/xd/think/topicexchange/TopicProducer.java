package com.xd.think.topicexchange;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TopicProducer {
    private static final Logger log = LoggerFactory.getLogger(TopicProducer.class);
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(){
        String msg1 = "i am topic.message msg1!";
        log.info("send1:"+ msg1);
        amqpTemplate.convertAndSend("exchange","topic.message",msg1);

        String msg2 = " i am topic.message msg2!";
        log.info("sender2:" + msg2);
        amqpTemplate.convertAndSend("exchange","topic.messages",msg2);

    }

}
