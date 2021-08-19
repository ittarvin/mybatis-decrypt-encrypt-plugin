package cn.zjcw.data.security.util;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;


public class AESUtil {
	
	
	
	private static AESUtil _me = new AESUtil() ;

	public static final String AESKEY_ALGORITHM = "AES";

	public static final String AESCIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
	

	public static AESUtil getInstance()
	{
		
		return _me;
	}


	/**
	 * 加密方法
	 * @param data 加密文本
	 * @param key key是32个字母组成的密要串
	 * @return
	 * @throws Exception
	 */
	public String encrypt2AES(String data, String key) throws Exception {
		Key k = toKey(key.getBytes());
		Cipher cipher = Cipher.getInstance(AESCIPHER_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, k);
		return parseByte2HexStr(cipher.doFinal(data.getBytes("UTF-8")));
	}


	/**
	 * 解秘方法
	 * @param data 解秘文本
	 * @param key key是32个字母组成的密要串
	 * @return
	 * @throws Exception
	 */
	public String decryptByAES(String data, String key) throws Exception {
		Key k = toKey(key.getBytes());
		Cipher cipher = Cipher.getInstance(AESCIPHER_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, k);
		return new String(cipher.doFinal(parseHexStr2Byte(data)),"UTF-8");
	}


	/**
	 * 生成密钥
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public Key toKey(byte[] key) throws Exception {
		SecretKey secretKey = new SecretKeySpec(key,AESKEY_ALGORITHM);
		return secretKey;
	}


	/**
	 * 字节数组转字符串
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte[] buf) {  
		StringBuffer sb = new StringBuffer();  
		for (int i = 0; i < buf.length; i++) {  
			String hex = Integer.toHexString(buf[i] & 0xFF);  
			if (hex.length() == 1) {  
				hex = '0' + hex;  
			}  
			sb.append(hex.toUpperCase());  
		}  
		return sb.toString();  
	}


	/**
	 * 16进制转换二进制
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {  
		if (hexStr.length() < 1) {
			return null;
		}
		byte[] result = new byte[hexStr.length()/2];  
		for (int i = 0;i< hexStr.length()/2; i++) {  
			int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);  
			int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);  
			result[i] = (byte) (high * 16 + low);  
		}  
		return result;  
	}



	   public static void main(String[] args) throws Exception {

		   AESUtil u= new AESUtil();

	       String AESKEY="617568c789ef9fe4aec8848866c29d2c";
	       String data="184D1F8B84AA0223265CFCC107DC47BD9F62D04B42642BA5FA5C82C5E9D14E3201EB69EDBA07DA24EB7B36D9F20CEA7A";


	       String content =  u.decryptByAES(data,AESKEY);

		   System.out.println(content);


		   String v = u.encrypt2AES(content,AESKEY);

		   System.out.println(v);

		   String v2 = u.encrypt2AES("江苏通湖物流园",AESKEY);

		   System.out.println(v2);

	    }
}
