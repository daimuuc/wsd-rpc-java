package com.dy.rpc.server;

import com.dy.rpc.core.annotation.ServiceScan;
import com.dy.rpc.core.transport.server.RpcServer;
import com.dy.rpc.core.transport.server.socket.SocketServer;

/**
 * 测试用Socket服务提供方（服务端）
 *
 * @Author: chenyibai
 * @Date: 2021/1/19 16:16
 */
// 包扫描。若未指定，则默认扫描该注解标记类所在的包。
@ServiceScan({"com.dy.rpc.server.service"})
public class SocketTestServer {

    public static void main(String[] args) {
        RpcServer server = new SocketServer("127.0.0.1", 9999);
        server.start();
    }

}
