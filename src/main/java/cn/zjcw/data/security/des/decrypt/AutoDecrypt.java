package cn.zjcw.data.security.des.decrypt;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME) // 在运行时可见
@Target(ElementType.METHOD) // 注解可以用在方法上
public @interface AutoDecrypt {
    /**
     * 解秘列
     * @return
     */
    String[] columns() default {};
}
