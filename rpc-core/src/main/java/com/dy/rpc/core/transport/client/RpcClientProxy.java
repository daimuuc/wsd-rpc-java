package com.dy.rpc.core.transport.client;

import com.dy.rpc.common.entity.RpcRequest;
import com.dy.rpc.common.entity.RpcResponse;
import com.dy.rpc.common.exception.RpcException;
import com.dy.rpc.common.utils.RpcMessageChecker;
import com.dy.rpc.core.cluster.Cluster;
import com.dy.rpc.core.properties.RpcServiceProperties;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * RPC客户端动态代理
 *
 * @Author: chenyibai
 * @Date: 2021/1/19 15:39
 */
@Slf4j
public class RpcClientProxy implements InvocationHandler {

    private static final Logger logger = LoggerFactory.getLogger(RpcClientProxy.class);

    private final Cluster cluster;
    private final RpcServiceProperties rpcServiceProperties;

    public RpcClientProxy(Cluster cluster, RpcServiceProperties rpcServiceProperties) {
        this.cluster = cluster;
        this.rpcServiceProperties = rpcServiceProperties;
    }

    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        rpcServiceProperties.setServiceName(method.getDeclaringClass().getName());
        String serviceName = rpcServiceProperties.toString();
        logger.info("调用方法: {}#{}", serviceName, method.getName());
        RpcRequest rpcRequest = new RpcRequest(UUID.randomUUID().toString(), serviceName,
                method.getName(), args, method.getParameterTypes(), false);

        RpcResponse rpcResponse = null;
        try {
            Object object = cluster.invoke(rpcRequest);
            if (object instanceof CompletableFuture) {
                CompletableFuture<RpcResponse> completableFuture = (CompletableFuture<RpcResponse>) object;
                rpcResponse = completableFuture.get();
            }

            if (object instanceof RpcResponse) {
                rpcResponse = (RpcResponse) object;
            }
        } catch (Exception e) {
            logger.error("方法调用请求发送失败", e);
            throw new RpcException("服务调用失败: ", e);
        }
        RpcMessageChecker.check(rpcRequest, rpcResponse);

        return rpcResponse.getData();
    }

}
