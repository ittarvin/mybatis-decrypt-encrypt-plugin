package cn.zjcw.data.security.des.decrypt;

import cn.zjcw.data.security.des.LocalMetadata;
import cn.zjcw.data.security.des.util.DESedeUtils;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;


/**
 * MyBatis allows you to intercept calls to at certain points within the execution of a mapped statement.
 * By default, MyBatis allows plug-ins to intercept method calls of:
 *
 * Executor (update, query, flushStatements, commit, rollback, getTransaction, close, isClosed)
 * ParameterHandler (getParameterObject, setParameters)
 * ResultSetHandler (handleResultSets, handleOutputParameters)
 * StatementHandler (prepare, parameterize, batch, update, query)
 *
 *
 * The details of these classes methods can be discovered by looking at the full method signature of each,
 * and the source code which is available with each MyBatis release.
 * You should understand the behaviour of the method you’re overriding, assuming you’re doing something more than just monitoring calls.
 * If you attempt to modify or override the behaviour of a given method, you’re likely to break the core of MyBatis.
 * These are low level classes and methods, so use plug-ins with caution.
 *
 * Using plug-ins is pretty simple given the power they provide. Simply implement the Interceptor interface, being sure to specify the signatures you want to intercept.
 *
 */
@Intercepts({@Signature(type = ResultSetHandler.class,
        method = "handleResultSets",
        args = {Statement.class})})
public class AutoDecryptPlugin implements Interceptor {

    private static final Logger logger = LoggerFactory.getLogger(AutoDecryptAspect.class);

    private Properties properties = new Properties();

   /* private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
    private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
    private static final ReflectorFactory DEFAULT_REFLECTOR_FACTORY = new DefaultReflectorFactory();
*/
    // 数据库类型(默认为mysql)
    //private static String defaultDialect = "mysql";
    // 需要拦截的ID(正则匹配)
    //private static String defaultPageSqlId = ".*List.*";
    // 数据库类型(默认为mysql)
    //private static String dialect = "";

    /**
     * The plug-in above will intercept all calls to the "update" method on the Executor instance,
     * which is an internal object responsible for the low-level execution of mapped statements
     *
     * NOTE Overriding the Configuration Class
     *
     * In addition to modifying core MyBatis behaviour with plugins, you can also override the Configuration class entirely.
     * Simply extend it and override any methods inside, and pass it into the call to the SqlSessionFactoryBuilder.build(myConfig) method.
     * Again though, this could have a severe impact on the behaviour of MyBatis, so use caution.
     *
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // implement post-processing if needed
        // 该方法写入自己的逻辑
        if (invocation.getTarget() instanceof ResultSetHandler) {
            if(LocalMetadata.LOCAL_DECRYPT.get().keySet().size()  == 0){
                Object returnObject = invocation.proceed();
                return returnObject;
            }

            Object returnObject = invocation.proceed();
            if(returnObject instanceof List){

                ((List<?>) returnObject).forEach(obj->{
                    processField(obj);
                });

            }
            else if(returnObject instanceof Map){

                processMap(returnObject);

            }else{
                processField(returnObject);
            }

            return returnObject;
        }

        Object returnObject = invocation.proceed();

        return returnObject;
    }

    /**
     * 将MAP字段解秘
     * @param obj
     */
    private void processMap(Object obj){

        Map<String, List<String>> local_data = LocalMetadata.LOCAL_DECRYPT.get();

        Set<String> keys = local_data.keySet();

        keys.stream().forEach(runMethod ->{
            DESedeUtils deSedeUtils = new DESedeUtils();
            List<String> cols = local_data.get(runMethod);
            for (int i = 0; i < cols.size(); i++) {
                try {
                    Map param = (MapperMethod.ParamMap) obj;
                    if(param.containsKey(cols.get(i))){
                        String value = deSedeUtils.decrypt((String) param.get(cols.get(i)),(String)
                                properties.get("slatKey"),(String)properties.get("vectorKey"));
                        param.put(cols.get(i),value);
                    }
                }catch (Exception e){
                    logger.error("解秘秘插件执行拦截->方法名称 异常 {}",runMethod);
                }
            }
        });

    }

    /**
     *  将普通对象字段解秘
     * @param obj
     */
    private void processField(Object obj){

        Map<String, List<String>> local_data = LocalMetadata.LOCAL_DECRYPT.get();

        Set<String> keys = local_data.keySet();

        keys.stream().forEach(runMethod ->{
            DESedeUtils deSedeUtils = new DESedeUtils();
            List<String> cols = local_data.get(runMethod);
            for (int i = 0; i < cols.size(); i++) {
                try {
                    Field field = obj.getClass().getDeclaredField(cols.get(i));
                    if(field!=null){
                        field.setAccessible(true);
                        String value = deSedeUtils.decrypt(
                                (String) field.get(obj),(String)
                                properties.get("slatKey"),
                                (String)properties.get("vectorKey"));
                        field.set(obj,value);
                    }
                }catch (Exception e){
                    logger.error("解秘插件执行拦截->方法名称 异常 {}",runMethod);
                }
            }
        });

    }

    @Override
    public Object plugin(Object target) {
        // 当目标类是ResultSetHandler类型时，才包装目标类，否者直接返回目标本身,减少目标被代理的次数
        if (target instanceof ResultSetHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }


    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
    }


}
