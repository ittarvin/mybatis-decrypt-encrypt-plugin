package cn.zjcw.data.security;

import java.lang.annotation.*;

/**
 * 加密注解声明
 * @author 王鹏飞
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AutoEncrypt {

    /**
     *
     * 加密列
     * @autor 王鹏飞
     * @return
     */
    String[] columns() default {};

    /**
     * 加密类型
     * @autor 王鹏飞
     */
    CryptoType cryptoType();
}
