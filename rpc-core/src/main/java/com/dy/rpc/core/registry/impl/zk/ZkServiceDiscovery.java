package com.dy.rpc.core.registry.impl.zk;

import com.dy.rpc.common.enums.RpcError;
import com.dy.rpc.common.exception.RpcException;
import com.dy.rpc.common.extension.ExtensionLoader;
import com.dy.rpc.core.loadbalancer.CommonLoadBalancer;
import com.dy.rpc.core.registry.ServiceDiscovery;
import com.dy.rpc.core.registry.impl.nacos.NacosServiceDiscovery;
import com.dy.rpc.core.registry.impl.zk.util.CuratorUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * ZooKeeper官网：https://zookeeper.apache.org/
 * 本项目所用版本：3.6.2
 *
 * @Author: chenyibai
 * @Date: 2021/2/20 15:55
 */
@Slf4j
public class ZkServiceDiscovery implements ServiceDiscovery {

    private static final Logger logger = LoggerFactory.getLogger(ZkServiceDiscovery.class);

    private final CommonLoadBalancer loadBalancer;

    public ZkServiceDiscovery() {
        this.loadBalancer = ExtensionLoader.getExtensionLoader(CommonLoadBalancer.class).getExtension("commonLoadBalancer");
    }

    @Override
    public InetSocketAddress lookupService(String serviceName) {
        CuratorFramework zkClient = CuratorUtils.getZkClient();
        List<String> serviceUrlList = CuratorUtils.getChildrenNodes(zkClient, serviceName);
        if (serviceUrlList == null || serviceUrlList.size() == 0) {
            logger.error("找不到对应的服务: " + serviceName);
            throw new RpcException(RpcError.SERVICE_NOT_FOUND);
        }
        // load balancing
        String targetServiceUrl = loadBalancer.select(serviceUrlList, serviceName);
        log.info("Successfully found the service address:[{}]", targetServiceUrl);
        String[] socketAddressArray = targetServiceUrl.split(":");
        String host = socketAddressArray[0];
        int port = Integer.parseInt(socketAddressArray[1]);
        return new InetSocketAddress(host, port);
    }

    @Override
    public List<String> lookupServiceList(String serviceName) {
        CuratorFramework zkClient = CuratorUtils.getZkClient();
        List<String> serviceUrlList = CuratorUtils.getChildrenNodes(zkClient, serviceName);
        if (serviceUrlList == null || serviceUrlList.size() == 0) {
            logger.error("找不到对应的服务: " + serviceName);
            throw new RpcException(RpcError.SERVICE_NOT_FOUND);
        }

        return serviceUrlList;
    }
}
