package com.dongluhitec.card;

import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import com.google.common.base.Charsets;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class RsaEncryptUtil {

	// RSA最大加密明文大小
	private static final int MAX_ENCRYPT_BLOCK = 117;

	// RSA最大解密密文大小
	private static final int MAX_DECRYPT_BLOCK = 128;
	
	private static RsaEncryptUtil rsaUtil;

	private static String publicKey;
	private static String privateKey;

	private RsaEncryptUtil() {
		try {
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
			// 密钥位数
			keyPairGen.initialize(1024);
			KeyPair keyPair = keyPairGen.generateKeyPair();
			// 公钥
			publicKey = getKeyString((RSAPublicKey) keyPair.getPublic());
			// 私钥
			privateKey = getKeyString((RSAPrivateKey) keyPair.getPrivate());
		} catch (Exception e) {
			throw new EncryptException("初始化加密解密工具失败");
		}
	}

	public static RsaEncryptUtil getInstance() {
		if (rsaUtil == null) {
			rsaUtil = new RsaEncryptUtil();
		}
		return rsaUtil;
	}

	/**
	 * 根据提供公钥进行加密
	 * 
	 * @param plainText
	 * @param publicKey
	 * @return
	 */
	public String encode(String plainText, String publicKey) {
		try {
			byte[] bytes = plainText.getBytes("ISO-8859-1");
			byte[] encode = encode(bytes,publicKey);
			
			String string = new String(encode,"ISO-8859-1");
			return string;
		} catch (Exception e) {
			throw new EncryptException("加密失败",e);
		}
	}
	
	public byte[] encode(byte[] data, String publicKey) {
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
			
			int inputLen = data.length;
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        int offSet = 0;
	        byte[] cache;
	        int i = 0;
	        // 对数据分段加密
	        while (inputLen - offSet > 0) {
	            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
	                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
	            } else {
	                cache = cipher.doFinal(data, offSet, inputLen - offSet);
	            }
	            out.write(cache, 0, cache.length);
	            i++;
	            offSet = i * MAX_ENCRYPT_BLOCK;
	        }
	        byte[] encryptedData = out.toByteArray();
	        out.close();
	        return encryptedData;
		} catch (Exception e) {
			throw new EncryptException("加密失败",e);
		}
	}

	/**
	 * 解密
	 * 
	 * @param ciphertext
	 * @return
	 */
	public String decode(String data) {
		try {
			byte[] bytes = data.getBytes("ISO-8859-1");
			byte[] decode = decode(bytes);
			return new String(decode,"ISO-8859-1");
		} catch (Exception e) {
			throw new EncryptException("解密失败",e);
		}
	}
	
	public byte[] decode(byte[] encryptedData) {
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, getPrivateKey(privateKey));

			int inputLen = encryptedData.length;
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        int offSet = 0;
	        byte[] cache;
	        int i = 0;
	        // 对数据分段解密
	        while (inputLen - offSet > 0) {
	            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
	                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
	            } else {
	                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
	            }
	            out.write(cache, 0, cache.length);
	            i++;
	            offSet = i * MAX_DECRYPT_BLOCK;
	        }
	        byte[] decryptedData = out.toByteArray();
	        out.close();
	        
	        return decryptedData;
		} catch (Exception e) {
			throw new EncryptException("解密失败",e);
		}
	}

	/**
	 * 获取公钥
	 * 
	 * @return
	 */
	public String getPublicKey() {
		return publicKey;
	}

	/**
	 * 得到公钥
	 * 
	 * @param key
	 *            密钥字符串（经过base64编码）
	 * @throws Exception
	 */
	private static PublicKey getPublicKey(String key) throws Exception {
		byte[] keyBytes;
		keyBytes = (new BASE64Decoder()).decodeBuffer(key);

		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}

	/**
	 * 得到私钥
	 * 
	 * @param key
	 *            密钥字符串（经过base64编码）
	 * @throws Exception
	 */
	private static PrivateKey getPrivateKey(String key) throws Exception {
		byte[] keyBytes;
		keyBytes = (new BASE64Decoder()).decodeBuffer(key);

		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}

	/**
	 * 得到密钥字符串（经过base64编码）
	 * 
	 * @return
	 */
	private static String getKeyString(Key key) throws Exception {
		byte[] keyBytes = key.getEncoded();
		String s = (new BASE64Encoder()).encode(keyBytes);
		return s;
	}
	
	/** 
     * @功能: BCD码转为10进制串(阿拉伯数据) 
     * @参数: BCD码 
     * @结果: 10进制串 
     */  
    public static String bcd2Str(byte[] bytes) {  
        StringBuffer temp = new StringBuffer(bytes.length * 2);  
        for (int i = 0; i < bytes.length; i++) {  
            temp.append((byte) ((bytes[i] & 0xf0) >>> 4));  
            temp.append((byte) (bytes[i] & 0x0f));  
        }  
        return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp  
                .toString().substring(1) : temp.toString();  
    }  
  
    /** 
     * @功能: 10进制串转为BCD码 
     * @参数: 10进制串 
     * @结果: BCD码 
     */  
    public static byte[] str2Bcd(String asc) {  
        int len = asc.length();  
        int mod = len % 2;  
        if (mod != 0) {  
            asc = "0" + asc;  
            len = asc.length();  
        }  
        byte abt[] = new byte[len];  
        if (len >= 2) {  
            len = len / 2;  
        }  
        byte bbt[] = new byte[len];  
        abt = asc.getBytes();  
        int j, k;  
        for (int p = 0; p < asc.length() / 2; p++) {  
            if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {  
                j = abt[2 * p] - '0';  
            } else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {  
                j = abt[2 * p] - 'a' + 0x0a;  
            } else {  
                j = abt[2 * p] - 'A' + 0x0a;  
            }  
            if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {  
                k = abt[2 * p + 1] - '0';  
            } else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {  
                k = abt[2 * p + 1] - 'a' + 0x0a;  
            } else {  
                k = abt[2 * p + 1] - 'A' + 0x0a;  
            }  
            int a = (j << 4) + k;  
            byte b = (byte) a;  
            bbt[p] = b;  
        }  
        return bbt;  
    }  

}
