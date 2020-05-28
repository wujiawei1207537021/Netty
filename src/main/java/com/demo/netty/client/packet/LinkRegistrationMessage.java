package com.demo.netty.client.packet;

import lombok.Data;

/**
 * @Description 链路注册消息
 * @Author 吴佳伟
 * @Date 2020-05-28 3:09 下午
 */
@Data
public class LinkRegistrationMessage extends LinkManagementPackage {


    /**
     * 客户端 ID
     */
    private String clientId = "gps_zdzk";
    /**
     * 注册码
     */
    private String registrationCode = "gps@zdzk";

    public LinkRegistrationMessage( PacketBody packetBody, String clientId, String registrationCode) {
        super(packetBody);
        this.clientId = clientId;
        this.registrationCode = registrationCode;
    }
}
