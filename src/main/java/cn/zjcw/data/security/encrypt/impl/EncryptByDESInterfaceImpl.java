package cn.zjcw.data.security.encrypt.impl;


import cn.zjcw.data.security.encrypt.EncryptInterface;
import cn.zjcw.data.security.util.DESedeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;


public class EncryptByDESInterfaceImpl implements EncryptInterface {

    private static final Logger logger = LoggerFactory.getLogger(EncryptByDESInterfaceImpl.class);

    @Value("${slatKey:-1}")
    private String slatKey;

    @Value("${vectorKey:-1}")
    private String vectorKey;

    @Override
    public String encrypt(String content) {

        if("-1".equals(slatKey)
                || "-1".equals(vectorKey)){
            return null;
        }

        try {

            return DESedeUtils.encrypt(content,slatKey,vectorKey);

        }catch (Exception e){

            logger.info("DES-数据加密异常 {}",e);

        }
        return null;
    }
}
