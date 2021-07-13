package cn.zjcw.data.security.des.decrypt;
import cn.zjcw.data.security.des.LocalMetadata;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.util.*;


@Aspect
@Slf4j
public class AutoDecryptAspect {

    private static final Logger logger = LoggerFactory.getLogger(AutoDecryptAspect.class);


    @Pointcut("@annotation(cn.zjcw.data.security.des.decrypt.AutoDecrypt)")
    public void autoDecryptCut() {
    }


    @Around("autoDecryptCut()")
    public Object doAround(ProceedingJoinPoint pjp) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        logger.info("解秘插件执行拦截-> Thread [{}] 设置本地缓存",Thread.currentThread().getId());
        // 从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        String key = method.getDeclaringClass().getName()+"_"+method.getName();
        AutoDecrypt decrypt = method.getAnnotation(AutoDecrypt.class);
        if (!ObjectUtils.isEmpty(decrypt)
                &&  !LocalMetadata.LOCAL_DECRYPT.get().containsKey(key)) {

            logger.info("解秘插件执行拦截-> Thread [{}] {}",Thread.currentThread().getId(),key);

            String[] columns = decrypt.columns();
            List<String> cols= new ArrayList<>(columns.length);
            for (int i = 0; i < columns.length; i++) {
                cols.add(columns[i]);
            }
            LocalMetadata.LOCAL_DECRYPT.get().put(key,cols);
        }
        Object[] args = pjp.getArgs();
        try {
            return pjp.proceed(args);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        } finally {
            LocalMetadata.LOCAL_DECRYPT.get().remove(key);
            logger.info("解秘插件执行拦截-> 删除缓存 Thread [{}] {}",Thread.currentThread().getId(),key);
        }
    }
}
