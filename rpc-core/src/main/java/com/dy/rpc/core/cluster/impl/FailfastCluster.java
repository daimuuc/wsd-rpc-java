package com.dy.rpc.core.cluster.impl;

import com.dy.rpc.common.entity.RpcRequest;
import com.dy.rpc.common.exception.RpcException;
import com.dy.rpc.core.cluster.AbstractCluster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * Failfast Cluster
 * 快速失败,只会发起一次调用,失败立即报错。
 * 通常用于非幂等性的写操作，比如：新增记录
 *
 * @Author: chengyibai
 * @Date: 2021-05-05 21:18
 */
public class FailfastCluster extends AbstractCluster {
    private static final Logger logger = LoggerFactory.getLogger(FailfastCluster.class);

    @Override
    protected Object doInvoke(List<String> serviceAddresses, RpcRequest rpcRequest) {
        try {
            // 服务名称
            String serviceName = rpcRequest.getInterfaceName();
            //通过负载均衡选择一个服务提供者
            String targetServiceUrl = loadBalancer.select(serviceAddresses, serviceName);
            String[] socketAddressArray = targetServiceUrl.split(":");
            String host = socketAddressArray[0];
            int port = Integer.parseInt(socketAddressArray[1]);
            InetSocketAddress inetSocketAddress = new InetSocketAddress(host, port);
            Object result = rpcClient.sendRequest(inetSocketAddress, rpcRequest);
            return result;
        } catch (Exception e) {
            logger.error("调用时有错误发生：", e);
            throw new RpcException("服务调用失败: ", e);
        }

    }
}
