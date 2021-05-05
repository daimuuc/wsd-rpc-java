package com.dy.rpc.core.registry.impl.nacos;

import com.alibaba.nacos.api.exception.NacosException;
import com.dy.rpc.common.enums.RpcError;
import com.dy.rpc.common.exception.RpcException;
import com.dy.rpc.common.extension.ExtensionLoader;
import com.dy.rpc.common.utils.NacosUtil;
import com.dy.rpc.core.loadbalancer.CommonLoadBalancer;
import com.dy.rpc.core.registry.ServiceDiscovery;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * Nacos官网：https://github.com/alibaba/nacos
 * 本项目所用版本：1.4.1
 *
 * @Author: chenyibai
 * @Date: 2021/1/21 17:35
 */
@Slf4j
public class NacosServiceDiscovery implements ServiceDiscovery {

    private static final Logger logger = LoggerFactory.getLogger(NacosServiceDiscovery.class);

    private final CommonLoadBalancer loadBalancer;

    public NacosServiceDiscovery() {
        this.loadBalancer = ExtensionLoader.getExtensionLoader(CommonLoadBalancer.class).getExtension("commonLoadBalancer");
    }

    @Override
    public InetSocketAddress lookupService(String serviceName) {
        try {
            List<String> serviceAddresses = NacosUtil.getAllInstanceStr(serviceName);
            if(serviceAddresses.size() == 0) {
                logger.error("找不到对应的服务: " + serviceName);
                throw new RpcException(RpcError.SERVICE_NOT_FOUND);
            }
            String targetServiceUrl = loadBalancer.select(serviceAddresses, serviceName);
            String[] socketAddressArray = targetServiceUrl.split(":");
            String host = socketAddressArray[0];
            int port = Integer.parseInt(socketAddressArray[1]);
            return new InetSocketAddress(host, port);
        } catch (NacosException e) {
            logger.error("获取服务时有错误发生:", e);
        }

        return null;
    }

    @Override
    public List<String> lookupServiceList(String serviceName) {
        try {
            List<String> serviceAddresses = NacosUtil.getAllInstanceStr(serviceName);
            if(serviceAddresses.size() == 0) {
                logger.error("找不到对应的服务: " + serviceName);
                throw new RpcException(RpcError.SERVICE_NOT_FOUND);
            }

            return serviceAddresses;
        } catch (NacosException e) {
            logger.error("获取服务时有错误发生:", e);
        }

        return null;
    }
}
