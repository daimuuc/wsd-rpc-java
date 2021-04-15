package com.dy.rpc.core.spring.client;

import com.dy.rpc.core.annotation.RPCServiceClientScan;
import com.dy.rpc.core.spring.SpringScanner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.StandardAnnotationMetadata;
import org.springframework.stereotype.Component;

/**
 * 扫描过滤特定的注解, 用于服务端.
 * https://juejin.cn/post/6844903942808141832
 *
 * ImportBeanDefinitionRegistrar介绍:
 *  https://blog.csdn.net/elim168/article/details/88131712
 * ResourceLoaderAware介绍：
 *  https://juejin.cn/post/6844903977637658637
 * @Author: chengyibai
 * @Date: 2021-04-12 20:20
 */
@Slf4j
public class ClientScannerRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {
    private static final String SPRING_BEAN_BASE_PACKAGE = "com.dy.rpc.core.spring.client";
    private static final String BASE_PACKAGE_ATTRIBUTE_NAME = "basePackage";
    private ResourceLoader resourceLoader;

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;

    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        // 获取RPCServiceClientScan注解的所有属性和值
        AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(RPCServiceClientScan.class.getName()));
        String[] basePackages = new String[0];
        if (annotationAttributes != null) {
            // 获取basePackage属性的值，即指定所扫描的包
            basePackages = annotationAttributes.getStringArray(BASE_PACKAGE_ATTRIBUTE_NAME);
        }
        if (basePackages.length == 0) {
            // 若未指定，则默认扫描该注解标记类所在的包
            basePackages = new String[]{((StandardAnnotationMetadata) annotationMetadata).getIntrospectedClass().getPackage().getName()};
        }


        // 只扫描Component注解
        SpringScanner springBeanScanner = new SpringScanner(beanDefinitionRegistry, Component.class);
        if (resourceLoader != null) {
            springBeanScanner.setResourceLoader(resourceLoader);
        }
        int springBeanAmount = springBeanScanner.scan(SPRING_BEAN_BASE_PACKAGE)
                + springBeanScanner.scan(basePackages);
        log.info("springBeanScanner扫描的数量 [{}]", springBeanAmount);

    }
}
