package com.dy.rpc.server.service;

import com.dy.rpc.api.ByeService;
import com.dy.rpc.core.annotation.Service;

/**
 * @Author: chenyibai
 * @Date: 2021/1/22 17:50
 */
@Service
public class ByeServiceImpl implements ByeService {

    @Override
    public String bye(String name) {
        return name + " too";
    }

}
