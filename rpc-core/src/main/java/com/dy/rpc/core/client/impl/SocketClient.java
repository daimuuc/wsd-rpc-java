package com.dy.rpc.core.client.impl;

import com.dy.rpc.common.entity.RpcRequest;
import com.dy.rpc.common.entity.RpcResponse;
import com.dy.rpc.common.enumeration.ResponseCode;
import com.dy.rpc.common.enumeration.RpcError;
import com.dy.rpc.common.exception.RpcException;
import com.dy.rpc.common.factory.ThreadPoolFactory;
import com.dy.rpc.common.util.RpcMessageChecker;
import com.dy.rpc.core.client.RpcClient;
import com.dy.rpc.core.codec.ObjectReader;
import com.dy.rpc.core.codec.ObjectWriter;
import com.dy.rpc.core.provider.impl.ServiceProviderImpl;
import com.dy.rpc.core.serializer.CommonSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Socket方式远程方法调用的消费者（客户端）
 *
 * @Author: chenyibai
 * @Date: 2021/1/19 15:35
 */
public class SocketClient implements RpcClient {

    private static final Logger logger = LoggerFactory.getLogger(SocketClient.class);

    private final String host;
    private final int port;
    private CommonSerializer serializer;

    public SocketClient(String host, int port) {
        this(host, port, DEFAULT_SERIALIZER);
    }

    public SocketClient(String host, int port, Integer serializer) {
        this.host = host;
        this.port = port;
        this.serializer = CommonSerializer.getByCode(serializer);
    }

    @Override
    public Object sendRequest(RpcRequest rpcRequest) {
        if(serializer == null) {
            logger.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        try (Socket socket = new Socket(host, port)) {
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();
            ObjectWriter.writeObject(outputStream, rpcRequest, serializer);
            Object obj = ObjectReader.readObject(inputStream);
            RpcResponse rpcResponse = (RpcResponse) obj;
            if (rpcResponse == null) {
                logger.error("服务调用失败，service：{}", rpcRequest.getInterfaceName());
                throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, " service:" + rpcRequest.getInterfaceName());
            }
            if (rpcResponse.getStatusCode() == null || rpcResponse.getStatusCode() != ResponseCode.SUCCESS.getCode()) {
                logger.error("调用服务失败, service: {}, response:{}", rpcRequest.getInterfaceName(), rpcResponse);
                throw new RpcException(RpcError.SERVICE_INVOCATION_FAILURE, " service:" + rpcRequest.getInterfaceName());
            }
            RpcMessageChecker.check(rpcRequest, rpcResponse);
            return rpcResponse.getData();
        } catch (IOException e) {
            logger.error("调用时有错误发生：", e);
            throw new RpcException("服务调用失败: ", e);
        }
    }

}
