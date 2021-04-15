package com.dy.rpc.core.annotation;

import com.dy.rpc.core.spring.server.ServerScannerRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 服务扫描, 用于服务端
 *
 * @Author: chenyibai
 * @Date: 2021/1/20 11:52
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Import(ServerScannerRegistrar.class)
@Documented
public @interface RPCServiceServerScan {

    String[] basePackage() default {};

}
