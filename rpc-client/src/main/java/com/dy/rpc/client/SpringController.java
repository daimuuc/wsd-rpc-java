package com.dy.rpc.client;

import com.dy.rpc.api.*;
import com.dy.rpc.core.annotation.RPCReference;
import org.springframework.stereotype.Component;

/**
 * @Author: chengyibai
 * @Date: 2021-04-13 18:44
 */
@Component
public class SpringController {
    @RPCReference(version = "version_1.0", group = "group_2")
    private HelloService helloService;
    @RPCReference(version = "version_1.0", group = "group_2")
    private StudentService studentService;
    @RPCReference(version = "version_1.0", group = "group_2")
    private ByeService byeService;


    public void test() {
        HelloObject object = new HelloObject(12, "This is a message");
        String res = helloService.hello(object);
        System.out.println(res);

        Student student = new Student("一白", 23, "男");
        studentService.printInfo(student);

        System.out.println(byeService.bye("bye, china"));
    }

}
