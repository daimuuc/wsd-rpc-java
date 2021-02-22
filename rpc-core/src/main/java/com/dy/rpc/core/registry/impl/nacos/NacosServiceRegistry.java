package com.dy.rpc.core.registry.impl.nacos;

import com.alibaba.nacos.api.exception.NacosException;
import com.dy.rpc.common.enums.RpcError;
import com.dy.rpc.common.exception.RpcException;
import com.dy.rpc.common.utils.NacosUtil;
import com.dy.rpc.core.registry.ServiceRegistry;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * Nacos服务注册中心
 *
 * @Author: chenyibai
 * @Date: 2021/1/21 18:59
 */
@Slf4j
public class NacosServiceRegistry implements ServiceRegistry {

    private static final Logger logger = LoggerFactory.getLogger(NacosServiceRegistry.class);

    @Override
    public void register(String serviceName, InetSocketAddress inetSocketAddress) {
        try {
            NacosUtil.registerService(serviceName, inetSocketAddress);
        } catch (NacosException e) {
            logger.error("注册服务时有错误发生:", e);
            throw new RpcException(RpcError.REGISTER_SERVICE_FAILED);
        }
    }

}
