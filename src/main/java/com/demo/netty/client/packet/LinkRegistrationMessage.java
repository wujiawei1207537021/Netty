package com.demo.netty.client.packet;

import com.demo.netty.util.CommonUtils;
import lombok.Data;

/**
 * @Description 链路注册消息
 * @Author 吴佳伟
 * @Date 2020-05-28 3:09 下午
 */
@Data
public class LinkRegistrationMessage extends MessageStructureAbstract implements MessageStructure  {


    /**
     * 客户端 ID
     */
    private String clientId = "gps_zdzk";
    /**
     * 注册码
     */
    private String registrationCode = "gps@zdzk";

    public LinkRegistrationMessage() {
    }

    public LinkRegistrationMessage(String clientId, String registrationCode) {
        this.clientId = clientId;
        this.registrationCode = registrationCode;
    }

    @Override
    public PacketBody getPacketBody(){
        PacketBody packetBody=new PacketBody() {
            @Override
            public byte[] getBytes() {
                return CommonUtils.append(getClientId().getBytes(),getRegistrationCode().getBytes());
            }
        };
       return packetBody;
    }

    @Override
    public byte[] getBytes() {
        return super.getBytes();
    }
}
