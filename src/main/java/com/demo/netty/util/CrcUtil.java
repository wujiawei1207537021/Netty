package com.demo.netty.util;

import io.netty.buffer.ByteBuf;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * <p> crc校验工具类 </p>
 *
 * @author zhangqi
 * @date 2019-11-27 15:20
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CrcUtil {

    /**
     * 校验crc码
     *
     * @param bytes    数据
     * @param crcCheck 校验开关
     * @return 校验结果
     */
    public static boolean checkCRC(byte[] bytes, boolean crcCheck) {
        if (!crcCheck) {
            //  跳过CRC校验
            return Boolean.TRUE;
        }
        ByteBuf byteBuf = CommonUtils.getByteBuf(bytes);
        // 获取数据长度和crc标示
        byteBuf.skipBytes(1);
        //  数据长度（包括头标识、数据头、数据体和尾标识）
        int msgLength = CommonUtils.bytes2int(new byte[]{bytes[1], bytes[2], bytes[3], bytes[4]});
        int crcLength = msgLength - 4;
        byte[] crcBody = new byte[crcLength];
        byteBuf.readBytes(crcBody);
        //  原始crc码
        short oldCRCcode = byteBuf.readShort();
        //  当前计算crc16校验码
        short currentCRCcode = getCRC16(crcBody);
        if (oldCRCcode == currentCRCcode) {
            return true;
        }
        log.error("crc校验失败;报文信息：{}", CommonUtils.PACKET_CACHE.get(Thread.currentThread().getName()));
        return false;
    }

    /**
     * 获取数据crc16码
     *
     * @param bytes 数据字节数组
     * @return crc16码
     */
    public static short getCRC16(byte[] bytes) {
        short crc = (short) 0xffff;
        short polynomial = 0x1021;
        for (int index = 0; index < bytes.length; index++) {
            byte b = bytes[index];
            for (int i = 0; i < 8; i++) {
                boolean bit = ((b >> (7 - i) & 1) == 1);
                boolean c15 = ((crc >> 15 & 1) == 1);
                crc <<= 1;
                if (c15 ^ bit) {
                    crc ^= polynomial;
                }
            }
        }
        crc &= 0xffff;
        //  byte[] short2Bytes = CommonUtils.short2Bytes(crc);
        return crc;
    }
}
