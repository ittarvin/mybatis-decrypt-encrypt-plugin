package cn.zjcw.data.security.factory;


import cn.zjcw.data.security.AutoDecrypt;
import cn.zjcw.data.security.AutoDecryptAspect;
import cn.zjcw.data.security.AutoEncrypt;
import cn.zjcw.data.security.AutoEncryptAspect;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnMissingBean(annotation = {
        AutoDecrypt.class,
        AutoEncrypt.class}
        )
public class AspectFactory {


    @Bean
    static AutoDecryptAspect autoDecryptAspect(){
        return new AutoDecryptAspect();
    }


    @Bean
    static AutoEncryptAspect autoEncryptAspect(){
        return new AutoEncryptAspect();
    }


}
