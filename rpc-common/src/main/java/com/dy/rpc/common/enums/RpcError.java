package com.dy.rpc.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * RPC调用过程中的错误
 *
 * @Author: chenyibai
 * @Date: 2021/1/19 14:22
 */
@AllArgsConstructor
@Getter
public enum RpcError {

    UNKNOWN_ERROR("出现未知错误"),
    SERVICE_SCAN_PACKAGE_NOT_FOUND("启动类ServiceScan注解缺失"),
    CLIENT_CONNECT_SERVER_FAILURE("客户端连接服务端失败"),
    SERVICE_INVOCATION_FAILURE("服务调用出现失败"),
    SERVICE_NOT_FOUND("找不到对应的服务"),
    SERVICE_NOT_IMPLEMENT_ANY_INTERFACE("注册的服务未实现接口"),
    UNKNOWN_PROTOCOL("不识别的协议包"),
    UNKNOWN_SERIALIZER("不识别的(反)序列化器"),
    UNKNOWN_PACKAGE_TYPE("不识别的数据包类型"),
    UNKNOWN_COMPRESS("不识别的(解压)压缩类型"),
    SERIALIZER_NOT_FOUND("找不到序列化器"),
    COMPRESS_NOT_FOUND("找不到(解压)压缩方法"),
    RESPONSE_NOT_MATCH("响应与请求号不匹配"),
    FAILED_TO_CONNECT_TO_SERVICE_REGISTRY("连接注册中心失败"),
    REGISTER_SERVICE_FAILED("注册服务失败");

    private final String message;

}
