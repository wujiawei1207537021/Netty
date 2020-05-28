package com.demo.netty.protocol.encode;

import com.demo.netty.client.packet.MessageStructureAbstract;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

/**
 * <p> 编码器接口类 </p>
 *
 * @author zhangqi
 * @date 2019-11-27 15:20
 */
public interface Encoder {
    /**
     * 编码
     *  @param ctx    消息通道
     * @param packet 数据包
     * @param out    字节输出
     */
    void encode(ChannelHandlerContext ctx, MessageStructureAbstract packet, ByteBuf out);
}
