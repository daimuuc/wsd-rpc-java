package com.dy.rpc.core.transport.server.netty;

import com.dy.rpc.common.enumeration.RpcError;
import com.dy.rpc.common.exception.RpcException;
import com.dy.rpc.common.extension.ExtensionLoader;
import com.dy.rpc.core.codec.CommonDecoder;
import com.dy.rpc.core.codec.CommonEncoder;
import com.dy.rpc.core.compress.Compress;
import com.dy.rpc.core.hook.ShutdownHook;
import com.dy.rpc.core.provider.ServiceProvider;
import com.dy.rpc.core.provider.impl.ServiceProviderImpl;
import com.dy.rpc.core.registry.ServiceRegistry;
import com.dy.rpc.core.registry.impl.NacosServiceRegistry;
import com.dy.rpc.core.serializer.CommonSerializer;
import com.dy.rpc.core.transport.server.AbstractRpcServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * NIO方式服务提供侧
 *
 * @Author: chenyibai
 * @Date: 2021/1/20 16:57
 */
public class NettyServer extends AbstractRpcServer {
    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);

    private CommonSerializer serializer;
    private Compress compress;

    public NettyServer(String host, int port) {
        this.host = host;
        this.port = port;
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

        ShutdownHook.getShutdownHook().addClearAllHook();
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .option(ChannelOption.SO_BACKLOG, 256)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new IdleStateHandler(30, 0, 0, TimeUnit.SECONDS));
                            pipeline.addLast(new CommonEncoder(serializer, compress));
                            pipeline.addLast(new CommonDecoder());
                            pipeline.addLast(new NettyServerHandler());
                        }
                    });
            ChannelFuture future = serverBootstrap.bind(port).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            logger.error("启动服务器时有错误发生: ", e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
