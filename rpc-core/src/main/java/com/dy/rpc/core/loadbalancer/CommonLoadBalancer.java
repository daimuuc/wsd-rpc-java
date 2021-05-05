package com.dy.rpc.core.loadbalancer;

import com.dy.rpc.common.extension.SPI;

import java.util.List;

/**
 * @Author: chenyibai
 * @Date: 2021/1/21 17:14
 */
@SPI
public interface CommonLoadBalancer {

    String select(List<String> serviceAddresses, String serviceName);

}
