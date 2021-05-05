package com.dy.rpc.core.cluster.impl;

import com.dy.rpc.common.entity.RpcRequest;
import com.dy.rpc.common.exception.RpcException;
import com.dy.rpc.core.cluster.AbstractCluster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * Failover Cluster
 * 失败自动切换，当出现失败，重试其他服务器(缺省)；
 * 通常用于读操作，但是重试会带来更长延迟。
 *
 * @Author: chengyibai
 * @Date: 2021-05-04 18:33
 */
public class FailoverCluster extends AbstractCluster {
    private static final Logger logger = LoggerFactory.getLogger(FailoverCluster.class);

    @Override
    protected Object doInvoke(List<String> serviceAddresses, RpcRequest rpcRequest) {
        // 未调用服务地址
        List<String> unusedAddresses = new ArrayList<>(serviceAddresses);
        int size = unusedAddresses.size();
        retry = Math.min(size, retry);
        // 最后一次调用异常
        RpcException lastExc = null;
        // 服务名称
        String serviceName = rpcRequest.getInterfaceName();
        for (int i = 0; i < retry; i++) {
            try {
                logger.info("第 {} 次尝试调用服务: {}", i + 1, serviceName);
                String targetServiceUrl = loadBalancer.select(unusedAddresses, serviceName);
                unusedAddresses.remove(targetServiceUrl);
                String[] socketAddressArray = targetServiceUrl.split(":");
                String host = socketAddressArray[0];
                int port = Integer.parseInt(socketAddressArray[1]);
                InetSocketAddress inetSocketAddress = new InetSocketAddress(host, port);
                Object result = rpcClient.sendRequest(inetSocketAddress, rpcRequest);
                return result;
            } catch (Exception e) {
                logger.error("调用时有错误发生：", e);
                lastExc = new RpcException("服务调用失败: ", e);
            }
        }


        throw lastExc;
    }
}
