package com.dy.rpc.core.client;

import com.dy.rpc.common.entity.RpcRequest;
import com.dy.rpc.core.serializer.CommonSerializer;

/**
 * 客户端类通用接口
 *
 * @Author: chenyibai
 * @Date: 2021/1/19 15:34
 */
public interface RpcClient {

    Object sendRequest(RpcRequest rpcRequest);

    void setSerializer(CommonSerializer serializer);

}
