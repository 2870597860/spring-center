package com.xd.think.hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class HelloReceiver0 {
    private static final Logger log = LoggerFactory.getLogger(HelloReceiver0.class);

    @RabbitListener(queues = "hello")
    public void process(String hello){
       log.info("Receiver0:"+hello);
    }

}
