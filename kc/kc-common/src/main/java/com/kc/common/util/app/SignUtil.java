package com.kc.common.util.app;

import com.kc.common.exception.AppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by AX on 2018/6/16.
 */
public class SignUtil {


    private static final Logger logger = LoggerFactory.getLogger(SignUtil.class);

    public static String getSign(String secret, Map<String,String> params) {
        String result = null;
        try {
            if (secret == null || "".equals(secret) || params == null)
                throw new Exception( "secret or params is null or blank, please check");
            StringBuilder sb = new StringBuilder();
            Set<String> sortedKeys = new TreeSet<String>();
            sortedKeys.addAll(params.keySet());
            for (String key : sortedKeys){
                sb.append(key).append(params.get(key));
            }
            sb.append(secret);
            MessageDigest md = MessageDigest.getInstance("MD5");
            result = byteToHex(md.digest(sb.toString().getBytes("utf-8")));
        } catch (Exception e) {
            logger.error("getSign error:", e);
            throw new AppException("签名验证失败");
        }
        return result;
    }


    /**
     * @Title: byteToHex
     * @Description: TODO 字节转十六进制表示
     * @param b
     * @return String
     * @throws
     */
    public static String byteToHex(byte[] b) {
        StringBuffer sb = new StringBuffer();
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                sb.append("0").append(stmp);
            else
                sb.append(stmp);
        }
        return sb.toString().toUpperCase();
    }

}
