package com.xd.batch.service;

import com.xd.batch.entity.Student;
import com.xd.batch.entity.User;
import com.xd.batch.mapper.splitPackage.test1.UserMapper;
import com.xd.batch.mapper.splitPackage.test2.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StudentMapper studentMapper;

    @Transactional(rollbackFor = Exception.class,transactionManager = "test1DataTranManager")
    public String doUserProcess() throws Exception {
        User user = new User();
        user.setUserName("xiaodai");
        user.setPassword("66666");
        user.setAge(15);
        userMapper.insert(user);
        int i =1;
       /* if(i ==1 ){
            throw new Exception();
        }*/
        return "user-ok";



    }
    @Transactional(rollbackFor = Exception.class,transactionManager = "test2DataTranManager")
    public String doStudentProcess() {
        Student student = new Student();
        student.setName("xiaodai");
        student.setClassNum("1-6");
        student.setAge(15);
        studentMapper.insert(student);
        return "student-ok";
    }
}
