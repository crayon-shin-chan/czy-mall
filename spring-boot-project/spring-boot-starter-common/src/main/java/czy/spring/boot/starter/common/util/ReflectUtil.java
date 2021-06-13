package czy.spring.boot.starter.common.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

@Slf4j
public class ReflectUtil {

    public static <T> T getProxyField(Object proxy,String fieldName,Class<T> clazz){
        try{
            Field f = proxy.getClass().getSuperclass().getDeclaredField("h");
            f.setAccessible(true);
            return (T)f.get(proxy);
        }catch (Exception e){
            log.error("获取代理对象字段错误：",e);
            return null;
        }

    }

}
