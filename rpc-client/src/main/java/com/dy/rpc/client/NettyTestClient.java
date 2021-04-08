package com.dy.rpc.client;

import com.dy.rpc.api.*;
import com.dy.rpc.core.properties.RpcServiceProperties;
import com.dy.rpc.core.transport.client.RpcClient;
import com.dy.rpc.core.transport.client.RpcClientProxy;
import com.dy.rpc.core.transport.client.netty.NettyClient;

/**
 * @Author: chenyibai
 * @Date: 2021/1/20 16:25
 */
public class NettyTestClient {

    public static void main(String[] args) {
        RpcClient client = new NettyClient();
        RpcServiceProperties rpcServiceProperties = RpcServiceProperties.builder()
                .group("group_2").version("version_1.0").build();
        RpcClientProxy proxy = new RpcClientProxy(client, rpcServiceProperties);

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
