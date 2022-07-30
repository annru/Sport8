package com.sh.sport.utils;

import com.orhanobut.logger.Logger;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    private static final String[] strDigits = new String[]{
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "A", "B", "C", "D", "E", "F"};

    public static String GetMD5Code2(String paramString) {
        Logger.i("GetMD5Code2=====" + paramString);
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update((paramString.getBytes(StandardCharsets.UTF_8)));
            byte[] rawBit = md.digest();
            String outputMD5 = " ";
            for (int i = 0; i < 16; i++) {
                outputMD5 = outputMD5 + strDigits[rawBit[i] >>> 4 & 0x0f];
                outputMD5 = outputMD5 + strDigits[rawBit[i] & 0x0f];
            }
            return outputMD5.trim();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("计算MD5值发生错误");
            e.printStackTrace();
        }
        return paramString;
    }

    public static String GetMD5Code(String paramString) {
//        paramString = "METHOD=ANDROID&NONCE=ANDROID&BIZ=APISETORDERFIELD&DATE=1659456000&ORDERLIST=[{\"STARTTIME\":18,\"ENDTIME\":\"19\",\"FIELDID\":\"513\"}]&STADIUMID=443&KEY=YDBPROJECT20180308";
        Logger.i("GetMD5Code=====" + paramString);
        String str = null;
        try {
            return byteToString(MessageDigest.getInstance("MD5").digest(paramString.getBytes()));

        } catch (Exception e) {
            str = paramString;
        }

        return str;
    }

    private static String byteToArrayString(int paramByte) {
        int i = 0;
        int j = paramByte;
        paramByte = j;
        if (j < 0)
            i = j + 256;
        j = i / 16;
        return strDigits[j] + strDigits[i % 16];
    }

    private static String byteToString(byte[] paramArrayOfbyte) {
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b = 0; b < paramArrayOfbyte.length; b++)
            stringBuffer.append(byteToArrayString(paramArrayOfbyte[b]));
        return stringBuffer.toString();
    }
}
