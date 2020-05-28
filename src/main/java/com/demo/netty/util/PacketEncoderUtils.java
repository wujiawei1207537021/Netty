package com.demo.netty.util;

/**
 * <p> 数据包转义工具类 </p>
 *
 * @author zhangqi
 * @date 2019-11-27 15:20
 */
public class PacketEncoderUtils {

    /** 编码转义*/
    public static byte[] encoderEscape(byte[] dataBytesWithoutHeadAndEnd){
        //  数据包转16进制字符串
        String dataStr = PacketDecoderUtils.bytes2FullHexStr(dataBytesWithoutHeadAndEnd);
        //  头标识转义
        dataStr = dataStr.replaceAll("0x5b", "0x5a0x01");
        dataStr = dataStr.replaceAll("0x5a", "0x5a0x02");
        //  尾标识转义
        dataStr = dataStr.replaceAll("0x5d", "0x5e0x01");
        dataStr = dataStr.replaceAll("0x5e", "0x5e0x02");
        byte[] bytes = PacketDecoderUtils.fullHexStr2Bytes(dataStr);
        return bytes;
    }
}
