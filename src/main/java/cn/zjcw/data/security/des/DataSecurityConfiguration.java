package cn.zjcw.data.security.des;


import cn.zjcw.data.security.des.decrypt.AutoDecrypt;
import cn.zjcw.data.security.des.decrypt.AutoDecryptAspect;
import cn.zjcw.data.security.des.encrypt.AutoEncryptAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnMissingBean(annotation = AutoDecrypt.class)
public class DataSecurityConfiguration {


    @Bean
    static AutoDecryptAspect autoDecryptAspect(){
        return new AutoDecryptAspect();
    }


    @Bean
    static AutoEncryptAspect autoEncryptAspect(){
        return new AutoEncryptAspect();
    }


}
