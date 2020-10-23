package com.kc.common.util.app;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

/** 
 * @ClassName: RSAEncrypt  
 * @Description: TODO RSA加密
 * @author jason  
 * @date 2018-1-12
 */
public class RSAEncrypt {

	/**
	 * @Title: generateKeyPair  
	 * @Description: TODO 初始化密钥对
	 * @param keySize 秘钥长度
	 * @return  KeyPair 返回类型  
	 * @throws
	 */
	public static KeyPair generateKeyPair(int keySize) throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA",
				new BouncyCastleProvider());
		keyPairGen.initialize(keySize, new SecureRandom());
		KeyPair keyPair = keyPairGen.genKeyPair();
		return keyPair;
	}

	/**
	 * @Title: generateRSAPublicKey
	 * @Description: TODO 生成公钥
	 * @param modulus
	 * @param publicExponent
	 * @return RSAPublicKey 返回类型
	 * @throws
	 */
	private static RSAPublicKey generateRSAPublicKey(byte[] modulus,
			byte[] publicExponent) throws Exception {
		KeyFactory keyFac = KeyFactory.getInstance("RSA",
				new BouncyCastleProvider());
		RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(new BigInteger(
				modulus), new BigInteger(publicExponent));
		return (RSAPublicKey) keyFac.generatePublic(pubKeySpec);
	}

	/**
	 * @Title: generateRSAPrivateKey
	 * @Description: TODO 生成私钥
	 * @param modulus
	 * @param privateExponent
	 * @return RSAPrivateKey 返回类型
	 * @throws Exception
	 * @throws
	 */
	private static RSAPrivateKey generateRSAPrivateKey(byte[] modulus,
			byte[] privateExponent) throws Exception {
		KeyFactory keyFac = KeyFactory.getInstance("RSA",
				new BouncyCastleProvider());
		RSAPrivateKeySpec priKeySpec = new RSAPrivateKeySpec(new BigInteger(
				modulus), new BigInteger(privateExponent));
		return (RSAPrivateKey) keyFac.generatePrivate(priKeySpec);

	}

	/**
	 * @Title: encrypt
	 * @Description: 加密
	 * @param pubKey
	 * @param data
	 * @return byte[] 返回类型            
	 * @throws
	 */
	public static String encrypt(String pubKey, String data) throws Exception {		
		RSAPublicKey publicKey = base64StrToRSAPublicKey(pubKey);
		Cipher cipher = Cipher.getInstance("RSA",  new BouncyCastleProvider());  
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
        // 模长  
        int key_len = publicKey.getModulus().bitLength() / 8;  
        // 加密数据长度 <= 模长-11  
        String[] datas = splitString(data, key_len - 11);  
        String ciphertext = "";  
        //如果明文长度大于模长-11则要分组加密  
        for (String s : datas) {  
        	ciphertext += bcdToStr(cipher.doFinal(s.getBytes()));  
        }
        return ciphertext;
	}
		
	/**
	 * @Title: bcdToStr
	 * @Description: BCD转字符串
	 * @param bytes
	 * @return String 返回类型
	 * @throws
	 */
	public static String bcdToStr(byte[] bytes) {
		char temp[] = new char[bytes.length * 2], val;

		for (int i = 0; i < bytes.length; i++) {
			val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);
			temp[i * 2] = (char) (val > 9 ? val + 'A' - 10 : val + '0');

			val = (char) (bytes[i] & 0x0f);
			temp[i * 2 + 1] = (char) (val > 9 ? val + 'A' - 10 : val + '0');
		}
		return new String(temp);
	}

	/**
	 * @Title: splitString
	 * @Description: TODO 拆分字符串
	 * @param string
	 * @param len
	 * @return String[] 返回类型
	 * @throws
	 */
	public static String[] splitString(String string, int len) {
		int x = string.length() / len;
		int y = string.length() % len;
		int z = 0;
		if (y != 0) {
			z = 1;
		}
		String[] strings = new String[x + z];
		String str = "";
		for (int i = 0; i < x + z; i++) {
			if (i == x + z - 1 && y != 0) {
				str = string.substring(i * len, i * len + y);
			} else {
				str = string.substring(i * len, i * len + len);
			}
			strings[i] = str;
		}
		return strings;
	}
	
	

	/**
	 * @Title: decrypt
	 * @Description: TODO 解密
	 * @param privateKey
	 * @param data
	 * @return byte[] 返回类型
	 * @throws
	 */
	public static String decrypt(String priKey, String data)throws Exception {
		RSAPrivateKey privateKey = base64StrToRSAPrivateKey(priKey);
		Cipher cipher = Cipher.getInstance("RSA", new BouncyCastleProvider());  
        cipher.init(Cipher.DECRYPT_MODE, privateKey);  
        //模长  
        int key_len = privateKey.getModulus().bitLength() / 8;  
        byte[] bytes = data.getBytes();  
        byte[] bcd = ASCII_TO_BCD(bytes, bytes.length);   
        //如果密文长度大于模长则要分组解密  
        String writing = "";  
        byte[][] arrays = splitArray(bcd, key_len);  
        for(byte[] arr : arrays){  
        	writing += new String(cipher.doFinal(arr), "utf-8");  
        }  
        return writing;
	} 
	
	/**
	 * @Title: ASCII_TO_BCD  
	 * @Description: TODO ASCII码转BCD码
	 * @param ascii
	 * @param asc_len
	 * @return   
	 * byte[] 返回类型  
	 * @throws
	 */
	public static byte[] ASCII_TO_BCD(byte[] ascii, int asc_len) {
		byte[] bcd = new byte[asc_len / 2];
		int j = 0;
		for (int i = 0; i < (asc_len + 1) / 2; i++) {
			bcd[i] = ASC_TO_BCD(ascii[j++]);
			bcd[i] = (byte) (((j >= asc_len) ? 0x00 : ASC_TO_BCD(ascii[j++])) + (bcd[i] << 4));
		}
		return bcd;
	}

	/**
	 * 
	 * @Title: ASC_TO_BCD  
	 * @Description: TODO 这里用一句话描述这个方法的作用  
	 * @param asc
	 * @return   
	 * byte 返回类型  
	 * @throws
	 */
	public static byte ASC_TO_BCD(byte asc) {
		byte bcd;
		if ((asc >= '0') && (asc <= '9'))
			bcd = (byte) (asc - '0');
		else if ((asc >= 'A') && (asc <= 'F'))
			bcd = (byte) (asc - 'A' + 10);
		else if ((asc >= 'a') && (asc <= 'f'))
			bcd = (byte) (asc - 'a' + 10);
		else
			bcd = (byte) (asc - 48);
		return bcd;
	}

	/**
	 * @Title: splitArray  
	 * @Description: TODO 拆分数组  
	 * @param data
	 * @param len
	 * @return byte[][] 返回类型  
	 * @throws
	 */
	public static byte[][] splitArray(byte[] data, int len) {
		int x = data.length / len;
		int y = data.length % len;
		int z = 0;
		if (y != 0) {
			z = 1;
		}
		byte[][] arrays = new byte[x + z][];
		byte[] arr;
		for (int i = 0; i < x + z; i++) {
			arr = new byte[len];
			if (i == x + z - 1 && y != 0) {
				System.arraycopy(data, i * len, arr, 0, y);
			} else {
				System.arraycopy(data, i * len, arr, 0, len);
			}
			arrays[i] = arr;
		}
		return arrays;
	}

	/**
	 * @Title: getRSAPublicKey  
	 * @Description: TODO 获取公钥
	 * @param publicKey
	 * @return   
	 * RSAPublicKey 返回类型  
	 * @throws
	 */
	private static RSAPublicKey getRSAPublicKey(RSAPublicKey publicKey) throws Exception {
		// 获取公钥系数(字节数组形式)
		byte[] pubModBytes = publicKey.getModulus().toByteArray();
		// 返回公钥公用指数(字节数组形式)
		byte[] pubPubExpBytes = publicKey.getPublicExponent().toByteArray();
		// 生成公钥
		RSAPublicKey recoveryPubKey = generateRSAPublicKey(pubModBytes,
				pubPubExpBytes);
		return recoveryPubKey;
	}
	
    /**
     * @Title: getRSAPrivateKey  
     * @Description: TODO 获取私钥
     * @param privateKey
     * @return   
     * RSAPrivateKey 返回类型  
     * @throws
     */
	private static RSAPrivateKey getRSAPrivateKey(RSAPrivateKey privateKey) throws Exception {
		// 返回私钥系数(字节数组形式)
		byte[] priModBytes = privateKey.getModulus().toByteArray();
		// 返回私钥专用指数(字节数组形式)
		byte[] priPriExpBytes = privateKey.getPrivateExponent().toByteArray();
		// 生成私钥
		RSAPrivateKey recoveryPriKey = generateRSAPrivateKey(priModBytes,
				priPriExpBytes);
		return recoveryPriKey;
	}
	
	/**
	 * @Title: getRSAKeyPair  
	 * @Description: TODO 获取RSA秘钥对
	 * @param keySize  秘钥长度
	 * @return RSAKeyPair 返回类型  
	 * @throws
	 */
	public static RSAKeyPair getRSAKeyPair(int keySize) throws Exception {
		// 生成秘钥对
		KeyPair keyPair = generateKeyPair(keySize);
		// 获取公钥
		RSAPublicKey publicKey = getRSAPublicKey((RSAPublicKey) keyPair
				.getPublic());
		// 获取私钥
		RSAPrivateKey privateKey = getRSAPrivateKey((RSAPrivateKey) keyPair
				.getPrivate());
		return new RSAKeyPair(privateKey, publicKey);
	}
	
	/**
	 * @Title: toBase64Str  
	 * @Description: TODO 字节数组转base64字符串
	 * @param encoded
	 * @return  String 返回类型  
	 * @throws
	 */
	public static String toBase64Str(byte[] encoded) {
		return new String(Base64.encodeBase64(encoded));
	}
	
	/**
	 * @Title: base64StrToRSAPublicKey  
	 * @Description: TODO base64字符串转RSA公钥对象
	 * @param str base64字符串
	 * @return RSAPublicKey 返回类型  
	 * @throws
	 */
	public static RSAPublicKey base64StrToRSAPublicKey(String str)
			throws Exception {
		X509EncodedKeySpec pubX509 = new X509EncodedKeySpec(
				Base64.decodeBase64(str.getBytes()));
		KeyFactory keyf = KeyFactory.getInstance("RSA",
				new BouncyCastleProvider());
		return (RSAPublicKey) keyf.generatePublic(pubX509);
	}
	
	/**
	 * @Title: base64StrToRSAPrivateKey  
	 * @Description: TODO base64字符串转RSA私钥对象
	 * @param str
	 * @return  RSAPrivateKey 返回类型  
	 * @throws
	 */
	public static RSAPrivateKey base64StrToRSAPrivateKey(String str) throws Exception {
		PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
				Base64.decodeBase64(str.getBytes()));
		KeyFactory keyf = KeyFactory.getInstance("RSA",
				new BouncyCastleProvider());
		return (RSAPrivateKey) keyf.generatePrivate(priPKCS8);
	}
	
	/**
	 * @Title: jsDecryptBase64  
	 * @Description: TODO js加密base64密文解密
	 * @param privateKey
	 * @param ciphertext
	 * @return
	 * @throws Exception   
	 * String 返回类型  
	 * @throws
	 */
    public static String jsDecryptBase64(String privateKey, String ciphertext) throws Exception {
        return new String(jsDecrypt(privateKey, Base64.decodeBase64(ciphertext)));
    }
    
    /**
     * @Title: apiDecryptBase64  
     * @Description: TODO api解密base64密文 
     * @param privateKey
     * @param ciphertext
     * @throws Exception
     * @return String
     * @throws
     */
    public static String apiDecryptBase64(String privateKey, String ciphertext) throws Exception {
        return jsDecryptBase64(privateKey, ciphertext);
    }
     
    /**
     * @Title: jsDecrypt  
     * @Description: TODO js加密密文解密
     * @param privateKey
     * @param ciphertext
     * @return byte[] 返回类型  
     * @throws Exception   
     * @throws
     */
	private static byte[] jsDecrypt(String privateKey, byte[] ciphertext)
			throws Exception {
		Security.addProvider(new BouncyCastleProvider());
		Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding", "BC");
		cipher.init(Cipher.DECRYPT_MODE, base64StrToRSAPrivateKey(privateKey));
		return cipher.doFinal(ciphertext);
	}

	/**
	 * @Title: apiEncryptBase64
	 * @Description: TODO api rsa加密
	 * @param publicKey
	 * @param data
	 * @throws Exception
	 * @return String
	 * @throws
	 */
	public static String apiEncryptBase64(String publicKey, String data)
			throws Exception {
		Cipher ci = Cipher.getInstance("RSA/None/PKCS1Padding",
				new BouncyCastleProvider());
		ci.init(Cipher.ENCRYPT_MODE, base64StrToRSAPublicKey(publicKey));
		return toBase64Str(ci.doFinal(data.getBytes()));
	}
    
    
}
