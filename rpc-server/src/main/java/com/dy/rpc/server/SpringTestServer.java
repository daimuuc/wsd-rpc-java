package com.dy.rpc.server;

import com.dy.rpc.common.utils.PropertiesFileUtil;
import com.dy.rpc.core.annotation.RPCServiceServerScan;
import com.dy.rpc.core.transport.server.RpcServer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Properties;

/**
 * @Author: chengyibai
 * @Date: 2021-04-12 20:24
 */
// 包扫描。若未指定，则默认扫描该注解标记类所在的包。
@RPCServiceServerScan(basePackage = {"com.dy.rpc.server.service"})
public class SpringTestServer {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringTestServer.class);
        Properties properties = PropertiesFileUtil.readPropertiesFile("rpcConfig.properties");
        String serverType = properties.getProperty("server.type");
        RpcServer server = (RpcServer) applicationContext.getBean(serverType);
        server.start();
    }

}
