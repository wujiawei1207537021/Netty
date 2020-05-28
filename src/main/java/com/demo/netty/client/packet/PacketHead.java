package com.demo.netty.client.packet;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description 包头
 * @Author 吴佳伟
 * @Date 2020-05-28 5:06 下午
 */
@Data
public abstract class PacketHead implements MessageStructure{

    /**
     * 版本
     */
    private byte version = 0x10;
    /**
     * 行业代码
     */
    private byte industryCode = 0x01;
    /**
     * 外部平台的接入码。
     */
    private String accessCode;
    /**
     * 固定为 0x0006，与命令字组成平台消息 ID。
     */
    private byte agreementType = 0x0006;
    /**
     * 消息命令字，用于标识消息类型。与协议类型组成平台消息 ID。
     */
    private String cmd;
    /**
     * 消息发送方 ID
     */
    private String sendId;
    /**
     * 消息接收方 ID，无指定接收方时该值为 0。
     */
    private int receiveId = 0;
    /**
     * 消息流水号，从 0 开始循环累加。由产生数据系统生成
     */
    private int serialId;
    /**
     * 平台消息生成时间，格式详细如下：
     * <p>
     * [0]: YY 年
     * <p>
     * [1]: MM 月
     * <p>
     * [2]: DD 日
     * <p>
     * [3]: hh 时
     * <p>
     * [4]: mm 分
     * <p>
     * [5]: ss 秒
     * <p>
     * [6]: ms 毫秒百位和十位
     * <p>
     * [7]: ms0 毫秒个位，该字节低 4 位为 0。
     */
    private LocalDateTime createTime;
    /**
     * 包体是否加密：0 不加密，1 加密。
     */
    private byte isEncrypted;
    /**
     * 包体加密密钥，是否加密值为 1 时才有效，为 0 时密钥也为 0。
     */
    private String encryptionKey;

    public byte[] getBytes() {
        return new byte[]{};
    }
}
