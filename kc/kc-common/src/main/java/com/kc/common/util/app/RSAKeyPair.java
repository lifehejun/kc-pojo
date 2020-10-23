package com.kc.common.util.app;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * @ClassName: RSAKeyPair  
 * @Description: TODO RSA秘钥对
 * @author jason  
 * @date 2018-3-10
 */
public class RSAKeyPair implements java.io.Serializable {

	/**  
	 * @Fields field
	 */
	private static final long serialVersionUID = -56753219430472157L;
	
	/**
	 * @Fields RSA私钥
	 */
	private RSAPrivateKey privateKey;
	
	/**
	 * @Fields RSA公钥
	 */
    private RSAPublicKey publicKey;
    
    
    public RSAKeyPair(RSAPrivateKey privateKey, RSAPublicKey publicKey) {
    	this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

	public RSAPrivateKey getPrivateKey() {
		return privateKey;
	}

	public RSAPublicKey getPublicKey() {
		return publicKey;
	}

}
