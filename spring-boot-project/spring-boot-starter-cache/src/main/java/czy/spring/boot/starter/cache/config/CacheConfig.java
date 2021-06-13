package czy.spring.boot.starter.cache.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.security.jackson2.SecurityJackson2Modules;

/**
 * 缓存配置
 */
public class CacheConfig {

    /**
     * redis缓存管理器构建定制化器
     * 条件配置，只有当spring缓存类型为redis时才创建
     * @return
     */
    @Bean
    @ConditionalOnProperty(prefix = "spring.cache", name = "type", havingValue = "redis", matchIfMissing = false)
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer(){
        return new RedisCacheManagerBuilderCustomizer() {
            @Override
            public void customize(RedisCacheManager.RedisCacheManagerBuilder builder) {
                ObjectMapper mapper = objectMapper();
                GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(mapper);
                /* 定制化Redis缓存配置，设置值序列化器，使用jackson序列化 */
                RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer));
                builder.cacheDefaults(cacheConfiguration)//设置缓存配置
                        .transactionAware();//事务同步
            }
        };
    }

    /**
     * redis序列化专用{@link ObjectMapper}
     * 1.需要不触发hibernate5懒加载特性，注册hibernate5的module
     * 2.需要序列化类型信息，反序列化时不需要指定类型即可
     */
    private static ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        /* 重新注册Hibernate5模块，替换persistent下面的集合实现，避免反序列化错误 */
        mapper.registerModule(new Hibernate5Module().enable(Hibernate5Module.Feature.REPLACE_PERSISTENT_COLLECTIONS));
        /* spring security 支持*/
        SecurityJackson2Modules.getModules(SecurityJackson2Modules.class.getClassLoader()).forEach(mapper::registerModule);
        /* 序列化类型信息支持 */
        GenericJackson2JsonRedisSerializer.registerNullValueSerializer(mapper,null);
        //mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        return mapper;
    }


}
