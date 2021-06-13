package czy.spring.boot.starter.auth.config.security;

import czy.spring.boot.starter.auth.constant.AuthorityConstant;
import czy.spring.boot.starter.auth.entity.Authority;
import czy.spring.boot.starter.common.annotion.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.method.AbstractFallbackMethodSecurityMetadataSource;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 基于{@link czy.spring.boot.starter.common.annotion.Permission}注解的方法安全元数据源
 */
@Slf4j
public class PermissionMethodSecurityMetadataSource extends AbstractFallbackMethodSecurityMetadataSource{

    /* 类上安全配置属性 */
    private Map<Class,List<ConfigAttribute>> classConfigAttributeCache = new HashMap<>();
    /* 方法上安全配置属性 */
    private Map<Method,List<ConfigAttribute>> methodConfigAttributeCache = new HashMap<>();
    /* 所有配置属性 */
    private Set<ConfigAttribute> allConfigAttribute = new HashSet<>();

    /* 权限实体 */
    private static Set<Authority> authorities = new HashSet<>();

    /**
     * 查找指定类上的配置属性
     * @param clazz
     * @return
     */
    protected Collection<ConfigAttribute> findAttributes(Class<?> clazz) {

        if(!this.classConfigAttributeCache.containsKey(clazz)){
            this.processAnnotations(clazz,null,clazz.getAnnotations());
        }

        return this.classConfigAttributeCache.get(clazz);
    }

    /**
     * 查找指定方法上的配置属性，启动时会对所有Bean进行回调，不需要自己调用
     * @param method
     * @param targetClass
     * @return
     */
    protected Collection<ConfigAttribute> findAttributes(Method method,
                                                         Class<?> targetClass) {
        if(!this.methodConfigAttributeCache.containsKey(method)){
            this.processAnnotations(targetClass,method,method.getAnnotations());
        }
        return this.methodConfigAttributeCache.get(method);
    }

    /**
     * 获取所有配置属性
     * @return
     */
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return allConfigAttribute;
    }

    private synchronized void processAnnotations(Class clazz,Method method,Annotation[] annotations) {

        /* 类与方法只处理一次 */
        if(Objects.nonNull(method)){
            if(this.methodConfigAttributeCache.containsKey(method)){
                return;
            }
        }else{
            if(this.classConfigAttributeCache.containsKey(clazz)){
                return;
            }
        }

        /* 没有注解则为空权限数组 */
        if (annotations == null || annotations.length == 0) {
            return;
        }
        List<ConfigAttribute> attributes = new ArrayList<>();

        /* 权限描述 */
        String description = null;

        for (Annotation a : annotations) {
            String permission = null;

            /* 如果是Permission注解，则使用指定权限名称 */
            if (a instanceof Permission) {
                /* 权限字符串，命名空间加上权限字符串 */
                permission = clazz.getName()+ AuthorityConstant.PERMISSION_JOIN+((Permission) a).value();
            }else if(a instanceof Read){
                permission = clazz.getName()+ AuthorityConstant.PERMISSION_JOIN+Read.class.getSimpleName();;
            }else if(a instanceof Create){
                permission = clazz.getName()+ AuthorityConstant.PERMISSION_JOIN+Create.class.getSimpleName();;
            }else if(a instanceof Update){
                permission = clazz.getName()+ AuthorityConstant.PERMISSION_JOIN+Update.class.getSimpleName();;
            }else if(a instanceof Delete){
                permission = clazz.getName()+ AuthorityConstant.PERMISSION_JOIN+Delete.class.getSimpleName();;
            }else if(a instanceof Import){
                permission = clazz.getName()+ AuthorityConstant.PERMISSION_JOIN+Import.class.getSimpleName();;
            }else if(a instanceof Export){
                permission = clazz.getName()+ AuthorityConstant.PERMISSION_JOIN+Export.class.getSimpleName();;
            }else if(a instanceof Metadata){
                permission = clazz.getName()+ AuthorityConstant.PERMISSION_JOIN+Metadata.class.getSimpleName();;
            }else if(a instanceof Api){
                description = ((Api) a).tags()[0];
                continue;
            }else if(a instanceof ApiOperation){
                description = ((ApiOperation) a).value();
                continue;
            }else{
                continue;
            }

            attributes.add(new SecurityConfig(permission));

        }

        if(Objects.nonNull(method)){
            this.methodConfigAttributeCache.put(method,attributes);
        }else{
            this.classConfigAttributeCache.put(clazz,attributes);
        }

        this.allConfigAttribute.addAll(attributes);

        /* 遍历本次的配置属性，转换为权限实体并且添加 */
        for(ConfigAttribute attribute:attributes){

            String[] arr = attribute.getAttribute().split(AuthorityConstant.PERMISSION_JOIN);

            this.authorities.add(new Authority().setNamespace(arr[0]).setName(arr[1]).setDescription(description).setEnable(true));

        }

    }

    /**
     * 获取所有扫描到的权限
     * @return
     */
    public static Set<Authority> getAuthorities() {
        return authorities;
    }
}
