package com.dy.rpc.core.registry;

import com.dy.rpc.common.extension.SPI;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * 服务发现接口
 *
 * @Author: chenyibai
 * @Date: 2021/1/21 17:31
 */
@SPI
public interface ServiceDiscovery {

    /**
     * 根据服务名称查找服务实体
     *
     * @param serviceName 服务名称
     * @return 服务实体
     */
    InetSocketAddress lookupService(String serviceName);

    /**
     * 根据服务名称查找服务实体列表
     *
     * @param serviceName 服务名称
     * @return 服务实体
     */
    List<String> lookupServiceList(String serviceName);

}
