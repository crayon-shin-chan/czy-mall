package czy.spring.boot.starter.auth.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import czy.spring.boot.starter.common.constant.GlobalConstant;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.support.NullValue;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.SessionRepository;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

import java.io.IOException;
import java.util.HashMap;

/* spring session 配置类 */
public class SpringSessionConfig{

    /**
     * 基于X-Auth-Token的session解析器
     * 全局只有一个sessionId解析器
     */
    @Bean
    public HttpSessionIdResolver httpSessionIdResolver() {
        return new HeaderHttpSessionIdResolver(GlobalConstant.SESSION_HEADER);
    }

    /**
     * 普通Session存储，单机方案
     */
    @Configuration
    @ConditionalOnProperty(prefix = "spring.session", name = "store-type", havingValue = "none", matchIfMissing = true)
    static class SimpleSessionConfiguration {

        /* 默认采用Map会话仓库，需要手动配置 */
        @Bean public SessionRepository sessionRepository(){
            return new MapSessionRepository(new HashMap<>());
        }

        /* 会话注册用于会话并发控制器 */
        @Bean
        public SessionRegistry sessionRegistry() {
            return new SessionRegistryImpl();
        }

    }

    /**
     * redis会话配置
     */
    @Configuration
    @ConditionalOnProperty(prefix = "spring.session", name = "store-type", havingValue = "redis", matchIfMissing = false)
    static class RedisSessionConfiguration{

        /* 会话索引仓库 */
        @Lazy
        @Autowired(required = false)
        private RedisIndexedSessionRepository sessionRepository;

        /* 会话注册用于会话并发控制器，集群会话时需要使用SpringSessionBackedSessionRegistry来检索会话信息 */
        @Bean
        public SessionRegistry sessionRegistry() {
            return new SpringSessionBackedSessionRegistry<>(sessionRepository);
        }

        /**
         * 后处理器，修改redis序列化器
         */
        @Configuration
        static class SpringSessionRepositoryConfig implements BeanPostProcessor ,ApplicationContextAware  {

            private ApplicationContext context;

            @Override
            public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
                this.context = applicationContext;
            }

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof RedisIndexedSessionRepository) {
                    RedisIndexedSessionRepository redisSessionRepository = (RedisIndexedSessionRepository) bean;
                    RedisTemplate redisTemplate = (RedisTemplate) redisSessionRepository.getSessionRedisOperations();
                    StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
                    GenericJackson2JsonRedisSerializer jsonRedisSerializer = new GenericJackson2JsonRedisSerializer(objectMapper());
                    redisTemplate.setKeySerializer(stringRedisSerializer);
                    redisTemplate.setValueSerializer(jsonRedisSerializer);
                    redisTemplate.setHashKeySerializer(stringRedisSerializer);
                    redisTemplate.setHashValueSerializer(jsonRedisSerializer);
                    redisTemplate.setStringSerializer(stringRedisSerializer);
                    redisTemplate.setDefaultSerializer(jsonRedisSerializer);
                    redisTemplate.setEnableDefaultSerializer(true);
                    redisSessionRepository.setDefaultSerializer(jsonRedisSerializer);
                }
                return bean;
            }
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

        static class NullValueSerializer extends StdSerializer<NullValue> {

            private static final long serialVersionUID = 1999052150548658808L;
            private final String classIdentifier;

            NullValueSerializer() {
                super(NullValue.class);
                this.classIdentifier = "@class";
            }

            @Override
            public void serialize(NullValue value, JsonGenerator jgen, SerializerProvider provider)
                    throws IOException {

                jgen.writeStartObject();
                jgen.writeStringField(classIdentifier, NullValue.class.getName());
                jgen.writeEndObject();
            }
        }
    }



}
