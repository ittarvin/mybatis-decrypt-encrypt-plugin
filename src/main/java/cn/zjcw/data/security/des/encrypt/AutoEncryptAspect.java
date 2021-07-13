package cn.zjcw.data.security.des.encrypt;

import cn.zjcw.data.security.des.LocalMetadata;
import cn.zjcw.data.security.des.decrypt.AutoDecrypt;
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
import java.util.ArrayList;
import java.util.List;


@Aspect
@Slf4j
public class AutoEncryptAspect {

    private static final Logger logger = LoggerFactory.getLogger(AutoEncryptAspect.class);


    @Pointcut("@annotation(cn.zjcw.data.security.des.encrypt.AutoEncrypt)")
    public void autoEncryptCut() {
    }


    @Around("autoEncryptCut()")
    public Object doAround(ProceedingJoinPoint pjp) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        logger.info("加密秘插件执行拦截-> Thread [{}] 设置本地缓存",Thread.currentThread().getId());
        // 从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        String key = method.getDeclaringClass().getName()+"_"+method.getName();
        AutoEncrypt decrypt = method.getAnnotation(AutoEncrypt.class);
        if (!ObjectUtils.isEmpty(decrypt)
                &&  !LocalMetadata.LOCAL_ENCRYPT.get().containsKey(key)) {

            logger.info("加秘插件执行拦截-> Thread [{}] {}",Thread.currentThread().getId(),key);
            String[] columns = decrypt.columns();
            List<String> cols= new ArrayList<>(columns.length);
            for (int i = 0; i < columns.length; i++) {
                cols.add(columns[i]);
            }
            LocalMetadata.LOCAL_ENCRYPT.get().put(key,cols);
        }
        Object[] args = pjp.getArgs();
        try {
            return pjp.proceed(args);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }finally {
            LocalMetadata.LOCAL_ENCRYPT.get().remove(key);
            logger.info("加秘插件执行拦截-> 删除缓存 Thread [{}] {}",Thread.currentThread().getId(),key);
        }
    }
}
