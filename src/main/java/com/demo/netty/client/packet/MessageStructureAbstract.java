package com.demo.netty.client.packet;

import com.demo.netty.util.CommonUtils;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @Description 消息结构  由起始标识、数据包类型、数据包内容、校验码和结束标识组成
 * @Author 吴佳伟
 * @Date 2020-05-28 1:55 下午
 */
@Data
@NoArgsConstructor
public abstract class MessageStructureAbstract implements MessageStructure {

    public MessageStructureAbstract(byte packetType, PacketBody packetBody, byte checkCode) {
        this.packetHead = new PacketHead() {

        };
        this.packetType = packetType;
        this.packetBody = packetBody;
        this.checkCode = checkCode;
    }

    public MessageStructureAbstract(byte packetType, PacketHead packetHead, PacketBody packetBody, byte checkCode) {
        this.packetType = packetType;
        this.packetHead = packetHead;
        this.packetBody = packetBody;
        this.checkCode = checkCode;
    }

    public MessageStructureAbstract(PacketBody packetBody) {
        this.packetBody = packetBody;
    }

    /**
     * 由起始标识、数据包类型、数据包内容、校验码和结束标识组成，消息 结构如下表所示
     */
    public static byte START_IDENTIFIED = 0x5b;

    /**
     * 默认数据包类型 链路管理包 0x01  业务数据包 0x00
     */
    private byte packetType = 0x01;

    public byte getPacketType() {
        return 0x01;
    }

    /**
     * 包头
     */
    private PacketHead packetHead;

    public PacketHead getPacketHead() {
        return packetHead;
    }

    /**
     * 包体 数据包的包体由消息头、消息体和附加消息组成，其中附加消息独立编码，不受 消息命令字的约束，用于协议扩展。
     */
    private PacketBody packetBody;

    public PacketBody getPacketBody() {
        return packetBody;
    }

    /**
     * 校验码是指从数据包类型（即第二个字节）开始，同后一字节异或，直到校验码前 一个字节，占用一个字节。
     */
    private byte checkCode;

    int getCheckCode() {
        byte[] typeBytes = CommonUtils.int2bytes(this.getPacketType());
        return typeBytes[2] ^ typeBytes[3];
    }

    public static byte END_IDENTIFIED = 0x5d;

    public byte[] getBytes() {
        byte[] bytes1 = new byte[]{START_IDENTIFIED};
        byte[] bytes2 = CommonUtils.append(bytes1, new byte[]{this.getPacketType()});
        byte[] bytes3 = CommonUtils.append(bytes2, getPacketHead().getBytes());
        byte[] bytes4 = CommonUtils.append(bytes3, getPacketBody().getBytes());
        byte[] bytes5 = CommonUtils.append(bytes4, CommonUtils.int2bytes(getCheckCode()));
        byte[] bytes = CommonUtils.append(bytes5, new byte[]{END_IDENTIFIED});
        return bytes;
    }


}
