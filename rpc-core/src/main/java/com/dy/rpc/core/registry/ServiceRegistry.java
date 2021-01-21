package com.dy.rpc.core.registry;

import java.net.InetSocketAddress;

/**
 * 服务注册接口
 *
 * @Author: chenyibai
 * @Date: 2021/1/21 17:32
 */
public interface ServiceRegistry {

    /**
     * 将一个服务注册进注册表
     *
     * @param serviceName 服务名称
     * @param inetSocketAddress 提供服务的地址
     */
    void register(String serviceName, InetSocketAddress inetSocketAddress);

}
