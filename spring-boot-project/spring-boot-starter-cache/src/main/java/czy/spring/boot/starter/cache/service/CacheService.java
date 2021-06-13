package czy.spring.boot.starter.cache.service;

import czy.spring.boot.starter.cache.ienum.CacheError;
import czy.spring.boot.starter.common.exception.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;

@Service
public class CacheService {

    @Autowired
    private CacheManager manager;

    /**
     * 获取所有缓存名称
     * @return
     */
    public Collection<String> getNames(){
        return this.manager.getCacheNames();
    }

    /**
     * 清理所有缓存
     */
    public void clearAll(){
        for(String name : getNames()){
            Cache cache = this.manager.getCache(name);
            if(Objects.isNull(cache)){
                throw new HttpException(CacheError.CACHE_NAME_NOT_EXISTS);
            }
            cache.clear();
        }
    }

    /**
     * 清理指定缓存
     * @param name
     */
    public void clear(String name){
        Cache cache = this.manager.getCache(name);
        if(Objects.isNull(cache)){
            throw new HttpException(CacheError.CACHE_NAME_NOT_EXISTS);
        }
        cache.clear();
    }



}
