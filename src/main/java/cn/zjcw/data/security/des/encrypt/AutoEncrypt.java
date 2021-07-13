package cn.zjcw.data.security.des.encrypt;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // 在运行时可见
@Target(ElementType.METHOD) // 注解可以用在方法上
public @interface AutoEncrypt {
    /**
     *  加密列
     * @return
     */
    String[] columns() default {};
}
