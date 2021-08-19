package cn.zjcw.data.security.sharding;



import cn.zjcw.data.security.util.AESUtil;
import org.apache.shardingsphere.encrypt.strategy.spi.Encryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Properties;


public class ShardingDecryptEncryptByAES implements Encryptor {

    private static final Logger logger = LoggerFactory.getLogger(ShardingDecryptEncryptByAES.class);

    private static final String AES_KEY = "aes.key.value";

    Properties properties = new Properties();

    @Override
    public void init() {

    }



    @Override
    public String encrypt(Object o) {

        AESUtil u = AESUtil.getInstance();

        try {

            String data = u.encrypt2AES(String.valueOf(o),this.properties.get("aes.key.value").toString());

            logger.debug("加密数据-原文：{}，密文：{}",String.valueOf(o),data);

            return data;

        }catch (Exception e){
            logger.info("AES-数据加密异常 {}",e);
        }

        return null;
    }

    @Override
    public String decrypt(String content) {

        AESUtil u = AESUtil.getInstance();

        try {

            String data = u.decryptByAES(content,this.properties.get("aes.key.value").toString());

            logger.debug("解秘数据-密文：{}，原文：{}",content,data);

            return data;

        }catch (Exception e){

            logger.info("AES-数据解密异常 {}",e);

        }

        return null;
    }

    @Override
    public String getType() {
        return "bxcxaes";
    }


    @Override
    public Properties getProperties() {
        return properties;
    }

    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
    }

}
