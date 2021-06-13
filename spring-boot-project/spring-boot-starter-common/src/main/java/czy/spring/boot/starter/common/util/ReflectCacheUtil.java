package czy.spring.boot.starter.common.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 反射缓存工具
 */
@Slf4j
public class ReflectCacheUtil {

    private static final ConcurrentHashMap <Class,ConcurrentHashMap<String, Field>> CACHE = new ConcurrentHashMap<>();

    public static Field get(Class target,String property){

        ConcurrentHashMap<String,Field> fieldCache = CACHE.get(target);

        if(Objects.isNull(fieldCache)){
            fieldCache = new ConcurrentHashMap<>();
            CACHE.put(target,fieldCache);
        }

        Field field = fieldCache.get(property);

        if(Objects.isNull(field)){
            try{
                /* 递归扫描父类获取字段 */
                while(!target.equals(Object.class) && Objects.isNull(field)){
                    field = target.getDeclaredField(property);
                    target = target.getSuperclass();
                }
            }catch (Exception ex){
                log.error("反射获取字段异常：",ex);
            }

            if(Objects.nonNull(field)){
                fieldCache.put(property,field);

            }
        }
        return field;
    }


}
