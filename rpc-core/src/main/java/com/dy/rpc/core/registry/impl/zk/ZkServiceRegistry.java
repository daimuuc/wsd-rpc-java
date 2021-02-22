package com.dy.rpc.core.registry.impl.zk;

import com.dy.rpc.core.registry.ServiceRegistry;
import com.dy.rpc.core.registry.impl.nacos.NacosServiceRegistry;
import com.dy.rpc.core.registry.impl.zk.util.CuratorUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * ZooKeeper服务注册中心
 *
 * @Author: chenyibai
 * @Date: 2021/2/20 15:40
 */
@Slf4j
public class ZkServiceRegistry implements ServiceRegistry {
    private static final Logger logger = LoggerFactory.getLogger(NacosServiceRegistry.class);

    @Override
    public void register(String serviceName, InetSocketAddress inetSocketAddress) {
        CuratorFramework zkClient = CuratorUtils.getZkClient();
        CuratorUtils.createPersistentNode(zkClient, serviceName, inetSocketAddress);
    }

}
