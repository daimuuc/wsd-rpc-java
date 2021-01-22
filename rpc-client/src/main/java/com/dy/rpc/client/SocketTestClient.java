package com.dy.rpc.client;

import com.dy.rpc.api.*;
import com.dy.rpc.core.transport.client.RpcClientProxy;
import com.dy.rpc.core.transport.client.socket.SocketClient;
import com.dy.rpc.core.loadbalancer.CommonLoadBalancer;
import com.dy.rpc.core.serializer.CommonSerializer;

/**
 * @Author: chenyibai
 * @Date: 2021/1/19 16:04
 */
public class SocketTestClient {

    public static void main(String[] args) {
        SocketClient client = new SocketClient(CommonSerializer.KRYO_SERIALIZER, CommonLoadBalancer.RANDOM_LOAD_BALANCER);
        RpcClientProxy proxy = new RpcClientProxy(client);

        HelloService helloService = proxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "This is a message");
        String res = helloService.hello(object);
        System.out.println(res);

        StudentService studentService = proxy.getProxy(StudentService.class);
        Student student = new Student("一白", 23, "男");
        studentService.printInfo(student);

        ByeService byeService = proxy.getProxy(ByeService.class);
        System.out.println(byeService.bye("bye, china"));
    }

}
