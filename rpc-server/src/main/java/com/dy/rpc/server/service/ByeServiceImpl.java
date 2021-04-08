package com.dy.rpc.server.service;

import com.dy.rpc.api.ByeService;
import com.dy.rpc.core.annotation.RPCService;

/**
 * @Author: chenyibai
 * @Date: 2021/1/22 17:50
 */
@RPCService(group = "group_1", version = "version_1.0")
public class ByeServiceImpl implements ByeService {

    @Override
    public String bye(String name) {
        return name + " too";
    }

}
