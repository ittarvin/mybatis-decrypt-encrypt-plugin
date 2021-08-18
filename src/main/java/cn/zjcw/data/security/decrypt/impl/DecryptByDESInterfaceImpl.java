package cn.zjcw.data.security.decrypt.impl;


import cn.zjcw.data.security.AutoEncryptAspect;
import cn.zjcw.data.security.decrypt.DecryptInterface;
import cn.zjcw.data.security.util.DESedeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;


public class DecryptByDESInterfaceImpl implements DecryptInterface {

    private static final Logger logger = LoggerFactory.getLogger(DecryptByDESInterfaceImpl.class);


    @Value("${slatKey:-1}")
    private String slatKey;

    @Value("${vectorKey:-1}")
    private String vectorKey;

    @Override
    public String decrypt(String content) {

        if("-1".equals(slatKey)
                || "-1".equals(vectorKey)){
            return null;
        }

        try {
            return DESedeUtils.decrypt(content,slatKey,vectorKey);
        }catch (Exception e){
            logger.info("DES-数据解密异常 {}",e);
        }
        return null;
    }

}
