package com.dy.rpc.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 注册中心发现服务时所用的负载均衡器
 *
 * @Author: chenyibai
 * @Date: 2021/1/21 17:39
 */
@AllArgsConstructor
@Getter
public enum LoadBalancerCode {

    RANDOM(0),
    POLLING(1);

    private final int code;

}
