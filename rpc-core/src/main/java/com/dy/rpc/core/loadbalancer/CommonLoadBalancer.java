package com.dy.rpc.core.loadbalancer;

import com.alibaba.nacos.api.naming.pojo.Instance;
import com.dy.rpc.common.extension.SPI;
import com.dy.rpc.core.loadbalancer.impl.PollingLoadBalancer;
import com.dy.rpc.core.loadbalancer.impl.RandomLoadBalancer;

import java.util.List;

/**
 * @Author: chenyibai
 * @Date: 2021/1/21 17:14
 */
@SPI
public interface CommonLoadBalancer {

    Instance select(List<Instance> instances, String serviceName);

}
