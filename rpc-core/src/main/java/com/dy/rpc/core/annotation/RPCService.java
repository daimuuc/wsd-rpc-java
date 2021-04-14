package com.dy.rpc.core.annotation;

import java.lang.annotation.*;

/**
 * 表示一个服务提供类，用于远程接口的实现类，即用于服务端
 *
 * @Author: chenyibai
 * @Date: 2021/1/20 11:50
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RPCService {
    /**
     * 服务版本号
     */
    String version() default "";

    /**
     * 对服务分组，处理一个接口有多个类实现的情况
     */
    String group() default "";

}
