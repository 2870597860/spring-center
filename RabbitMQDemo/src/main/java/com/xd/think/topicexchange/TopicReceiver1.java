package com.xd.think.topicexchange;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "topic.message")
public class TopicReceiver1 {

    private static final Logger log = LoggerFactory.getLogger(TopicReceiver1.class);

    @RabbitHandler
    public void process(String msg ){
        log.info("TopicReceiver1：" + msg);
    }
}
