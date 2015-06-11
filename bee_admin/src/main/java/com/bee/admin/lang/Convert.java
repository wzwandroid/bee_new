/*
*/
package com.bee.admin.lang;

/**
 * Created by jiankangjin on 2014/5/10.
 */
public abstract class Convert {

    /**
     * 用于建立十六进制字符的输出的小写字符数组
     */
    public static final char[] lowerDigitals = "0123456789abcdef".toCharArray();

    /**
     * 用于建立十六进制字符的输出的大写字符数组
     */
    public static final char[] upperDigitals = "0123456789ABCDEF".toCharArray();

    public static String str2HexStr(String str) {
        return bytes2HexStr(str.getBytes());
    }

    public static String bytes2HexStr(byte[] data) {
        return bytes2HexStr(data, true);
    }

    public static String bytes2HexStr(byte[] data, boolean lower) {
        return encodeHex(data, lower ? lowerDigitals : upperDigitals);
    }

    private static String encodeHex(byte[] data, char[] digitals) {
        int len = data.length;
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < len; i++) {
            sb.append(digitals[(0xF0 & data[i]) >>> 4]);
            sb.append(digitals[0x0F & data[i]]);
        }
        return sb.toString();
    }

    /**
     * 将十六进制字符数组转换为字节数组
     *
     * @param hexStr 十六进制的字符串
     * @return
     * @throws IllegalArgumentException 如果源十六进制字符数组是一个奇怪的长度
     */
    public static String hexStr2OriginalStr(String hexStr) {
        char[] data = hexStr.toCharArray();
        int len = data.length;

        if ((len & 0x01) != 0) {
            throw new IllegalArgumentException("Odd number of characters.");
        }
        byte[] out = new byte[len >> 1];
        // two characters form the hex value.
        for (int i = 0, j = 0; j < len; i++) {
            int f = toDigit(data[j], j) << 4;
            j++;
            f = f | toDigit(data[j], j);
            j++;
            out[i] = (byte) (f & 0xFF);

        }
        return new String(out);

    }

    /**
     * 将十六进制字符转换成一个整数
     *
     * @param ch    十六进制char
     * @param index 十六进制字符在字符数组中的位置
     * @return 一个整数
     * @throws RuntimeException 当ch不是一个合法的十六进制字符时，抛出运行时异常
     */
    private static int toDigit(char ch, int index) {
        int digit = Character.digit(ch, 16);
        if (digit == -1) {
            throw new RuntimeException("Illegal hexadecimal character " + ch + " at index " + index);
        }
        return digit;
    }

}
