package com.kc.common.util.api;
import com.kc.common.consts.CommConst;
import com.kc.common.util.Md5Encrypt;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.text.ParseException;

public class AesUtil {

    private static String KEY = "abcdef0123456789"; // 长度必须是 16

    private static String IV = "abcdef0123456789";  // 长度必须是 16

    /**
     * 加密返回的数据转换成 String 类型
     * @param content 明文
     * @param key 秘钥
     * @param iv 初始化向量是16位长度的字符串
     * @return
     * @throws Exception
     */
    public static String encrypt(String content, String key, String iv) throws Exception {
        // 将返回的加密过的 byte[] 转换成Base64编码字符串  ！！！！很关键
        return base64ToString(AES_CBC_Encrypt(content.getBytes(), key.getBytes(), iv.getBytes()));
    }

    /**
     * 将解密返回的数据转换成 String 类型
     * @param content Base64编码的密文
     * @param key 秘钥
     * @param iv 初始化向量是16位长度的字符串
     * @return
     * @throws Exception
     */
    public static String decrypt(String content, String key, String iv) throws Exception {
        // stringToBase64() 将 Base64编码的字符串转换成 byte[]  !!!与base64ToString(）配套使用
        return new String(AES_CBC_Decrypt(stringToBase64(content), key.getBytes(), iv.getBytes()));
    }

    private static byte[] AES_CBC_Encrypt(byte[] content, byte[] keyBytes, byte[] iv){
        try {
            SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE,key, new IvParameterSpec(iv));
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (Exception e) {
            System.out.println("exception:"+e.toString());
        }
        return null;
    }

    private static byte[] AES_CBC_Decrypt(byte[] content, byte[] keyBytes, byte[] iv){
        try {
            SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE,key, new IvParameterSpec(iv));
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (Exception e) {
            System.out.println("exception:"+e.toString());
        }
        return null;
    }

    /**
     * 字符串装换成 Base64
     */

    public static byte[] stringToBase64(String key) throws Exception {
        return Base64.decodeBase64(key.getBytes());
    }

    /**
     * Base64装换成字符串
     */
    public static String base64ToString(byte[] key) throws Exception {
        return new Base64().encodeToString(key);
    }


    public static void main(String[] args) throws Exception{
        //System.out.println(AesUtil.encrypt("123456", "abcdef0123456789","abcdef0123456789"));
        System.out.println(AesUtil.encrypt("123456789", "abcdef0123456789","abcdef0123456789"));
        //System.out.println(GenerationUtil.generationUserId());
        //System.out.println(System.currentTimeMillis()/1000);
        //System.out.println(Md5Encrypt.md5("1234"));
    }


}
