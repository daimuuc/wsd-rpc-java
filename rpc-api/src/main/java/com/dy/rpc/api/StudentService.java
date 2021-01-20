package com.dy.rpc.api;

/**
 * @Author: chenyibai
 * @Date: 2021/1/20 11:05
 */
public interface StudentService {

    Student getInfo();
    boolean printInfo(Student student);

}
