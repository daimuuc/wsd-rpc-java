package com.dy.rpc.common.extension;

/**
 * @Author: chenyibai
 * @Date: 2021/1/26 15:31
 */
public class Holder<T> {
    private volatile T value;

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }
}
