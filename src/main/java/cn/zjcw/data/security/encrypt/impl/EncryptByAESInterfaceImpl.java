package cn.zjcw.data.security.encrypt.impl;


import cn.zjcw.data.security.decrypt.impl.DecryptByAESInterfaceImpl;
import cn.zjcw.data.security.encrypt.EncryptInterface;
import cn.zjcw.data.security.util.AESUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public class EncryptByAESInterfaceImpl implements EncryptInterface {

    private static final Logger logger = LoggerFactory.getLogger(EncryptByAESInterfaceImpl.class);

    @Value("${aesKey:-1}")
    private String aesKey;


    @Override
    public String encrypt(String content) {

        AESUtil u = AESUtil.getInstance();

        try {

            return  u.encrypt2AES(content,aesKey);

        }catch (Exception e){
            logger.info("AES-数据加密异常 {}",e);
        }

        return null;
    }
}
