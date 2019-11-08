package com.xd.batch.service;

import com.xd.batch.entity.Student;
import com.xd.batch.mapper.aop.test4.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class Test34Service {
    @Autowired
    private PersonMapper personMapper;

    @Transactional(rollbackFor = Exception.class,transactionManager = "test3DataTranManager")
    public String doStudentProcess() throws Exception {
        Student student = new Student();
        student.setName("xiaodai");
        student.setClassNum("4-6");
        student.setAge(15);
        personMapper.insert(student);
        int i = 1;
        if(i ==1 ){
            throw new Exception();
        }
        return "student-ok";
    }

}
