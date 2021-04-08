package com.dy.rpc.server.service;

import com.dy.rpc.api.HelloObject;
import com.dy.rpc.api.HelloService;
import com.dy.rpc.core.annotation.RPCService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: chengyibai
 * @Date: 2021-04-08 20:21
 */
@RPCService(group = "group_2", version = "version_1.0")
public class HelloServiceImpl2 implements HelloService {

    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);

    @Override
    public String hello(HelloObject object) {
        logger.info("接收到：{}", object.getMessage());
        return "这是2.0版本调用的返回值，id=" + object.getId();
    }

}
