//package com.demo.netty.protocol.decoder.client;
//
//import com.supconit.its.extract.common.Const;
//import com.supconit.its.extract.common.util.PacketDecoderUtils;
//import com.supconit.its.extract.packet.AbstractJt809BasePacket;
//import com.supconit.its.extract.packet.server.Jt809LoginPacket;
//import com.supconit.its.extract.protocol.decoder.Decoder;
//import io.netty.buffer.ByteBuf;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.nio.charset.Charset;
//
///**
// * <p> 从链路登录响应数据包解码类 </p>
// *
// * @author zhangqi
// * @date 2019-11-27 15:20
// */
//public class LoginResponseDecoder implements Decoder {
//    private static Logger log = LoggerFactory.getLogger(LoginResponseDecoder.class);
//
//    @Override
//    public AbstractJt809BasePacket decoder(byte[] bytes) throws Exception {
//        Jt809LoginPacket loginPacket = new Jt809LoginPacket();
//        ByteBuf byteBuf = PacketDecoderUtils.baseDecoder(bytes, loginPacket);
//        loginPacketDecoder(byteBuf, loginPacket);
//        return loginPacket;
//    }
//
//    /**
//     * 登录包数据包解码
//     *
//     * @param byteBuf     字节缓冲区
//     * @param loginPacket 登录数据包对象
//     * @throws Exception 异常
//     */
//    private void loginPacketDecoder(ByteBuf byteBuf, Jt809LoginPacket loginPacket) throws Exception {
//        ByteBuf msgBodyBuf = null;
//        if (loginPacket.getEncryptFlag() == Const.EncryptFlag.NO) {
//            log.info("报文未加密！继续处理。");
//            msgBodyBuf = PacketDecoderUtils.getMsgBodyBuf(byteBuf);
//        } else {
//            // TODO: 解密
//            log.info("报文已加密！未处理。");
//            msgBodyBuf = PacketDecoderUtils.getMsgBodyBuf(byteBuf);
//            return;
//        }
//        loginPacket.setUserId(msgBodyBuf.readInt());
//        byte[] passwordBytes = new byte[8];
//        msgBodyBuf.readBytes(passwordBytes);
//        loginPacket.setPassword(new String(passwordBytes, Charset.forName("GBK")));
//        byte[] downLinkIpBytes = new byte[32];
//        msgBodyBuf.readBytes(downLinkIpBytes);
//        //  清除后面的0x00
//        downLinkIpBytes = cleanAfter0x00(downLinkIpBytes);
//        //  清除前面的0x00
//        downLinkIpBytes = cleanBefore0x00(downLinkIpBytes);
//        loginPacket.setDownLinkIp(new String(downLinkIpBytes, Charset.forName("GBK")));
//        loginPacket.setDownLinkPort(msgBodyBuf.readShort());
//    }
//
//    /**
//     * 清除后面的0x00
//     *
//     * @param srcb 字节数组
//     * @return 清除后的字节数组
//     */
//    public static byte[] cleanAfter0x00(byte[] srcb) {
//        byte[] target = null;
//        int index = srcb.length - 1;
//        for (int i = index; i > 0; i--) {
//            if (srcb[i] != 0x00) {
//                target = new byte[i + 1];
//                for (int j = 0; j <= i; j++) {
//                    target[j] = srcb[j];
//                }
//                return target;
//            }
//        }
//        return target;
//    }
//
//    /**
//     * 清除数据前面加0x00
//     *
//     * @param srcb 字节数组
//     * @return 清除后的字节数组
//     */
//    public static byte[] cleanBefore0x00(byte[] srcb) {
//        byte[] target = null;
//        boolean pd = true;
//        int index = 0;
//        for (int i = 0; i < srcb.length; i++) {
//            if (pd) {
//                if (srcb[i] != 0x00) {
//                    target = new byte[srcb.length - i];
//                    target[index] = srcb[i];
//                    index++;
//                    pd = false;
//                }
//            } else {
//                target[index] = srcb[i];
//                index++;
//            }
//        }
//        return target;
//    }
//
//}
