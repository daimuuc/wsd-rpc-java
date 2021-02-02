package com.dy.rpc.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 字节流中标识解压和压缩方法
 *
 * @Author: chenyibai
 * @Date: 2021/2/1 16:42
 */
@AllArgsConstructor
@Getter
public enum CompressCode {
    GZIP(0),
    SNAPPY(1);

    private final int code;
}
