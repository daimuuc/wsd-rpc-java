package com.dy.rpc.core.transport.client;

import com.dy.rpc.common.entity.RpcRequest;
import com.dy.rpc.common.extension.SPI;
import com.dy.rpc.core.loadbalancer.CommonLoadBalancer;
import com.dy.rpc.core.serializer.CommonSerializer;

/**
 * 客户端类通用接口
 *
 * @Author: chenyibai
 * @Date: 2021/1/19 15:34
 */
@SPI
public interface RpcClient {

    Object sendRequest(RpcRequest rpcRequest);

}
