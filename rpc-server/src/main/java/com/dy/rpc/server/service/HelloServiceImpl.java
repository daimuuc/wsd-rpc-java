package com.dy.rpc.server.service;

import com.dy.rpc.api.HelloObject;
import com.dy.rpc.api.HelloService;
import com.dy.rpc.core.annotation.RPCService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: chenyibai
 * @Date: 2021/1/19 16:17
 */
@RPCService(group = "group_1", version = "version_1.0")
public class HelloServiceImpl implements HelloService {

    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);

    @Override
    public String hello(HelloObject object) {
        logger.info("接收到：{}", object.getMessage());
        return "这是调用的返回值，id=" + object.getId();
    }

}
