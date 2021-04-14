package com.dy.rpc.client;

import com.dy.rpc.core.annotation.RPCServiceClientScan;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Author: chenyibai
 * @Date: 2021/1/19 16:04
 */
@RPCServiceClientScan(basePackage = {"com.dy.rpc.client"})
public class SpringTestClient {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringTestClient.class);
        SpringController springController = (SpringController) applicationContext.getBean("springController");
        springController.test();
    }

}
