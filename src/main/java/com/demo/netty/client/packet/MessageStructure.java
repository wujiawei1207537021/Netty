package com.demo.netty.client.packet;

/***
 * 作用实现获取消息体 消息头
 */
public interface MessageStructure extends Message {



     byte getPacketType();


     PacketHead getPacketHead();

    /**
     * 包体 数据包的包体由消息头、消息体和附加消息组成，其中附加消息独立编码，不受 消息命令字的约束，用于协议扩展。
     */


     PacketBody getPacketBody();




}
