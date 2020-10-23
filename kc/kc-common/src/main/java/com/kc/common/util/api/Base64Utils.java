package com.kc.common.util.api;

import java.io.IOException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
public class Base64Utils {

    public static String encode(byte[] bArray) {
        return new BASE64Encoder().encode(bArray);
    }
    
    public static byte[] decode(String encodeString) {
         byte[] bArray = new byte[0];;
        try {
            bArray = new BASE64Decoder().decodeBuffer(encodeString);
        } catch (IOException e) {
        }
         return bArray;
    }
}
