package com.dy.rpc.core.loadbalancer.impl;

import com.dy.rpc.core.loadbalancer.AbstractLoadBalancer;

import java.util.List;
import java.util.Random;

/**
 * 随机负载均衡
 *
 * @Author: chenyibai
 * @Date: 2021/1/21 17:16
 */
public class RandomLoadBalancer extends AbstractLoadBalancer {

    @Override
    public String doSelect(List<String> serviceAddresses, String serviceName) {
        return serviceAddresses.get(new Random().nextInt(serviceAddresses.size()));
    }

}
