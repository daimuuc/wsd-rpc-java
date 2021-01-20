package com.dy.rpc.server;

import com.dy.rpc.api.HelloService;
import com.dy.rpc.api.StudentService;
import com.dy.rpc.core.provider.ServiceProvider;
import com.dy.rpc.core.provider.impl.DefaultServiceProvider;
import com.dy.rpc.core.serializer.impl.HessianSerializer;
import com.dy.rpc.core.serializer.impl.JsonSerializer;
import com.dy.rpc.core.serializer.impl.KryoSerializer;
import com.dy.rpc.core.server.impl.SocketServer;
import com.dy.rpc.server.service.HelloServiceImpl;
import com.dy.rpc.server.service.StudentServiceImpl;

/**
 * 测试用服务提供方（服务端）
 *
 * @Author: chenyibai
 * @Date: 2021/1/19 16:16
 */
public class SocketTestServer {

    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        StudentService studentService = new StudentServiceImpl();
        ServiceProvider serviceProvider = new DefaultServiceProvider();
        serviceProvider.register(helloService);
        serviceProvider.register(studentService);
        SocketServer socketServer = new SocketServer(serviceProvider);
//        socketServer.setSerializer(new KryoSerializer());
//        socketServer.setSerializer(new HessianSerializer());
        socketServer.setSerializer(new JsonSerializer());
        socketServer.start(9999);
    }

}
