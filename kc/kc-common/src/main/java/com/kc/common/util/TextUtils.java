package com.kc.common.util;

import com.kc.common.util.api.AES256;
import com.kc.common.util.api.Base64Utils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.*;

/**
 * Created by mark on 2017/10/28
 */
public class TextUtils {

    public static String jm(String content, String path)
            throws UnsupportedEncodingException, CertificateException,
            FileNotFoundException, NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        byte[] msg = content.getBytes("GBK"); // 待加解密的消息

        // 用证书的公钥加密
        CertificateFactory cff = CertificateFactory.getInstance("X.509");
        FileInputStream fis1 = new FileInputStream(path + "tomcat.cer"); // 证书文件
        Certificate cf = cff.generateCertificate(fis1);
        PublicKey pk1 = cf.getPublicKey(); // 得到证书文件携带的公钥
        Cipher c1 = Cipher.getInstance("RSA/ECB/PKCS1Padding"); // 定义算法：RSA
        byte[] dataReturn = null;
        c1.init(Cipher.PUBLIC_KEY, pk1);
        // StringBuilder sb = new StringBuilder();
        for (int i = 0; i < msg.length; i += 100) {
            byte[] doFinal = c1.doFinal(ArrayUtils.subarray(msg, i, i + 100));

            // sb.append(new String(doFinal,"gbk"));
            dataReturn = ArrayUtils.addAll(dataReturn, doFinal);
        }

        BASE64Encoder encoder = new BASE64Encoder();

        String afjmText = encoder.encode(dataReturn);

        return afjmText;
    }

    public static String buildMysign(Map sArray, String key) {
        String prestr = createLinkString(sArray); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        prestr = prestr + key; // 把拼接后的字符串再与安全校验码直接连接起来
        String mysign = Md5Encrypt.md5(prestr);
        return mysign;
    }




    public static String signRuYiPayJiaMi(Map sArray,String key,String signType) {
        String prestr = "merId="+sArray.get("merId")+"&merOrdId="+sArray.get("merOrdId")+"&merOrdAmt="+sArray.get("merOrdAmt")+"&payType="+sArray.get("payType")+"&bankCode="+sArray.get("bankCode")+"&remark="+sArray.get("remark")+"&returnUrl="+sArray.get("returnUrl")+"&notifyUrl="+sArray.get("notifyUrl")+"&signType="+sArray.get("signType");
        //String prestr = createLinkStringNoSort(sArray); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        prestr = prestr + "&merKey="+key; // 把拼接后的字符串再与安全校验码直接连接起来
        System.out.println("==" + prestr + "============");
        String charset ="UTF-8";
        String mysign = Md5Encrypt.md5Charset(prestr,charset);
        return mysign;
    }


    public static String signLuckyParams(Map sArray,String key) {
        String prestr = createLinkString(sArray);
        prestr = prestr + "&key="+key; // 把拼接后的字符串再与安全校验码直接连接起来
        System.out.println("==" + prestr + "============");
        String charset ="UTF-8";
        String mysign = Md5Encrypt.md5Charset(prestr,charset);
        return mysign;
    }

    public static String aesLuckyParams(Map sArray,String key) {
        String prestr = createLinkString(sArray);
        System.out.println("==" + prestr + "============");
        String myAesStr = Base64Utils.encode(AES256.encode(prestr, key.getBytes()));
        return myAesStr;
    }

    public static String signFumeNotifyJiaMi(Map sArray,String key,String signType) {
        String prestr = "merchantId="+sArray.get("merchantId")+"&merOrderNo="+sArray.get("merOrderNo")+"&orderAmount="+sArray.get("orderAmount")+"&realAmount="+sArray.get("realAmount")+"&channelCode="+sArray.get("channelCode")+"&payPlatform="+sArray.get("payPlatform")+"&orderTime="+sArray.get("orderTime")+"&orderPayTime="+sArray.get("orderPayTime")+"&signType="+sArray.get("signType");
        prestr = prestr + "&merKey="+key; // 把拼接后的字符串再与安全校验码直接连接起来
        System.out.println("==" + prestr + "============");
        String charset ="UTF-8";
        String mysign = Md5Encrypt.md5Charset(prestr,charset);
        return mysign;
    }

    public static String signRuYiPayJieMi(Map sArray,String key,String signType) {
        String prestr = "merId="+sArray.get("merId")+"&merOrdId="+sArray.get("merOrdId")+"&merOrdAmt="+sArray.get("merOrdAmt")+"&sysOrdId="+sArray.get("sysOrdId")+"&tradeStatus="+sArray.get("tradeStatus")+"&remark="+sArray.get("remark")+"&signType="+sArray.get("signType");
        //String prestr = createLinkStringNoSort(sArray); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        prestr = prestr + "&merKey="+key; // 把拼接后的字符串再与安全校验码直接连接起来
        System.out.println("==" + prestr + "============");
        String charset ="UTF-8";
        String mysign = Md5Encrypt.md5Charset(prestr,charset);
        return mysign;
    }




    public static String signEasyPay(Map sArray,String secret,String signType) {
        String prestr = createLinkString(sArray); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        prestr = prestr + "&secret="+secret; // 把拼接后的字符串再与安全校验码直接连接起来
        System.out.println("==" + prestr + "============");
        String charset ="utf-8";
        String mysign = Md5Encrypt.md5Charset(prestr,charset);
        //String mysign = MD5Utils.zhonbgaoMD5sign(prestr,charset);
        //String mysign = MD5.MD5(prestr,charset);
        return mysign;
    }




    /**
     * 功能：除去数组中的空值和签名参数
     *
     * @param sArray
     *            签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    @SuppressWarnings("rawtypes")
    public static Map mapFilter(Map sArray) {
        List keys = new ArrayList(sArray.keySet());
        Map sArrayNew = new HashMap();
        for (int i = 0; i < keys.size(); i++) {
            String key = (String) keys.get(i);
            String value = (String) sArray.get(key);
			/*if(value.equals("") || value == null || key.equalsIgnoreCase("sign") || key.equalsIgnoreCase("sign_type")){//新增notifyid不参加签名,只做标识用
				continue;
			}*/
            sArrayNew.put(key, value);
        }
        return sArrayNew;
    }

    /**
     * 功能：把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params
     *            需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map params) {
        List keys = new ArrayList(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = (String) keys.get(i);
            String value = String.valueOf(params.get(key));

            if(StringUtils.isEmpty(value))continue; //value值为空则不参与签名
            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }


    public static String createLinkStringNoFilter(Map params) {
        List keys = new ArrayList(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = (String) keys.get(i);
            String value = String.valueOf(params.get(key));

            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }

    public static String getSplitStr(String inStr) {
        if (inStr.contains("-")) {
            String outStr = inStr.split("-")[1];
            return outStr;
        }
        return inStr;
    }


    public static String createLinkStringNoSort(Map params) {
        List keys = new ArrayList(params.keySet());

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = (String) keys.get(i);
            String value = String.valueOf(params.get(key));

            if(StringUtils.isEmpty(value))continue; //value值为空则不参与签名
            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }


}
