package com.dy.rpc.core.annotation;

import java.lang.annotation.*;

/**
 * 使用远程服务端提供的服务类，即用于客户端
 *
 * @Author: chengyibai
 * @Date: 2021-04-13 18:31
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Inherited
public @interface RPCReference {
    /**
     * 服务版本号
     */
    String version() default "";

    /**
     * 服务分组
     */
    String group() default "";
}
