package com.dy.rpc.server.service;

import com.dy.rpc.api.ByeService;
import com.dy.rpc.core.annotation.RPCService;

/**
 * @Author: chengyibai
 * @Date: 2021-04-08 20:20
 */
@RPCService(group = "group_2", version = "version_1.0")
public class ByeServiceImpl2 implements ByeService {

    @Override
    public String bye(String name) {
        return name + " too. 2.0版本";
    }

}
