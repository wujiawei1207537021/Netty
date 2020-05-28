package com.demo.netty.util;

/**
 * @Description
 * @Author 吴佳伟
 * @Date 2020-05-28 2:37 下午
 */
public class CommonUtil {
    /**
     * 大转小时，会截取低位，舍弃高位。
     * int 32位
     * byte 8位
     * 当int转为1个byte时，会截取int最低的8位
     * int to bytes
     *
     * @param n
     * @return
     */
    public static byte[] int2bytes(int n) {
        byte[] b = new byte[4];

        for (int i = 0; i < 4; i++) {
            b[i] = (byte) (n >> (24 - i * 8));
        }
        return b;
    }
}
