package czy.spring.boot.starter.common.service;

import czy.spring.boot.starter.common.exception.HttpException;
import czy.spring.boot.starter.common.ienum.CommonError;
import czy.spring.boot.starter.jpa.entity.BaseEntity;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 服务层抽象基类
 * @param <T>：实体类泛型
 */
public abstract class BaseService<T extends BaseEntity> implements IService<T> {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    protected BaseService<T> service;

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    protected JpaRepositoryImplementation<T,Long> repository;

    /**
     * 是否开启缓存，如果覆盖此方法返回true，需要在类上添加{@link org.springframework.cache.annotation.CacheConfig}注解
     * 指定缓存的名称
     * @return：是否使用数据缓存，默认false
     */
    protected boolean cacheable(){
        return false;
    }

    /**
     * 获取数据缓存
     * @return：返回主键与实体的映射
     */
    public Map<Long,T> getCache(){
        List<T> list = this.repository.findAll();
        /* 转换为Map，一个ID映射一个实体对象 */
        return list.stream().collect(Collectors.toMap(BaseEntity::getId, a -> a,(k1, k2)->k1,TreeMap::new));
    }

    /**
     * 更新数据缓存
     * @return：返回主键与实体的映射，执行方法，返回结果会更新缓存
     */
    public Map<Long,T> updateCache(){
        List<T> list = this.repository.findAll();
        /* 转换为Map，一个ID映射一个实体对象 */
        return list.stream().collect(Collectors.toMap(BaseEntity::getId, a -> a,(k1, k2)->k1,TreeMap::new));
    }

    /**
     * 获取缓存分页谓词。使用查询数据对分页进行过滤，子类可以自己实现
     * @param payload：查询数据
     * @return
     */
    public Predicate<T> pageCachePredicate(T payload){
        return Objects::nonNull;//默认不为空即可
    }

    /**
     * 缓存分页排序比较器
     * @param sort：排序对象
     * @return：比较器
     */
    public Comparator<T> pageCacheComparator(Sort sort){
        return new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                return 0;
            }
        };
    }

    /**
     * 缓存分页查找
     * @param payload：查询属性
     * @param pageable：分页属性
     * @return：分页结果
     */
    public Page<T> pageCache(T payload, Pageable pageable){
        /* 获取总数 */
        long total = this.service.getCache().values().stream().filter(pageCachePredicate(payload)).count();
        /* 获取分页数据 */
        List<T> list = this.service.getCache().values().stream()
                .filter(pageCachePredicate(payload))//查询过滤
                .sorted(pageCacheComparator(pageable.getSort()))//查询排序
                .skip(pageable.getOffset()).limit(pageable.getPageSize())//查询分页
                .collect(Collectors.toList());
        return new PageImpl<T>(list, pageable, total);
    }

    /**
     * 创建前的检查
     * @param payload
     */
    protected void createCheck(T payload){

    };

    /**
     * 更新前的检查，ID是否存在已经检查过
     * @param payload
     * @return 返回已存在的数据
     */
    protected T updateCheck(T payload){
        Optional<T> exist = this.repository.findById(payload.getId());
        /* 不存在抛出异常 */
        if(!exist.isPresent()){
            throw new HttpException(CommonError.ID_NOT_EXISTS);
        }
        return exist.get();
    }

    /**
     * 删除前的检查
     * @param payload
     * @return 返回已存在的数据
     */
    protected T deleteCheck(T payload){
        Optional<T> exist = this.repository.findById(payload.getId());
        /* 不存在抛出异常 */
        if(!exist.isPresent()){
            throw new HttpException(CommonError.ID_NOT_EXISTS);
        }
        return exist.get();
    }

    /**
     * 批量删除前的检查
     * @param ids：主键ID列表
     */
    protected List<T> batchDeleteCheck(List<Long> ids){
        List<T> list = this.repository.findAllById(ids);
        /* 不存在抛出异常 */
        if(list.size()<ids.size()){
            throw new HttpException(CommonError.ID_NOT_EXISTS);
        }
        return list;
    }

    @Override
    public T create(T payload) {
        /* 创建时ID为null */
        payload.setId(null);
        this.createCheck(payload);//创建前检查
        T result =  this.repository.save(payload);
        /* 启用缓存则更新缓存 */
        if(cacheable()){
            this.updateCache();
        }
        return result;
    }

    @Override
    public T update(T payload) {
        this.updateCheck(payload);//更新前检查
        T result =  this.repository.save(payload);
        /* 启用缓存则更新缓存 */
        if(cacheable()){
            this.updateCache();
        }
        return result;
    }

    @Override
    public void delete(T payload) {
        this.deleteCheck(payload);//删除前检查
        this.repository.delete(payload);
        /* 启用缓存则更新缓存 */
        if(cacheable()){
            this.updateCache();
        }
    }

    @Override
    public void batchDelete(List<Long> ids) {
        this.batchDeleteCheck(ids);//批量更新前检查
        /** 暂时使用遍历删除 */
        for(Long id:ids){
            this.repository.deleteById(id);
        }
        /* 启用缓存则更新缓存 */
        if(cacheable()){
            this.updateCache();
        }
    }

    /**
     * 存在的示例匹配
     * @return
     */
    public ExampleMatcher existExampleMatcher(){
        return ExampleMatcher.matching();
    }

    /**
     * 缓存中存在查找谓词
     * @return
     */
    public Predicate<T> existCachePredicate(T payload){
        return new Predicate<T>() {
            @Override
            public boolean test(T t) {
                return false;
            }
        };
    }

    @Override
    public Boolean exist(T payload) {
        if(cacheable()){
            return this.service.getCache().values().stream().allMatch(this.existCachePredicate(payload));
        }
        Example<T> example = Example.of(payload,existExampleMatcher());
        return this.repository.exists(example);
    }

    @Override
    public T getById(Long id) {
        /* 启用缓存则更新缓存 */
        if(cacheable()){
            T data = this.service.getCache().get(id);
            if(Objects.isNull(data)){
                throw new HttpException(CommonError.ID_NOT_EXISTS);
            }
        }
        return this.repository.findById(id).get();
    }

    /**
     * 分页查询格式，可以返回CriteriaAPI的谓词，即查询条件
     * 作为分页的查询条件
     * @param payload
     * @return
     */
    public Specification<T> pageSpecification(T payload){
        return new Specification<T>() {
            /* 返回查询谓词 */
            @Override
            public javax.persistence.criteria.Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return null;
            }
        };
    }

    @Override
    public Page<T> page(T payload, Pageable pageable) {
        /* 如果启用缓存，则在缓存中分页查找 */
        if(cacheable()){
            return pageCache(payload,pageable);
        }
        return this.repository.findAll(pageSpecification(payload),pageable);
    }



}
