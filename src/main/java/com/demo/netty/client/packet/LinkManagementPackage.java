package com.demo.netty.client.packet;

/**
 * @Description 链路管理包
 * @Author 吴佳伟
 * @Date 2020-05-28 3:02 下午
 */
public class LinkManagementPackage extends MessageStructureAbstract{

    public LinkManagementPackage(byte packetType, PacketBody packetBody, byte checkCode) {
        super(packetType, packetBody, checkCode);
    }

    public LinkManagementPackage(byte packetType, PacketHead packetHead, PacketBody packetBody, byte checkCode) {
        super(packetType, packetHead, packetBody, checkCode);
    }


    public LinkManagementPackage(PacketBody packetBody) {
        super(packetBody);
    }
}
