package cn.zjcw.data.security.factory;



import cn.zjcw.data.security.AutoDecrypt;
import cn.zjcw.data.security.AutoEncrypt;
import cn.zjcw.data.security.CryptoType;
import cn.zjcw.data.security.decrypt.DecryptInterface;
import cn.zjcw.data.security.decrypt.impl.DecryptByAESInterfaceImpl;
import cn.zjcw.data.security.decrypt.impl.DecryptByDESInterfaceImpl;
import cn.zjcw.data.security.encrypt.EncryptInterface;
import cn.zjcw.data.security.encrypt.impl.EncryptByAESInterfaceImpl;
import cn.zjcw.data.security.encrypt.impl.EncryptByDESInterfaceImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 加密工作初始化类
 * @author 王鹏飞
 *
 */
@Configuration
@ConditionalOnMissingBean(annotation = {
        AutoDecrypt.class,
        AutoEncrypt.class}
)
public class CryptoFactory implements InitializingBean {


    /**
     * 解秘实现方法结合 key 为 CryptoType
     */
    private static Map<CryptoType,DecryptInterface> decryptInterfaceMap
            = new ConcurrentHashMap<>(5);


    /**
     * 加密秘实现方法结合 key 为 CryptoType
     */
    private static Map<CryptoType,EncryptInterface> encryptInterfaceMap
            = new ConcurrentHashMap<>(5);


    /**
     * 加密实现 AES 方式
     * @return
     */
    @Bean(name ="encryptByAES")
    static EncryptInterface encryptByAES(){
        return new EncryptByAESInterfaceImpl();
    }

    /**
     * 解秘实现 AES 方式
     * @return
     */
    @Bean(name ="decryptByAES")
    static DecryptInterface decryptByAES(){
        return new DecryptByAESInterfaceImpl();
    }

    /**
     * 加密方式 DES 方式
     * @return
     */
    @Bean(name ="encryptByDES")
    static EncryptInterface encryptByDES(){
        return new EncryptByDESInterfaceImpl();
    }

    /**
     * 解密方式 DES 方式
     * @return
     */
    @Bean(name ="decryptByDES")
    static DecryptInterface decryptByDES(){
        return new DecryptByDESInterfaceImpl();
    }


    @Autowired
    @Qualifier("encryptByAES")
    EncryptInterface encryptByAES;

    @Autowired
    @Qualifier("decryptByAES")
    DecryptInterface decryptByAES;

    @Autowired
    @Qualifier("encryptByDES")
    EncryptInterface encryptByDES;

    @Autowired
    @Qualifier("decryptByDES")
    DecryptInterface decryptByDES;


    public  static String encrypt(String content,
                                 CryptoType cryptoType){
        EncryptInterface encrypt = encryptInterfaceMap.get(cryptoType);
        return encrypt.encrypt(content);
    }


    public  static String decrypt(String content,
                                 CryptoType cryptoType){
        DecryptInterface decrypt = decryptInterfaceMap.get(cryptoType);
        return decrypt.decrypt(content);
    }


    /**
     * 初始化工程类成员
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {

        decryptInterfaceMap.putIfAbsent(CryptoType.AES,decryptByAES);
        encryptInterfaceMap.putIfAbsent(CryptoType.AES,encryptByAES);

        decryptInterfaceMap.putIfAbsent(CryptoType.DES3,decryptByDES);
        encryptInterfaceMap.putIfAbsent(CryptoType.DES3,encryptByDES);

    }


}
