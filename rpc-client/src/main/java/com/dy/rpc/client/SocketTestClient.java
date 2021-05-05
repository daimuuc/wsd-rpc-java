package com.dy.rpc.client;

import com.dy.rpc.api.*;
import com.dy.rpc.core.cluster.Cluster;
import com.dy.rpc.core.cluster.impl.FailoverCluster;
import com.dy.rpc.core.properties.RpcServiceProperties;
import com.dy.rpc.core.transport.client.RpcClientProxy;

/**
 * @Author: chenyibai
 * @Date: 2021/1/19 16:04
 */
public class SocketTestClient {

    public static void main(String[] args) {
        Cluster cluster = new FailoverCluster();
        RpcServiceProperties rpcServiceProperties = RpcServiceProperties.builder()
                .group("group_1").version("version_1.0").build();
        RpcClientProxy proxy = new RpcClientProxy(cluster, rpcServiceProperties);

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