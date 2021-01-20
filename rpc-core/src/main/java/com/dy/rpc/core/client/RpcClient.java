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

    int DEFAULT_SERIALIZER = CommonSerializer.KRYO_SERIALIZER;

    Object sendRequest(RpcRequest rpcRequest);

}
