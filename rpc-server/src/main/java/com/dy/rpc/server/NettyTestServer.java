package com.dy.rpc.server;

import com.dy.rpc.core.annotation.RPCServiceScan;
import com.dy.rpc.core.transport.server.RpcServer;
import com.dy.rpc.core.transport.server.netty.NettyServer;

/**
 * 测试用Netty服务提供者（服务端）
 *
 * @Author: chenyibai
 * @Date: 2021/1/20 16:56
 */
@RPCServiceScan
public class NettyTestServer {

    public static void main(String[] args) {
        RpcServer server = new NettyServer("127.0.0.1", 9999);
        server.start();
    }

}
