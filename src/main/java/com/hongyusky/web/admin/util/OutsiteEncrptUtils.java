package com.hongyusky.web.admin.util;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

public class OutsiteEncrptUtils {

	public static final Charset charset = Charset.forName("UTF-8");
	public static final String emptyString ="";
	public static final String apiKey ="siyu";
	public static final String secretKey="5759e0bf1b174247ae36c9082296b954";
	/**
	 * 客户端获取到加密的head 头信息
	 * @param apiKey appId
	 * @param secretKey 秘钥
	 * @return
	 * @throws Exception
	 */
    public static String genderHeaderAuthorization() throws Exception{
    	String  time = System.currentTimeMillis()+"";
    	String sign = getMD5(apiKey+secretKey+time);
    	StringBuilder jsonString = new  StringBuilder();
    	jsonString.append("{\"apiKey\":\"").append(apiKey).append("\",\"time\":\"").append(time).append("\",\"sign\":\"").append(sign).append("\"}");
    	return encodeBase64(jsonString.toString());
    }
    
    /**
     * 加密请求访问的reqInfo 参数信息
     * @param jsonStringReqInfo reqInfo参数(JsonString)
     * @param secretKey 秘钥
     * @return
     * @throws Exception
     */
    public static String encryptReqInfo(String jsonStringReqInfo)throws Exception{
    	if(jsonStringReqInfo==null ||emptyString.equals(jsonStringReqInfo)){
    		throw new IllegalArgumentException("jsonStringReqInfo is empty.");
    	}
    	return encrypt(encodeBase64(jsonStringReqInfo),secretKey);
    }
    
    /**
     * 解码返回的信息数据
     * @param msg
     * @param secretKey
     * @return
     */
   public static String decryptMsg(String msgData) throws Exception{
	   if(msgData==null|| emptyString.equals(msgData) ){
   			throw new IllegalArgumentException("msgData is empty.");
   		}
	   return decodeBase64(decrypt(msgData, secretKey));
   }
    
    
    
	// 加密
    private static String encodeBase64(String str) {
        String result = null;
        byte[] strByte = str.getBytes(charset);
        byte[] encodeBase64 = Base64.encodeBase64(strByte);
        if (encodeBase64 != null) {
            result = new String(encodeBase64,charset);
        }
        return result;
    }

    // 解密
    private static String decodeBase64(String str) {
        String result = null;
        byte[] strByte = str.getBytes(charset);
        byte[] encodeBase64 = Base64.decodeBase64(strByte);
        if (encodeBase64 != null) {
            result = new String(encodeBase64,charset);
        }
        return result;
    }
    
    /**
     * 对一段String生成MD5加密信息
     *
     * @param message 要加密的String
     * @return 生成的MD5信息
     */
    private static String getMD5(String message) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] data = md.digest(message.getBytes());
            return Hex.encodeHexString(data, true);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
	 * 加密s
	 * 
	 * @param datasource
	 *            byte[]
	 * @param password
	 *            String
	 * @return byte[]
	 */
	private static String encrypt(String datasource,String secretKey) throws Exception {
			SecureRandom random = new SecureRandom();
			DESKeySpec desKey = new DESKeySpec(secretKey.getBytes(charset));
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(desKey);
			// Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance("DES");
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
			// 现在，获取数据并加密
			// 正式执行加密操作
			return Hex.encodeHexString(cipher.doFinal(datasource.getBytes(charset)),true);
	}

	/**
	 * 解密
	 * 
	 * @param src
	 *            byte[]
	 * @param password
	 *            String
	 * @return byte[]
	 * @throws Exception
	 */
	private static String decrypt(String hexString,String secretKey) throws Exception {
		// DES算法要求有一个可信任的随机数源
		SecureRandom random = new SecureRandom();
		// 创建一个DESKeySpec对象
		DESKeySpec desKey = new DESKeySpec(secretKey.getBytes(charset));
		// 创建一个密匙工厂
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		// 将DESKeySpec对象转换成SecretKey对象
		SecretKey securekey = keyFactory.generateSecret(desKey);
		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance("DES");
		// 用密匙初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, securekey, random);
		byte[] decodeHex = Hex.decodeHex(hexString);
		// 真正开始解密操作
		return new String(cipher.doFinal(decodeHex),charset);
	}
	
}
