package com.dy.rpc.core.registry.impl;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.dy.rpc.common.enumeration.RpcError;
import com.dy.rpc.common.exception.RpcException;
import com.dy.rpc.common.extension.ExtensionLoader;
import com.dy.rpc.common.util.NacosUtil;
import com.dy.rpc.core.loadbalancer.CommonLoadBalancer;
import com.dy.rpc.core.registry.ServiceDiscovery;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
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
            List<Instance> instances = NacosUtil.getAllInstance(serviceName);
            if(instances.size() == 0) {
                logger.error("找不到对应的服务: " + serviceName);
                throw new RpcException(RpcError.SERVICE_NOT_FOUND);
            }
            Instance instance = loadBalancer.select(instances, serviceName);
            return new InetSocketAddress(instance.getIp(), instance.getPort());
        } catch (NacosException e) {
            logger.error("获取服务时有错误发生:", e);
        }

        return null;
    }

}
