package com.xd.think.userentity;

import com.xd.think.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class UserProducer {
    private static final Logger log = LoggerFactory.getLogger(UserProducer.class);

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void producer(){
        User user = new User();
        user.setName("xiaodai");
        user.setPass("woshi");
        log.info("user producer:" + user.toString());
        amqpTemplate.convertAndSend("user",user);
    }
}
