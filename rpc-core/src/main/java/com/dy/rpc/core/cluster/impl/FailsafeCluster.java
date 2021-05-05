package com.dy.rpc.core.cluster.impl;

import com.dy.rpc.common.entity.RpcRequest;
import com.dy.rpc.common.exception.RpcException;
import com.dy.rpc.core.cluster.AbstractCluster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * Failsafe Cluster
 * 失败安全，出现异常时，直接忽略。
 * 通常用于写入审计日志等操作
 *
 * @Author: chengyibai
 * @Date: 2021-05-05 21:29
 */
public class FailsafeCluster extends AbstractCluster {
    private static final Logger logger = LoggerFactory.getLogger(FailsafeCluster.class);

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
            return null;
        }

    }
}
