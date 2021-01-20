package com.dy.rpc.core.server;

import com.dy.rpc.core.serializer.CommonSerializer;

/**
 * 服务器类通用接口
 *
 * @Author: chenyibai
 * @Date: 2021/1/19 15:40
 */
public interface RpcServer {

    void start(int port);

    void setSerializer(CommonSerializer serializer);

}
