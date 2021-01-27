package com.dy.rpc.core.loadbalancer;

import com.alibaba.nacos.api.naming.pojo.Instance;

import java.util.List;

/**
 * @Author: chenyibai
 * @Date: 2021/1/26 18:05
 */
public abstract class AbstractLoadBalancer implements CommonLoadBalancer{

    @Override
    public String select(List<String> serviceAddresses, String serviceName) {
        if (serviceAddresses == null || serviceAddresses.size() == 0) {
            return null;
        }
        if (serviceAddresses.size() == 1) {
            return serviceAddresses.get(0);
        }
        return doSelect(serviceAddresses, serviceName);
    }

    protected abstract String doSelect(List<String> serviceAddresses, String serviceName);

}
