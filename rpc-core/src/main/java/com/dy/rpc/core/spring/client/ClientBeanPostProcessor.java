package com.dy.rpc.core.spring.client;

import com.dy.rpc.common.extension.ExtensionLoader;
import com.dy.rpc.common.utils.PropertiesFileUtil;
import com.dy.rpc.core.annotation.RPCReference;
import com.dy.rpc.core.annotation.RPCService;
import com.dy.rpc.core.cluster.Cluster;
import com.dy.rpc.core.properties.RpcServiceProperties;
import com.dy.rpc.core.provider.ServiceProvider;
import com.dy.rpc.core.registry.ServiceRegistry;
import com.dy.rpc.core.transport.client.RpcClient;
import com.dy.rpc.core.transport.client.RpcClientProxy;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.util.Properties;

/**
 * 自定义BeanPostProcessor
 *
 * @Author: chengyibai
 * @Date: 2021-04-12 20:22
 */
@Slf4j
@Component
public class ClientBeanPostProcessor implements BeanPostProcessor {
    private final Cluster cluster;

    public ClientBeanPostProcessor() {
        Properties properties = PropertiesFileUtil.readPropertiesFile("rpcConfig.properties");
        String clusterType = properties.getProperty("cluster.type");
        this.cluster = ExtensionLoader.getExtensionLoader(Cluster.class).getExtension(clusterType);
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> targetClass = bean.getClass();
        Field[] declaredFields = targetClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            RPCReference rpcReference = declaredField.getAnnotation(RPCReference.class);
            if (rpcReference != null) {
                RpcServiceProperties rpcServiceProperties = RpcServiceProperties.builder()
                        .group(rpcReference.group()).version(rpcReference.version()).build();
                RpcClientProxy rpcClientProxy = new RpcClientProxy(cluster, rpcServiceProperties);
                Object clientProxy = rpcClientProxy.getProxy(declaredField.getType());
                declaredField.setAccessible(true);
                try {
                    declaredField.set(bean, clientProxy);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

        }
        return bean;
    }
}
