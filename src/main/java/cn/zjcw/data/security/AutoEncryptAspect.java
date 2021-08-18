package cn.zjcw.data.security;

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


    @Pointcut("@annotation(cn.zjcw.data.security.AutoEncrypt)")
    public void autoEncryptCut() {
    }


    @Around("autoEncryptCut()")
    public Object doAround(ProceedingJoinPoint pjp) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

        logger.info("加密秘插件执行拦截-> Thread [{}] 设置本地缓存",Thread.currentThread().getId());

        // 从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) pjp.getSignature();

        Method method = signature.getMethod();



        AutoEncrypt encrypt = method.getAnnotation(AutoEncrypt.class);

        if (!ObjectUtils.isEmpty(encrypt)
                &&  !LocalMetadata.LOCAL_ENCRYPT.get().containsKey(LocalMetadata._columns)) {

            logger.info("加秘插件执行拦截-> Thread [{}] {}",Thread.currentThread().getId(),LocalMetadata._columns);

            String[] columns = encrypt.columns();

            CryptoType cryptoType = encrypt.cryptoType();

            if(cryptoType == null
                    || columns.length == 0){

                throw new RuntimeException("加密注解配置异常，请检查 columns cryptoType 配置");

            }

            List<String> cols= new ArrayList<>(columns.length);

            for (int i = 0; i < columns.length; i++) {
                cols.add(columns[i]);
            }


            LocalMetadata.LOCAL_ENCRYPT.get().put(LocalMetadata._crypto,cryptoType);

            LocalMetadata.LOCAL_ENCRYPT.get().put(LocalMetadata._columns,cols);

        }

        Object[] args = pjp.getArgs();

        try {

            return pjp.proceed(args);

        } catch (Throwable e) {

            throw new RuntimeException(e);

        }finally {

            LocalMetadata.LOCAL_ENCRYPT.get().remove(LocalMetadata._columns);

            logger.info("加秘插件执行拦截-> 删除缓存 Thread [{}] {}",Thread.currentThread().getId(),LocalMetadata._columns);

            LocalMetadata.LOCAL_ENCRYPT.get().remove(LocalMetadata._crypto);

            logger.info("加秘插件执行拦截-> 删除缓存 Thread [{}] {}",Thread.currentThread().getId(),LocalMetadata._crypto);
        }
    }
}
