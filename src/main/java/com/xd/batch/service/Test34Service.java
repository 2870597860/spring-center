package com.xd.batch.service;

import com.xd.batch.entity.Person;
import com.xd.batch.mapper.aop.test4.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class Test34Service {
    @Autowired
    private PersonMapper personMapper;

    @Transactional(rollbackFor = Exception.class,transactionManager = "test4DataTranManager")
    public String doPersonProcess() throws Exception {
        Person person = new Person();
        person.setName("xiaodai");
        person.setClassNum("4-6");
        person.setAge(15);
        personMapper.insert(person);
       /* int i = 1;
        if(i ==1 ){
            throw new Exception();
        }*/
        return "person-ok";
    }

}
