package com.dy.rpc.core.loadbalancer;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;

/**
 * @Author: chenyibai
 * @Date: 2021/1/26 18:05
 */
public abstract class AbstractLoadBalancer implements CommonLoadBalancer{

    @Override
    public Instance select(List<Instance> instances, String serviceName) {
        if (instances == null || instances.size() == 0) {
            return null;
        }
        if (instances.size() == 1) {
            return instances.get(0);
        }
        return doSelect(instances, serviceName);
    }

    protected abstract Instance doSelect(List<Instance> instances, String serviceName);

}
