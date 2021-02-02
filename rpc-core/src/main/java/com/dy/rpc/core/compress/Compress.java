package com.dy.rpc.core.compress;

import com.dy.rpc.common.extension.SPI;
import com.dy.rpc.core.compress.impl.GzipCompress;
import com.dy.rpc.core.compress.impl.SnappyCompress;

/**
 * @Author: chenyibai
 * @Date: 2021/2/1 15:56
 */
@SPI
public interface Compress {
    Integer GZIP_COMPRESS = 0;
    Integer SNAPPY_COMPRESS = 1;

    Integer DEFAULT_COMPRESS = GZIP_COMPRESS;

    static Compress getByCode(int code) {
        switch (code) {
            case 0:
                return new GzipCompress();
            case 1:
                return new SnappyCompress();
            default:
                return null;
        }
    }

    byte[] compress(byte[] bytes);

    byte[] decompress(byte[] bytes);

    int getCode();

}
