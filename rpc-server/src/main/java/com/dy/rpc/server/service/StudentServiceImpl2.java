package com.dy.rpc.server.service;

import com.dy.rpc.api.Student;
import com.dy.rpc.api.StudentService;
import com.dy.rpc.core.annotation.RPCService;

/**
 * @Author: chengyibai
 * @Date: 2021-04-08 20:22
 */
@RPCService(group = "group_2", version = "version_1.0")
public class StudentServiceImpl2 implements StudentService {

    public Student getInfo() {
        Student person = new Student();
        person.setAge(28);
        person.setName("峻霄");
        person.setSex("男");
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
