package com.dy.rpc.core.serializer;

import com.dy.rpc.core.serializer.impl.HessianSerializer;
import com.dy.rpc.core.serializer.impl.JsonSerializer;
import com.dy.rpc.core.serializer.impl.KryoSerializer;

/**
 * @Author: chenyibai
 * @Date: 2021/1/19 15:06
 */
public interface CommonSerializer {

    Integer KRYO_SERIALIZER = 0;
    Integer JSON_SERIALIZER = 1;
    Integer HESSIAN_SERIALIZER = 2;

    Integer DEFAULT_SERIALIZER = KRYO_SERIALIZER;

    static CommonSerializer getByCode(int code) {
        switch (code) {
            case 0:
                return new KryoSerializer();
            case 1:
                return new JsonSerializer();
            case 2:
                return new HessianSerializer();
            default:
                return null;
        }
    }

    byte[] serialize(Object obj);

    Object deserialize(byte[] bytes, Class<?> clazz);

    int getCode();

}
