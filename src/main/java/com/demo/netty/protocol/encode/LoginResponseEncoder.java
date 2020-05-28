//package com.demo.netty.protocol.encode;
//
//import com.demo.netty.client.packet.MessageStructureAbstract;
//import com.supconit.its.extract.packet.AbstractJt809BasePacket;
//import com.supconit.its.extract.packet.server.Jt809LoginResponsePacket;
//import io.netty.buffer.ByteBuf;
//import io.netty.channel.ChannelHandlerContext;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**
// * <p> 数据包解码器 </p>
// *
// * @author zhangqi
// * @date 2019-11-27 15:20
// */
//public class LoginResponseEncoder implements Encoder {
//    private static Logger log = LoggerFactory.getLogger(LoginResponseEncoder.class);
//    @Override
//    public void encode(ChannelHandlerContext ctx, MessageStructureAbstract packet, ByteBuf out) {
//        log.info("登录响应开始编码！");
//        Jt809LoginResponsePacket responsePacket = (Jt809LoginResponsePacket) packet;
//
//    }
//
//}
