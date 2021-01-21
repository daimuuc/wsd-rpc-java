package com.dy.rpc.core.loadbalancer;

import com.alibaba.nacos.api.naming.pojo.Instance;
import com.dy.rpc.core.loadbalancer.impl.PollingLoadBalancer;
import com.dy.rpc.core.loadbalancer.impl.RandomLoadBalancer;

import java.util.List;

/**
 * @Author: chenyibai
 * @Date: 2021/1/21 17:14
 */
public interface CommonLoadBalancer {

    Integer RANDOM_LOAD_BALANCER = 0;
    Integer POLLING_LOAD_BALANCER = 1;

    Integer DEFAULT_SERIALIZER = RANDOM_LOAD_BALANCER;

    static CommonLoadBalancer getByCode(int code) {
        switch (code) {
            case 0:
                return new RandomLoadBalancer();
            case 1:
                return new PollingLoadBalancer();
            default:
                return null;
        }
    }

    Instance select(List<Instance> instances);

    int getCode();

}
