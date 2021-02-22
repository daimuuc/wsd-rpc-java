package com.dy.rpc.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: chenyibai
 * @Date: 2021/2/20 15:53
 */
@AllArgsConstructor
@Getter
public enum RpcConfigEnum {

    RPC_CONFIG_PATH("rpc.properties"),
    ZK_ADDRESS("rpc.zookeeper.address");

    private final String propertyValue;

}
