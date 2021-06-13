package czy.spring.boot.starter.common.provider;

import czy.spring.boot.starter.common.interfaces.Provider;

/**
 * 提供swagger api key的供应商
 * 基础设置Bean，在BeanPostProcessor之前初始化
 */
public interface ApiKeyProvider extends Provider<String> {
}
