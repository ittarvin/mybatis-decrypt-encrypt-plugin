package cn.zjcw.data.security.des.util;


import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;

/**
 **
 * 参考：https://baijiahao.baidu.com/s?id=1650238413514992134&wfr=spider&for=pc
 * 源码参考 ： https://www.cnblogs.com/caizhaokai/p/10944667.html
 * jdk8 参考文档 ： https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html
 * DES 加密算法
 * @date 20200812
 * @author 王鹏飞
 */
public class DESedeUtils {


    /**
     * 加密的盐
     */
/*    @Value("${des.slat.key}")
    private  String slatKey;*/

 /*   @Value("${des.vector.key}")
    private  String vectorKey;*/

    private final String pattern = "DESede/CBC/PKCS5Padding";

    /**
     *
     * 加密算法	密匙长度	向量长度
     * AES	    16	     16
     * DES	     8	      8
     * DES3	    24	      8
     *
     * content: 加密内容
     * slatKey: 加密的盐
     * vectorKey: 加密的向量
     */
    public  String encrypt(String content, String slatKey,String vectorKey) throws Exception {

        //新建Cipher对象时需要传入一个参数"DES/CBC/PKCS5Padding"
        Cipher cipher = Cipher.getInstance(pattern);

        //ey ：密匙，使用传入的盐构造出一个密匙，可以使用SecretKeySpec、KeyGenerator和KeyPairGenerator创建密匙，其中
        //* SecretKeySpec和KeyGenerator支持AES，DES，DESede三种加密算法创建密匙
        //* KeyPairGenerator支持RSA加密算法创建密匙
        //(3)params ：使用CBC模式时必须传入该参数，该项目使用IvParameterSpec创建iv 对象

        SecretKey secretKey = new SecretKeySpec(slatKey.getBytes(), "DESede");
        IvParameterSpec iv = new IvParameterSpec(vectorKey.getBytes());

        //cipher对象使用之前还需要初始化，共三个参数("加密模式或者解密模式","密匙","向量")
        //Cipher.ENCRYPT_MODE(加密模式)和 Cipher.DECRYPT_MODE(解密模式)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

        //调用数据转换：cipher.doFinal(content)，其中content是一个byte数组
        byte[] encrypted = cipher.doFinal(content.getBytes());
        return Base64.encodeBase64String(encrypted);
    }



    /**
     * content: 解密内容(base64编码格式)
     * slatKey: 加密时使用的盐
     * vectorKey: 加密时使用的向量
     */
    public String decrypt(String base64Content,String slatKey,String vectorKey) throws Exception{

        try {

            Cipher cipher = Cipher.getInstance(pattern);
            SecretKey secretKey = new SecretKeySpec(slatKey.getBytes(), "DESede");
            IvParameterSpec iv = new IvParameterSpec(vectorKey.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

            byte[] content = Base64.decodeBase64(base64Content);
            byte[] encrypted = cipher.doFinal(content);

            return new String(encrypted);

        }catch (InvalidKeyException e){

            return decryptDes(base64Content,slatKey,vectorKey);
        }catch (BadPaddingException e){

            return decryptDes(base64Content,slatKey,vectorKey);
        }

    }




    /**
     *
     * content: 解密内容(base64编码格式)
     * slatKey: 加密时使用的盐
     * vectorKey: 加密时使用的向量
     */
    public String decryptDes(String base64Content,String slatKey,String vectorKey) throws Exception {
        Cipher cipher = Cipher.getInstance(pattern);
        String key = slatKey.substring(0,8);
        String slatKeyNew = key + key + key ;
        SecretKey secretKey = new SecretKeySpec(slatKeyNew.getBytes(), "DESede");
        IvParameterSpec iv = new IvParameterSpec(vectorKey.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
        byte[] content = Base64.decodeBase64(base64Content);
        byte[] encrypted = cipher.doFinal(content);
        return new String(encrypted);
    }




}
