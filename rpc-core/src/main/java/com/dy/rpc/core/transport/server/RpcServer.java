package com.dy.rpc.core.transport.server;

import com.dy.rpc.core.serializer.CommonSerializer;

/**
 * 服务器类通用接口
 *
 * @Author: chenyibai
 * @Date: 2021/1/19 15:40
 */
public interface RpcServer {

    int DEFAULT_SERIALIZER = CommonSerializer.KRYO_SERIALIZER;

    void start();

    <T> void publishService(T service, String serviceName);

}
