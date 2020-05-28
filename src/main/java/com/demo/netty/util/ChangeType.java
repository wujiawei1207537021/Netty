package com.demo.netty.util;

import java.util.ArrayList;
import java.util.List;

public class ChangeType {

	/**
	 * 将int数值转换为占四个字节的byte数组，本方法适用于(低位在前，高位在后)的顺序。 和bytesToInt（）配套使用
	 * 
	 * @param value
	 *            要转换的int值
	 * @return byte数组
	 */
	public static List<Byte> intTo4Bytes(int value) {
		List<Byte> list = new ArrayList<Byte>(4);
		list.add(0, (byte) ((value >> 24) & 0xFF));
		list.add(1, (byte) ((value >> 16) & 0xFF));
		list.add(2, (byte) ((value >> 8) & 0xFF));
		list.add(3, (byte) (value & 0xFF));
		return list;
	}

	/**
	 * 将int数值转换为占四个字节的byte数组，本方法适用于(高位在后，低位在前)的顺序。 和bytesToInt2（）配套使用
	 */
	public static List<Byte> intTo2Bytes(int value) {

		List<Byte> list = new ArrayList<Byte>(2);
		list.add(0, (byte) ((value >> 8) & 0xFF));
		list.add(1, (byte) (value & 0xFF));
		return list;
	}

	/**
	 * byte数组中取int数值，本方法适用于(低位在后，高位在前)的顺序。和intToBytes2（）配套使用
	 */
	public static int bytesTo4Int(byte[] src, int offset) {
		int value;
		value = (int) (((src[offset] & 0xFF) << 24)
				| ((src[offset + 1] & 0xFF) << 16)
				| ((src[offset + 2] & 0xFF) << 8) 
				| (src[offset + 3] & 0xFF));
		return value;
	}

	/**
	 * byte数组中取int数值，本方法适用于(低位在后，高位在前)的顺序。和intToBytes2（）配套使用
	 */
	public static int bytesTo2Int(byte[] src, int offset) {
		int value;
		value = (int) (((src[offset] & 0xFF) << 8) 
				| (src[offset + 1] & 0xFF));
		return value;
	}
	
	  
	public static long bytesToLong(byte[] byteNum) {  
	    long num = 0;  
	    for (int ix = 0; ix < 8; ++ix) {  
	        num <<= 8;  
	        num |= (byteNum[ix] & 0xff);  
	    }  
	    return num;  
	}

	public static byte[] longToBytes(long value) {
		byte[] b = new byte[8];
		b[0] = (byte) ((value >> 56) & 0xFF);
		b[1] = (byte) ((value >> 48) & 0xFF);
		b[2] = (byte) ((value >> 40) & 0xFF);
		b[3] = (byte) ((value >> 32) & 0xFF);
		b[4] = (byte) ((value >> 24) & 0xFF);
		b[5] = (byte) ((value >> 16) & 0xFF);
		b[6] = (byte) ((value >> 8) & 0xFF);
		b[7] = (byte) (value & 0xFF);
		return b;
	}
}
