package com.xd.think.userentity;

import com.xd.think.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class UserReceiver {
    private static final Logger log = LoggerFactory.getLogger(UserReceiver.class);

    @Autowired
    private AmqpTemplate amqpTemplate;

    @RabbitHandler
    @RabbitListener(queues = "user")
    public void receive(User user){
        log.info("user receive:" + user.toString());
    }
}
