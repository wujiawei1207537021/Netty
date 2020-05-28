package com.demo.netty.client.packet;

import lombok.Data;

/**
 * @Description
 *   包体  数据包的包体由消息头、消息体和附加消息组成，其中附加消息独立编码，不受 消息命令字的约束，用于协议扩展。
 * @Author 吴佳伟
 * @Date 2020-05-28 5:06 下午
 */


@Data
public abstract class PacketBody implements MessageStructure{

    //        private String messageHeader;
//
//        private String messageBody;
//
//        private String attachMessage;
}
