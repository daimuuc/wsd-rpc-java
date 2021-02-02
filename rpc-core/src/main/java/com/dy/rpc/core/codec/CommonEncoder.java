package com.dy.rpc.core.codec;

import com.dy.rpc.common.entity.RpcRequest;
import com.dy.rpc.common.enumeration.PackageType;
import com.dy.rpc.core.compress.Compress;
import com.dy.rpc.core.serializer.CommonSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Author: chenyibai
 * @Date: 2021/1/20 16:42
 */
public class CommonEncoder extends MessageToByteEncoder {

    private static final int MAGIC_NUMBER = 0xCAFEBABE;

    private final CommonSerializer serializer;
    private final Compress compress;

    public CommonEncoder(CommonSerializer serializer, Compress compress) {
        this.serializer = serializer;
        this.compress = compress;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        out.writeInt(MAGIC_NUMBER);
        if (msg instanceof RpcRequest) {
            out.writeInt(PackageType.REQUEST_PACK.getCode());
        } else {
            out.writeInt(PackageType.RESPONSE_PACK.getCode());
        }
        out.writeInt(serializer.getCode());
        out.writeInt(compress.getCode());
        byte[] bytes = serializer.serialize(msg);
        bytes = compress.compress(bytes);
        out.writeInt(bytes.length);
        out.writeBytes(bytes);
    }

}
