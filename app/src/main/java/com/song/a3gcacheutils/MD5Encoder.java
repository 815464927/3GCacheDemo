package com.song.a3gcacheutils;

import java.security.MessageDigest;

/**
 * Created by song on 2018/6/30.
 * Email：815464927@qq.com
 */
class MD5Encoder {

    /**
     * encode
     * @param string source string
     * @return encode string
     * @throws Exception e
     */
    public static String encode(String string) throws Exception{
        String MD5 = "MD5";
        String UTF_8 = "UTF-8";
        byte[] hash = MessageDigest.getInstance(MD5).digest(string.getBytes(UTF_8));
        StringBuffer hex = new StringBuffer(hash.length * 2);
        for(byte b:hash){
            if((b & 0xFF)<0x10){
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }
}
