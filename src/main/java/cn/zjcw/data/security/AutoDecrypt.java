package cn.zjcw.data.security;

import java.lang.annotation.*;

/**
 * 解秘注解声明
 * @author 王鹏飞
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AutoDecrypt {
    /**
     * 解秘列
     * @return
     */
    String[] columns() default {};

    /**
     * 加密类型
     */
    CryptoType cryptoType();
}
