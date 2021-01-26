package com.dy.rpc.core.loadbalancer.impl;

import com.alibaba.nacos.api.naming.pojo.Instance;
import com.dy.rpc.common.enumeration.LoadBalancerCode;
import com.dy.rpc.core.loadbalancer.AbstractLoadBalancer;
import com.dy.rpc.core.loadbalancer.CommonLoadBalancer;

import java.util.List;

/**
 * 轮询负载均衡
 *
 * @Author: chenyibai
 * @Date: 2021/1/21 17:17
 */
public class PollingLoadBalancer extends AbstractLoadBalancer {

    private int index = 0;

    @Override
    public Instance doSelect(List<Instance> instances, String serviceName) {
        if(index >= instances.size()) {
            index %= instances.size();
        }
        return instances.get(index++);
    }

}
