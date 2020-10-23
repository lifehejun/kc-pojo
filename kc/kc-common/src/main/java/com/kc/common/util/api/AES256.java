package com.kc.common.util.api;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

// BouncyCastleProvider属于第三方，请自行百度
import org.bouncycastle.jce.provider.BouncyCastleProvider;


public class AES256 {  
  
    public static boolean initialized = false;  
      
    public static final String ALGORITHM = "AES/ECB/PKCS7Padding";  
      
    /** 
     * @param   str  要被加密的字符串
     * @param   key  加/解密要用的长度为32的字节数组（256位）密钥
     * @return byte[]  加密后的字节数组 
     */  
    public static byte[] encode(String str, byte[] key){  
        initialize();  
        byte[] result = null;  
        try{  
            Cipher cipher = Cipher.getInstance(ALGORITHM, "BC");  
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES"); //生成加密解密需要的Key  
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);  
            result = cipher.doFinal(str.getBytes("UTF-8"));  
        }catch(Exception e){  
            e.printStackTrace();  
        }  
        return result;  
    }  
      
    /** 
     * @param   bytes  要被解密的字节数组
     * @param   key    加/解密要用的长度为32的字节数组（256位）密钥
     * @return String  解密后的字符串 
     */  
    public static String decode(byte[] bytes, byte[] key){  
        initialize();  
        String result = null;  
        try{  
            Cipher cipher = Cipher.getInstance(ALGORITHM, "BC");  
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES"); //生成加密解密需要的Key  
            cipher.init(Cipher.DECRYPT_MODE, keySpec);  
            byte[] decoded = cipher.doFinal(bytes);  
            result = new String(decoded, "UTF-8");  
        }catch(Exception e){  
            e.printStackTrace();  
        }  
        return result;  
    }  
      
    public static void initialize(){  
        if (initialized) return;  
        Security.addProvider(new BouncyCastleProvider());  
        initialized = true;  
    }
    
    
    /**
     * 调用示例
     * @param args [description]
     */
	public static void main(String[] args) {

		String key = "01234567890123456789012345678901";
		String str = "testStr";
		byte[] v = encode(str, key.getBytes());

		String strEncode = Base64Utils.encode(v);
		System.out.println("strEncode:" + strEncode);

		String strDecode = decode(Base64Utils.decode(strEncode), key.getBytes());
		System.out.println("strDecode:" + strDecode);
	}
}  
