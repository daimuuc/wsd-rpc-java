package com.dy.rpc.core.transport.client.socket;

import com.dy.rpc.common.entity.RpcRequest;
import com.dy.rpc.common.entity.RpcResponse;
import com.dy.rpc.common.enums.ResponseCode;
import com.dy.rpc.common.enums.RpcError;
import com.dy.rpc.common.exception.RpcException;
import com.dy.rpc.common.extension.ExtensionLoader;
import com.dy.rpc.common.utils.RpcMessageChecker;
import com.dy.rpc.core.compress.Compress;
import com.dy.rpc.core.transport.client.RpcClient;
import com.dy.rpc.core.codec.ObjectReader;
import com.dy.rpc.core.codec.ObjectWriter;
import com.dy.rpc.core.registry.ServiceDiscovery;
import com.dy.rpc.core.serializer.CommonSerializer;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Socket方式远程方法调用的消费者（客户端）
 *
 * @Author: chenyibai
 * @Date: 2021/1/19 15:35
 */
@Slf4j
public class SocketClient implements RpcClient {

    private static final Logger logger = LoggerFactory.getLogger(SocketClient.class);

    private final ServiceDiscovery serviceDiscovery;
    private final CommonSerializer serializer;
    private final Compress compress;

    public SocketClient() {
        this.serviceDiscovery = ExtensionLoader.getExtensionLoader(ServiceDiscovery.class).getExtension("serviceDiscovery");
        this.serializer = ExtensionLoader.getExtensionLoader(CommonSerializer.class).getExtension("commonSerializer");
        this.compress = ExtensionLoader.getExtensionLoader(Compress.class).getExtension("compress");
    }

    @Override
    public Object sendRequest(RpcRequest rpcRequest) {
        if(serializer == null) {
            logger.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        if (compress == null) {
            logger.error("未设置（解压）压缩方法");
            throw new RpcException(RpcError.COMPRESS_NOT_FOUND);
        }

        InetSocketAddress inetSocketAddress = serviceDiscovery.lookupService(rpcRequest.getInterfaceName());
        try (Socket socket = new Socket()) {
            socket.connect(inetSocketAddress);
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();
            ObjectWriter.writeObject(outputStream, rpcRequest, serializer, compress);
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
            return rpcResponse;
        } catch (IOException e) {
            logger.error("调用时有错误发生：", e);
            throw new RpcException("服务调用失败: ", e);
        }
    }

}
