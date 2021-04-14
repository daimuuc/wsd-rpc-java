package com.dy.rpc.core.spring;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.lang.annotation.Annotation;

/**
 * 自定义包扫描器
 *
 * @Author: chengyibai
 * @Date: 2021-04-12 20:21
 */
public class SpringScanner extends ClassPathBeanDefinitionScanner {
    public SpringScanner(BeanDefinitionRegistry registry, Class<? extends Annotation> annoType) {
        super(registry);
        //添加过滤条件，这里是只添加了annoType的注解才会被扫描到
        super.addIncludeFilter(new AnnotationTypeFilter(annoType));
    }

    @Override
    public int scan(String... basePackages) {
        return super.scan(basePackages);
    }
}
