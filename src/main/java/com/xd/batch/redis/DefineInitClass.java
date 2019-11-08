package com.xd.batch.redis;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;

import javax.annotation.PostConstruct;


public class DefineInitClass implements CommandLineRunner, ApplicationRunner, InitializingBean {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("commandLineRunner");
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("ApplicationRunner");
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean");
    }

    @PostConstruct
    public void postConstructInit(){
        System.out.println("postConstruct");
    }

}
