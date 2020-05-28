package com.demo.netty.protocol;

import com.demo.netty.client.packet.MessageStructureAbstract;
import com.demo.netty.util.CommonUtils;
import com.demo.netty.util.PacketDecoderUtils;
import com.demo.netty.util.PacketEncoderUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * <p> 编码适配器类 </p>
 *
 * @author zhangqi
 * @date 2019-11-27 15:20
 */
@Slf4j
public class Jt809EncodeAdapter extends MessageToByteEncoder<MessageStructureAbstract> {

    @Override
    protected void encode(ChannelHandlerContext ctx, MessageStructureAbstract packet, ByteBuf out) throws Exception {
        byte[] allBody = packet.getBytes();
        // 转义
        byte[] dataBytes = PacketEncoderUtils.encoderEscape(allBody);
        byte[] bytes1 = CommonUtils.append(new byte[]{MessageStructureAbstract.START_IDENTIFIED}, dataBytes);
        byte[] bytes = CommonUtils.append(bytes1, new byte[]{MessageStructureAbstract.END_IDENTIFIED});
        String hexStr = PacketDecoderUtils.bytes2HexStr(bytes);
        log.info("发出的报文为：{}",hexStr);
        out.writeBytes(bytes);
    }
}
