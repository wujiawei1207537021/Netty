package com.demo.netty.client.packet;

/**
 * 作用 实现 getBytes 方法
 */
public interface Message {


    default byte[] getBytes() {
        return new byte[]{};
    }

}
