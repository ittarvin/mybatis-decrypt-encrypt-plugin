package cn.zjcw.data.security;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 与线程绑定的数据缓存，在 *Aspect （AutoDecryptAspect）方法中赋值
 *                   在  *Plugin （AutoDecryptPlugin） 取值使用
 *
 * @author 王鹏飞
 * @date 20210818
 */
public class LocalMetadata {

    /**
     * 加解密算法，缓存KEY，用于 区别线程相关缓存冲的算法
     *  value 值 cn.zjcw.data.security.CryptoType
     *
     */
    public static final String _crypto ="_crypto";

    /**
     * 需要加解密的列
     */
    public static final String _columns ="_columns";

    /**
     *  此方法于本地线程绑定，
     *
     *  解秘密算法缓存数据存储，key：_crypto 、_columns
     *
     *   _crypto :CryptoType
     *   _columns : List<String>
     *
     */
    public static final ThreadLocal<Map<String,Object>> LOCAL_DECRYPT = new ThreadLocal<Map<String,Object>>(){
        @Override
        protected Map<String, Object> initialValue(){
            return new ConcurrentHashMap<>(200);
        }
    };

    /**
     *
     * 此方法于本地线程绑定，
     *
     *  加密算法缓存数据存储，key：_crypto 、_columns
     *
     *   _crypto :CryptoType
     *   _columns : List<String>
     *
     */
    public static final ThreadLocal<Map<String,Object>> LOCAL_ENCRYPT = new ThreadLocal<Map<String,Object>>(){
        @Override
        protected Map<String, Object> initialValue(){
            return new ConcurrentHashMap<>(200);
        }
    };

}
