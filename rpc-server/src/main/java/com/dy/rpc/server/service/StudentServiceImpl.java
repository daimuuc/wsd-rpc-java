package com.dy.rpc.server.service;

import com.dy.rpc.api.Student;
import com.dy.rpc.api.StudentService;
import com.dy.rpc.core.annotation.RPCService;

/**
 * @Author: chenyibai
 * @Date: 2021/1/20 11:08
 */
@RPCService(group = "group_1", version = "version_1.0")
public class StudentServiceImpl implements StudentService {

    public Student getInfo() {
        Student person = new Student();
        person.setAge(18);
        person.setName("arrylist");
        person.setSex("女");
        return person;
    }

    public boolean printInfo(Student person) {
        if (person != null) {
            System.out.println(person);
            return true;
        }
        return false;
    }

}
