package com.demo.netty.protocol.encode;

import java.util.HashMap;
import java.util.Map;

/**
 * <p> 编码工厂类 </p>
 *
 * @author zhangqi
 * @date 2019-11-27 15:20
 */
public class EncoderFactory {
    private static Map<String,Encoder> ENCODER_FACTORY = new HashMap();
    static {
        ENCODER_FACTORY.put("Jt809LoginResponsePacket",new LoginResponseEncoder());
    }

    public static Encoder getEncoder(String packetClassName){
        return ENCODER_FACTORY.get(packetClassName);
    }
}
