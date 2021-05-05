package com.dy.rpc.core.cluster;

import com.dy.rpc.common.entity.RpcRequest;
import com.dy.rpc.common.extension.SPI;

/**
 * 容错机制.
 *
 * @Author: chengyibai
 * @Date: 2021-05-02 20:41
 */
@SPI
public interface Cluster {
    Object invoke(RpcRequest rpcRequest);
}

