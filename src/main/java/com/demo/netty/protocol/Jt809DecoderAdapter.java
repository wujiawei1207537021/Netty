//package com.demo.netty.protocol;
//
//
//import com.demo.netty.client.packet.MessageStructureAbstract;
//import com.demo.netty.util.CommonUtils;
//import com.demo.netty.util.CrcUtil;
//import com.demo.netty.util.PacketDecoderUtils;
//import com.sun.org.apache.bcel.internal.Const;
//import io.netty.buffer.ByteBuf;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.handler.codec.ByteToMessageDecoder;
//import lombok.extern.slf4j.Slf4j;
//
//import java.util.List;
//
//
///**
// * <p> 数据包解码器 </p>
// *
// * @author zhangqi
// * @date 2019-11-27 15:20
// */
//@Slf4j
//public class Jt809DecoderAdapter extends ByteToMessageDecoder {
//
//    private int argIndex;
//
//    public Jt809DecoderAdapter(int argIndex) {
//        this.argIndex = argIndex;
//    }
//
//    @Override
//    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
//        //判断是否有可读的字节
//        if (in.readableBytes() <= 0) {
//            return;
//        }
//        // 1、进行转义
//        byte[] bytes = PacketDecoderUtils.decoderEscape(in);
//        // 2、校验crc
////        if (!CrcUtil.checkCRC(bytes, Const.CRC.get(argIndex))) {
////            return;
////        }
//        // 3、判断业务数据类型，交给具体的解码器类完成。
//        ByteBuf byteBuf = CommonUtils.getByteBuf(bytes);
//        //  跳到业务数据类型字节前
//        byteBuf.skipBytes(9);
//        // 获取业务标志[MSG_ID 2字节]
//        short msgId = byteBuf.readShort();
//        // 交给具体的解码器
//        MessageStructureAbstract packet;
//        try {
////            packet = DecoderFactory.getDecoder(msgId).decoder(bytes);
//        } catch (Exception e) {
//            if (e instanceof NullPointerException) {
//                log.info("尚未支持该业务数据包,无可用解析器，报文信息：{}；", e.getMessage(), PACKET_CACHE.get(Thread.currentThread().getName()));
//            } else {
//                log.error("报文解析出错！错误信息：{}；报文信息：{}；", e.getMessage(), PACKET_CACHE.get(Thread.currentThread().getName()));
//            }
//            return;
//        }
//        out.add(packet);
//    }
//}
