package com.dy.rpc.core.loadbalancer.impl;

import com.alibaba.nacos.api.naming.pojo.Instance;
import com.dy.rpc.common.enumeration.LoadBalancerCode;
import com.dy.rpc.common.enumeration.SerializerCode;
import com.dy.rpc.core.loadbalancer.CommonLoadBalancer;

import java.util.List;
import java.util.Random;

/**
 * 随机负载均衡
 *
 * @Author: chenyibai
 * @Date: 2021/1/21 17:16
 */
public class RandomLoadBalancer implements CommonLoadBalancer {

    @Override
    public Instance select(List<Instance> instances) {
        return instances.get(new Random().nextInt(instances.size()));
    }

    @Override
    public int getCode() {
        return LoadBalancerCode.valueOf("RANDOM").getCode();
    }

}
