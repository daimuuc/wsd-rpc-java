package com.dy.rpc.core.spring.server;

import com.dy.rpc.common.extension.ExtensionLoader;
import com.dy.rpc.core.annotation.RPCService;
import com.dy.rpc.core.properties.RpcServiceProperties;
import com.dy.rpc.core.provider.ServiceProvider;
import com.dy.rpc.core.registry.ServiceRegistry;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import java.net.InetSocketAddress;

/**
 * 自定义BeanPostProcessor
 *
 * @Author: chengyibai
 * @Date: 2021-04-12 20:22
 */
@Slf4j
@Component
public class SpringServerBeanPostProcessor implements BeanPostProcessor {
    protected String host = "127.0.0.1";
    protected int port = 9999;

    private final ServiceProvider serviceProvider;
    private final ServiceRegistry serviceRegistry;

    public SpringServerBeanPostProcessor() {
        this.serviceProvider = ExtensionLoader.getExtensionLoader(ServiceProvider.class).getExtension("serviceProvider");
        this.serviceRegistry = ExtensionLoader.getExtensionLoader(ServiceRegistry.class).getExtension("serviceRegistry");
    }

    @SneakyThrows
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        if (clazz.isAnnotationPresent(RPCService.class)) {
            log.info("[{}] is annotated with  [{}]", clazz.getName(), RPCService.class.getCanonicalName());
            // 获取RPCService注解
            RPCService rpcService = bean.getClass().getAnnotation(RPCService.class);
            // 获取所有接口
            Class<?>[] interfaces = clazz.getInterfaces();
            for (Class<?> oneInterface: interfaces){
                RpcServiceProperties rpcServiceProperties = RpcServiceProperties.builder()
                        .serviceName(oneInterface.getCanonicalName()).group(rpcService.group())
                        .version(rpcService.version()).build();
                String serviceName = rpcServiceProperties.toString();
                serviceProvider.registerService(bean, serviceName);
                serviceRegistry.register(serviceName, new InetSocketAddress(host, port));
            }
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
