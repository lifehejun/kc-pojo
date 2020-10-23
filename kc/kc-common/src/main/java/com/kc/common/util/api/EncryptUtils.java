package com.kc.common.util.api;

import java.security.MessageDigest;
import java.util.Base64;
import java.util.Base64.Encoder;

public class EncryptUtils {



	public final static String encryptPassword(String password) {
		String md5 = MD5(password + "!@#k3&^*");
		return md5;
	}

	public final static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] btInput = s.getBytes("UTF-8");
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			mdInst.update(btInput);
			byte[] md = mdInst.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public final static String encryptPHP(String password, String key) {
		try {
			if(key == null) key = "";
			Encoder base64Encoder = Base64.getEncoder();
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] sign = md5.digest(key.getBytes("UTF-8"));
			key = Bytes2HexString(sign).toLowerCase();
			String data = base64Encoder.encodeToString(password.getBytes("UTF-8"));
			int x = 0;
			int len = data.length();
			int l = key.length();
			String _char = "";
			for(int i = 0; i < len; i ++) {
				if (x == l)
					x = 0;
				_char += key.substring(x, x+1);
				x ++;
			}
			String str = "0000000000";
			byte[] b = new byte[str.length() + len];
			int index = 0;
			for(int i=0; i<str.length(); i++) {
				b[index++] = (byte) str.charAt(i);
			}
			for(int i = 0; i < len; i ++) {
				//str +=  new String(new byte[]{(byte) (data.charAt(i) +  _char.charAt(i) % 256)}) ;
				b[index++] = (byte) (data.charAt(i) +  _char.charAt(i) % 256);
			}
			//base64Encoder.en
			String result = base64Encoder.encodeToString(b);
			result = result.replace('+', '-').replace('/', '_').replace("=", "");
			return result;
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 将byte数组转成十六进制的字符串
	 * 
	 * @param b
	 *            byte[]
	 * @return String
	 */
	public static String Bytes2HexString(byte[] b) {
		StringBuffer ret = new StringBuffer(b.length);
		String hex = "";
		for (int i = 0; i < b.length; i++) {
			hex = Integer.toHexString(b[i] & 0xFF);

			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			ret.append(hex.toUpperCase());
		}
		return ret.toString();
	}
	
	public static void main(String[] args) {
		String password = "Hj@1zM$I$hx8F";
		System.out.println(MD5(password + "!@#k3&^*"));
		//System.out.println(encryptPHP("123456", "KRUjtJoY"));
	}
}
