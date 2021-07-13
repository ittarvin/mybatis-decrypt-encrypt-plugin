package cn.zjcw.data.security.des;


import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class LocalMetadata {

    public static final ThreadLocal<Map<String,List<String>>> LOCAL_DECRYPT = new ThreadLocal<Map<String,List<String>>>(){
        @Override
        protected Map<String, List<String>> initialValue(){
            return new ConcurrentHashMap<>(10);
        }
    };

    public static final ThreadLocal<Map<String,List<String>>> LOCAL_ENCRYPT = new ThreadLocal<Map<String,List<String>>>(){
        @Override
        protected Map<String, List<String>> initialValue(){
            return new ConcurrentHashMap<>(10);
        }
    };

}
