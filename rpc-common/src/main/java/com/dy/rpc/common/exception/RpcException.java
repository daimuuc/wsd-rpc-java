package com.dy.rpc.common.exception;

import com.dy.rpc.common.enums.RpcError;

/**
 * @Author: chenyibai
 * @Date: 2021/1/19 14:21
 */
public class RpcException extends RuntimeException {

    public RpcException(RpcError error, String detail) {
        super(error.getMessage() + ": " + detail);
    }

    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcException(RpcError error) {
        super(error.getMessage());
    }

}
