package com.dy.rpc.client;

import com.dy.rpc.api.HelloObject;
import com.dy.rpc.api.HelloService;
import com.dy.rpc.api.Student;
import com.dy.rpc.api.StudentService;
import com.dy.rpc.core.client.RpcClientProxy;
import com.dy.rpc.core.client.impl.SocketClient;
import com.dy.rpc.core.serializer.CommonSerializer;

/**
 * @Author: chenyibai
 * @Date: 2021/1/19 16:04
 */
public class SocketTestClient {

    public static void main(String[] args) {
        SocketClient client = new SocketClient("127.0.0.1", 9999, CommonSerializer.KRYO_SERIALIZER);
        RpcClientProxy proxy = new RpcClientProxy(client);

        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "This is a message");
        String res = helloService.hello(object);
        System.out.println(res);

        StudentService studentService = proxy.getProxy(StudentService.class);
        Student student = new Student("一白", 23, "男");
        studentService.printInfo(student);
    }

}
