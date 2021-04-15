package com.dy.rpc.core.annotation;

import com.dy.rpc.core.spring.client.ClientScannerRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 服务扫描, 用于客户端
 *
 * @Author: chengyibai
 * @Date: 2021-04-13 21:20
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Import(ClientScannerRegistrar.class)
@Documented
public @interface RPCServiceClientScan {

    String[] basePackage() default {};

}
