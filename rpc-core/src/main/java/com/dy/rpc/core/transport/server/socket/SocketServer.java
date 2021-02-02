package com.dy.rpc.core.transport.server.socket;

import com.dy.rpc.common.enumeration.RpcError;
import com.dy.rpc.common.exception.RpcException;
import com.dy.rpc.common.extension.ExtensionLoader;
import com.dy.rpc.common.factory.ThreadPoolFactory;
import com.dy.rpc.core.compress.Compress;
import com.dy.rpc.core.provider.ServiceProvider;
import com.dy.rpc.core.provider.impl.ServiceProviderImpl;
import com.dy.rpc.core.registry.ServiceDiscovery;
import com.dy.rpc.core.registry.ServiceRegistry;
import com.dy.rpc.core.registry.impl.NacosServiceRegistry;
import com.dy.rpc.core.serializer.CommonSerializer;
import com.dy.rpc.core.transport.server.RequestHandler;
import com.dy.rpc.core.transport.server.AbstractRpcServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * Socket方式远程方法调用的提供者（服务端）
 *
 * @Author: chenyibai
 * @Date: 2021/1/19 15:41
 */
public class SocketServer extends AbstractRpcServer {

    private static final Logger logger = LoggerFactory.getLogger(SocketServer.class);

    private final ExecutorService threadPool;
    private CommonSerializer serializer;
    private Compress compress;
    private RequestHandler requestHandler = new RequestHandler();

    public SocketServer(String host, int port) {
        this.host = host;
        this.port = port;
        threadPool = ThreadPoolFactory.createDefaultThreadPool("socket-rpc-server");
        this.serviceRegistry = ExtensionLoader.getExtensionLoader(ServiceRegistry.class).getExtension("serviceRegistry");
        this.serviceProvider = ExtensionLoader.getExtensionLoader(ServiceProvider.class).getExtension("serviceProvider");
        this.serializer = ExtensionLoader.getExtensionLoader(CommonSerializer.class).getExtension("commonSerializer");
        this.compress = ExtensionLoader.getExtensionLoader(Compress.class).getExtension("compress");
        scanServices();
    }

    @Override
    public void start() {
        if(serializer == null) {
            logger.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        if (compress == null) {
            logger.error("未设置（解压）压缩方法");
            throw new RpcException(RpcError.COMPRESS_NOT_FOUND);
        }

        try (ServerSocket serverSocket = new ServerSocket()) {
            serverSocket.bind(new InetSocketAddress(host, port));
            logger.info("服务器启动……");
            Socket socket;
            while ((socket = serverSocket.accept()) != null) {
                logger.info("消费者连接: {}:{}", socket.getInetAddress(), socket.getPort());
                threadPool.execute(new RequestHandlerThread(socket, requestHandler, serializer, compress));
            }
            threadPool.shutdown();
        } catch (IOException e) {
            logger.error("服务器启动时有错误发生:", e);
        }
    }

}
