package cn.zjcw.data.security.decrypt.impl;


import cn.zjcw.data.security.decrypt.DecryptInterface;
import cn.zjcw.data.security.util.AESUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;


public class DecryptByAESInterfaceImpl implements DecryptInterface {

    private static final Logger logger = LoggerFactory.getLogger(DecryptByAESInterfaceImpl.class);

    @Value("${aesKey:-1}")
    private String aesKey;


    @Override
    public String decrypt(String content) {

        AESUtil u = AESUtil.getInstance();

        try {

            return  u.decryptByAES(content,aesKey);

        }catch (Exception e){

            logger.info("AES-数据解密异常 {}",e);

        }

        return null;
    }

}
