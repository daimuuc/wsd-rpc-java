package com.dy.rpc.core.transport.server;

import com.dy.rpc.common.enums.RpcError;
import com.dy.rpc.common.exception.RpcException;
import com.dy.rpc.common.utils.ReflectUtil;
import com.dy.rpc.core.annotation.RPCService;
import com.dy.rpc.core.annotation.RPCServiceScan;
import com.dy.rpc.core.provider.ServiceProvider;
import com.dy.rpc.core.registry.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.Set;

/**
 * @Author: chenyibai
 * @Date: 2021/1/20 13:50
 */
public abstract class AbstractRpcServer implements RpcServer {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected String host;
    protected int port;

    protected ServiceRegistry serviceRegistry;
    protected ServiceProvider serviceProvider;

    public void scanServices() {
        String mainClassName = ReflectUtil.getStackTrace();
        Class<?> startClass;
        try {
            startClass = Class.forName(mainClassName);
            if(!startClass.isAnnotationPresent(RPCServiceScan.class)) {
                logger.error("启动类缺少 @ServiceScan 注解");
                throw new RpcException(RpcError.SERVICE_SCAN_PACKAGE_NOT_FOUND);
            }
        } catch (ClassNotFoundException e) {
            logger.error("出现未知错误");
            throw new RpcException(RpcError.UNKNOWN_ERROR);
        }

        String[] packageNameNum = startClass.getAnnotation(RPCServiceScan.class).value();
        if (packageNameNum.length == 0) {
            String basePackage = mainClassName.substring(0, mainClassName.lastIndexOf("."));
            packageNameNum = new String[] {basePackage};
        }
        Set<Class<?>> classSet = ReflectUtil.getAllClasses(packageNameNum);
        for(Class<?> clazz : classSet) {
            if(clazz.isAnnotationPresent(RPCService.class)) {
                String serviceName = clazz.getAnnotation(RPCService.class).value();
                Object obj;
                try {
                    obj = clazz.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    logger.error("创建 " + clazz + " 时有错误发生");
                    continue;
                }
                if("".equals(serviceName)) {
                    Class<?>[] interfaces = clazz.getInterfaces();
                    for (Class<?> oneInterface: interfaces){
                        publishService(obj, oneInterface.getCanonicalName());
                    }
                } else {
                    publishService(obj, serviceName);
                }
            }
        }
    }

    @Override
    public <T> void publishService(T service, String serviceName) {
        serviceProvider.registerService(service, serviceName);
        serviceRegistry.register(serviceName, new InetSocketAddress(host, port));
    }

}
