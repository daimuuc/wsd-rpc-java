package com.dy.rpc.core.codec;

import com.dy.rpc.common.entity.RpcRequest;
import com.dy.rpc.common.entity.RpcResponse;
import com.dy.rpc.common.enums.PackageType;
import com.dy.rpc.common.enums.RpcError;
import com.dy.rpc.common.exception.RpcException;
import com.dy.rpc.core.compress.Compress;
import com.dy.rpc.core.serializer.CommonSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Socket方式从输入流中读取字节并反序列化
 *
 * 调用参数与返回值的传输采用了如下rpc协议以防止粘包：
 *
 * +---------------+---------------+-----------------+-------------+-------------+
 * |  Magic Number |  Package Type | Serializer Type |Compress Type| Data Length |
 * |    4 bytes    |    4 bytes    |     4 bytes     |   4 bytes   |   4 bytes   |
 * +---------------+---------------+-----------------+-------------+-------------+
 * |                                    Data Bytes                               |
 * |                                Length: ${Data Length}                       |
 * +-----------------------------------------------------------------------------+
 *
 * 字段             |    解释
 * Magic Number    |	魔数，表识一个rpc协议包，0xCAFEBABE
 * Package Type	   |    包类型，标明这是一个调用请求还是调用响应
 * Serializer Type |	序列化器类型，标明这个包的数据的序列化方式
 * Compress Type   |    压缩类型
 * Data Length	   |    数据字节的长度
 * Data Bytes	   |    传输的对象，通常是一个RpcRequest或RpcResponse对象，取决于Package Type字段，对象的序列化方式取决于Serializer Type字段。
 *
 * @Author: chenyibai
 * @Date: 2021/1/19 14:49
 */
public class ObjectReader {
    private static final Logger logger = LoggerFactory.getLogger(ObjectReader.class);
    private static final int MAGIC_NUMBER = 0xCAFEBABE;

    public static Object readObject(InputStream in) throws IOException {
        byte[] numberBytes = new byte[4];
        in.read(numberBytes);
        int magic = bytesToInt(numberBytes);
        if (magic != MAGIC_NUMBER) {
            logger.error("不识别的协议包: {}", magic);
            throw new RpcException(RpcError.UNKNOWN_PROTOCOL);
        }
        in.read(numberBytes);
        int packageCode = bytesToInt(numberBytes);
        Class<?> packageClass;
        if (packageCode == PackageType.REQUEST_PACK.getCode()) {
            packageClass = RpcRequest.class;
        } else if (packageCode == PackageType.RESPONSE_PACK.getCode()) {
            packageClass = RpcResponse.class;
        } else {
            logger.error("不识别的数据包: {}", packageCode);
            throw new RpcException(RpcError.UNKNOWN_PACKAGE_TYPE);
        }
        in.read(numberBytes);
        int serializerCode = bytesToInt(numberBytes);
        CommonSerializer serializer = CommonSerializer.getByCode(serializerCode);
        if (serializer == null) {
            logger.error("不识别的反序列化器: {}", serializerCode);
            throw new RpcException(RpcError.UNKNOWN_SERIALIZER);
        }
        in.read(numberBytes);
        int compressCode = bytesToInt(numberBytes);
        Compress compress = Compress.getByCode(compressCode);
        if (compress == null) {
            logger.error("不识别的解压类型: {}", compressCode);
            throw new RpcException(RpcError.UNKNOWN_COMPRESS);
        }
        in.read(numberBytes);
        int length = bytesToInt(numberBytes);
        byte[] bytes = new byte[length];
        in.read(bytes);
        bytes = compress.decompress(bytes);
        return serializer.deserialize(bytes, packageClass);
    }

    public static int bytesToInt(byte[] src) {
        int value;
        value = (src[0] & 0xFF)
                | ((src[1] & 0xFF)<<8)
                | ((src[2] & 0xFF)<<16)
                | ((src[3] & 0xFF)<<24);
        return value;
    }

}
