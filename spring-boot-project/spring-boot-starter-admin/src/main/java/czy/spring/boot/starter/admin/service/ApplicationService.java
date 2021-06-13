package czy.spring.boot.starter.admin.service;

import czy.spring.boot.starter.admin.constant.CacheConstant;
import czy.spring.boot.starter.admin.entity.Application;
import czy.spring.boot.starter.admin.entity.Application_;
import czy.spring.boot.starter.admin.ienum.AdminError;
import czy.spring.boot.starter.admin.repository.ApplicationRepository;
import czy.spring.boot.starter.common.exception.HttpException;
import czy.spring.boot.starter.common.service.BaseService;
import czy.spring.boot.starter.common.util.OrderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

@Service
@CacheConfig(cacheNames = CacheConstant.APPLICATION)
public class ApplicationService extends BaseService<Application> {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Override
    protected boolean cacheable() {
        return true;//启用缓存
    }

    @Override
    @Cacheable(key = "'map'")
    public Map<Long, Application> getCache() {
        return super.getCache();
    }

    @Override
    @CachePut(key = "'map'")
    public Map<Long, Application> updateCache() {
        return super.updateCache();
    }

    @Override
    public Predicate<Application> pageCachePredicate(Application payload) {
        /* 基本谓词 */
        Predicate<Application> root = super.pageCachePredicate(payload);
        if(StringUtils.hasText(payload.getName())){
            root = root.and(a->a.getName().contains(payload.getName()));
        }
        if(StringUtils.hasText(payload.getAppId())){
            root = root.and((a->a.getAppId().contains(payload.getAppId())));
        }
        if(Objects.nonNull(payload.getType())){
            root = root.and(a->a.getType().equals(payload.getType()));
        }
        if(Objects.nonNull(payload.getEnable())){
            root = root.and(a->a.getEnable().equals(payload.getEnable()));
        }
        return root;
    }

    @Override
    public Comparator<Application> pageCacheComparator(Sort sort) {
        Comparator<Application> root = super.pageCacheComparator(sort);
        List<Sort.Order> orders = sort.toList();
        /* 遍历所有排序条件 */
        for(Sort.Order order:orders){
            if(StringUtils.hasText(order.getProperty())){
                switch (order.getProperty()){
                    case Application_.NAME:
                        root.thenComparing(OrderUtil.getComparator(order,Application::getName));
                        break;
                    case Application_.APP_ID:
                        root.thenComparing(OrderUtil.getComparator(order,Application::getAppId));
                        break;
                    case Application_.TYPE:
                        root.thenComparing(OrderUtil.getComparator(order,Application::getType));
                        break;
                    case Application_.ENABLE:
                        root.thenComparing(OrderUtil.getComparator(order,Application::getEnable));
                        break;
                    default:
                        break;
                }
            }
        }
        return root;
    }

    @Override
    protected void createCheck(Application payload) {
        if(this.applicationRepository.countByName(payload.getName())>0){
            throw new HttpException(AdminError.APPLICATION_NAME_EXIST);
        }
    }

    @Override
    protected Application updateCheck(Application payload) {
        Application exist =  super.updateCheck(payload);
        /* 如果名称被修改，则检查名称是否存在 */
        if(!exist.getName().equals(payload.getName())){
            if(this.applicationRepository.countByName(payload.getName())>0){
                throw new HttpException(AdminError.APPLICATION_NAME_EXIST);
            }
        }
        return exist;
    }

    @Override
    public Predicate<Application> existCachePredicate(Application payload) {
        return a->a.getName().equals(payload.getName());//名称相等，则说明存在
    }

    /**
     * 通过appId查找对象
     * @param appId
     * @return
     */
    public Application getByAppId(String appId){
        Map<Long,Application> cache = this.service.getCache();
        return cache.values().stream().filter(a->a.getAppId().equals(appId)).findFirst().get();
    }
}
