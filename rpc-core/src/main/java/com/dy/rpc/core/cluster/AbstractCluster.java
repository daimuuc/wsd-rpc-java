package com.dy.rpc.core.cluster;

import com.dy.rpc.common.entity.RpcRequest;
import com.dy.rpc.common.extension.ExtensionLoader;
import com.dy.rpc.common.utils.PropertiesFileUtil;
import com.dy.rpc.core.loadbalancer.CommonLoadBalancer;
import com.dy.rpc.core.registry.ServiceDiscovery;
import com.dy.rpc.core.transport.client.RpcClient;

import java.util.List;
import java.util.Properties;

/**
 * @Author: chengyibai
 * @Date: 2021-05-04 18:40
 */
public abstract class AbstractCluster implements Cluster{
    private final ServiceDiscovery serviceDiscovery;
    protected final CommonLoadBalancer loadBalancer;
    protected final RpcClient rpcClient;
    protected int retry;

    protected AbstractCluster() {
        serviceDiscovery = ExtensionLoader.getExtensionLoader(ServiceDiscovery.class).getExtension("serviceDiscovery");
        loadBalancer = ExtensionLoader.getExtensionLoader(CommonLoadBalancer.class).getExtension("commonLoadBalancer");
        Properties properties = PropertiesFileUtil.readPropertiesFile("rpcConfig.properties");
        String clientType = properties.getProperty("client.type");
        rpcClient = ExtensionLoader.getExtensionLoader(RpcClient.class).getExtension(clientType);
        String retryStr = properties.getProperty("cluster.retry");
        retry = Integer.parseInt(retryStr);
    }

    @Override
    public Object invoke(RpcRequest rpcRequest) {
        List<String> serviceAddresses = serviceDiscovery.lookupServiceList(rpcRequest.getInterfaceName());
        return doInvoke(serviceAddresses, rpcRequest);
    }

    protected abstract Object doInvoke(List<String> serviceAddresses, RpcRequest rpcRequest);
}
