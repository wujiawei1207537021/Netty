package com.demo.netty.client.packet;

public interface MessageStructure {


    default byte[] getBytes() {
        return new byte[]{};
    }

}
