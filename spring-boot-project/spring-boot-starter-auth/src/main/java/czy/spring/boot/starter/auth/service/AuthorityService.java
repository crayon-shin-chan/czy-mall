package czy.spring.boot.starter.auth.service;

import czy.spring.boot.starter.auth.AuthProperties;
import czy.spring.boot.starter.auth.comparator.AuthorityComparator;
import czy.spring.boot.starter.auth.constant.CacheConstant;
import czy.spring.boot.starter.auth.entity.Authority;
import czy.spring.boot.starter.auth.entity.Authority_;
import czy.spring.boot.starter.auth.entity.Role;
import czy.spring.boot.starter.auth.ienum.AuthError;
import czy.spring.boot.starter.auth.repository.AuthorityRepository;
import czy.spring.boot.starter.common.exception.HttpException;
import czy.spring.boot.starter.common.ienum.CommonError;
import czy.spring.boot.starter.common.service.BaseService;
import czy.spring.boot.starter.common.util.OrderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Service
@CacheConfig(cacheNames = CacheConstant.AUTHORITH)
public class AuthorityService extends BaseService<Authority> {

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthProperties authProperties;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    protected boolean cacheable() {
        return true;
    }

    @Override
    @Cacheable(key = "'map'")
    public Map<Long, Authority> getCache() {
        return super.getCache();
    }

    @Override
    @CachePut(key = "'map'")
    public Map<Long, Authority> updateCache() {
        this.roleService.updateCache();//权限启用/禁用之后，需要重新加载角色缓存
        return super.updateCache();
    }

    @Override
    public Predicate<Authority> pageCachePredicate(Authority payload) {
        /* 基本谓词 */
        Predicate<Authority> root = super.pageCachePredicate(payload);
        if(StringUtils.hasText(payload.getNamespace())){
            root = root.and((a->a.getNamespace().contains(payload.getNamespace())));
        }
        if(StringUtils.hasText(payload.getName())){
            root = root.and(a->a.getName().contains(payload.getName()));
        }
        if(Objects.nonNull(payload.getDescription())){
            root = root.and(a->a.getDescription().equals(payload.getDescription()));
        }
        if(Objects.nonNull(payload.getEnable())){
            root = root.and(a->a.getEnable().equals(payload.getEnable()));
        }
        return root;
    }

    @Override
    public Comparator<Authority> pageCacheComparator(Sort sort) {
        Comparator<Authority> root = super.pageCacheComparator(sort);
        List<Sort.Order> orders = sort.toList();
        /* 遍历所有排序条件 */
        for(Sort.Order order:orders){
            if(StringUtils.hasText(order.getProperty())){
                switch (order.getProperty()){
                    case Authority_.NAME:
                        root.thenComparing(OrderUtil.getComparator(order,Authority::getName));
                        break;
                    case Authority_.NAMESPACE:
                        root.thenComparing(OrderUtil.getComparator(order,Authority::getNamespace));
                        break;
                    case Authority_.DESCRIPTION:
                        root.thenComparing(OrderUtil.getComparator(order,Authority::getDescription));
                        break;
                    case Authority_.ENABLE:
                        root.thenComparing(OrderUtil.getComparator(order,Authority::getEnable));
                        break;
                    default:
                        break;
                }
            }
        }
        return root;
    }

    /* 不支持的操作，权限不支持增删改查 */
    @Override
    protected void createCheck(Authority authority) {
        throw new HttpException(CommonError.NOT_SUPPORT);
    }

    @Override
    protected Authority updateCheck(Authority payload) {
        throw new HttpException(CommonError.NOT_SUPPORT);
    }

    @Override
    protected Authority deleteCheck(Authority payload) {
        throw new HttpException(CommonError.NOT_SUPPORT);
    }

    @Override
    public void batchDelete(List<Long> ids) {
        throw new HttpException(CommonError.NOT_SUPPORT);
    }

    /* 获取命名空间与权限的映射 */
    public Map<String,List<Authority>> getAll(){
        Map<Long,Authority> authorities = this.getCache();
        /* 根据命名空间进行分组 */
        Map<String,List<Authority>> map = authorities.values().stream().collect(Collectors.groupingBy(Authority::getNamespace));
        /* 列表排序 */
        map.values().forEach(list -> list.sort(AuthorityComparator.INSTANCE));
        return map;
    }

    /**
     * 启用权限
     * @param id
     */
    public void enable(Long id){
        Optional<Authority> optional = this.repository.findById(id);
        if(!optional.isPresent()){
            throw new HttpException(AuthError.AUTHORITY_ID_NOT_EXIST);
        }
        Authority authority = optional.get();
        if(authority.getEnable()){
            throw new HttpException(AuthError.AUTHORITY_ALREADY_ENABLED);
        }
        authority.setEnable(true);
        this.repository.save(authority);
        this.updateCache();
    }

    /**
     * 批量启用权限
     * @param ids：权限列表
     */
    public void batchEnable(List<Long> ids){
        List<Authority> list = this.repository.findAllById(ids);
        if(list.size()<ids.size()){
            throw new HttpException(AuthError.AUTHORITY_ID_NOT_EXIST);
        }
        for(Authority authority : list){
            if(authority.getEnable()){
                throw new HttpException(AuthError.AUTHORITY_ALREADY_ENABLED);
            }
            authority.setEnable(true);
        }
        this.repository.saveAll(list);
        this.updateCache();
    }

    /**
     * 禁用权限
     * @param id
     */
    public void disable(Long id){
        Optional<Authority> optional = this.repository.findById(id);
        if(!optional.isPresent()){
            throw new HttpException(AuthError.AUTHORITY_ID_NOT_EXIST);
        }
        Authority authority = optional.get();
        if(!authority.getEnable()){
            throw new HttpException(AuthError.AUTHORITY_ALREADY_DISABLED);
        }
        authority.setEnable(false);
        this.repository.save(authority);
        this.updateCache();
    }

    /**
     * 批量禁用权限
     * @param ids：权限列表
     */
    public void batchDisable(List<Long> ids){
        List<Authority> list = this.repository.findAllById(ids);
        if(list.size()<ids.size()){
            throw new HttpException(AuthError.AUTHORITY_ID_NOT_EXIST);
        }
        for(Authority authority : list){
            if(!authority.getEnable()){
                throw new HttpException(AuthError.AUTHORITY_ALREADY_DISABLED);
            }
            authority.setEnable(false);
        }
        this.repository.saveAll(list);
        this.updateCache();
    }

    /* 权限同步方法，加上事务，可以保证多对多移除关系成功 */
    @Transactional
    public void permissionSync(Set<Authority> authorities){

        /* 目前数据库中所有，这里因为抓取了多层，需要使用Set接收，List会出现重复数据 */
        Set<Authority> all = this.authorityRepository.findAllWithRoles();

        /* 新增权限、删除权限、更新权限 */
        List<Authority> create = new ArrayList<>();
        List<Authority> delete = new ArrayList<>();
        List<Authority> update = new ArrayList<>();

        /* 在已存在的里面查找最新的，找到就更新，没找到就新增*/
        for(Authority authority:authorities){

            Authority auth = null;

            for(Authority a:all){
                /* 权限相等，即namespace和name相等，说明需要更新 */
                if(authority.equals(a)){
                    a.setDescription(authority.getDescription());
                    auth = a;
                }
            }

            /* 如果在已存在的里面找到了新的权限，则更新 */
            if(Objects.nonNull(auth)){
                update.add(auth);
            }
            /* 如果没找到，则新增 */
            else{
                create.add(authority);
            }

        }

        /* 在最新的里面查找已存在，找到就更新，没找到就删除*/
        for(Authority authority:all){

            Authority auth = null;

            for(Authority a:authorities){
                /* 权限相等，即namespace和name相等，说明需要更新 */
                if(authority.equals(a)){
                    a.setDescription(authority.getDescription());
                    auth = a;
                }
            }

            /* 如果在最新的里面找到了已存在的权限，则更新，需要更新的已经在上面添加了 */
            if(Objects.nonNull(auth)){
                //update.add(auth);
            }
            /* 如果没找到，则删除 */
            else{
                delete.add(authority);
            }

        }

        if(authProperties.isPermissionCreate()){
            log.info("插入权限");
            this.authorityRepository.saveAll(create);
        }

        if(authProperties.isPermissionUpdate()){
            log.info("更新权限");
            this.authorityRepository.saveAll(update);
        }

        if(authProperties.isPermissionDelete()){
            for(Authority authority:delete){
                /* 清除此权限的所有关系 */
                for(Role role:authority.getRoles()){
                    role.getAuthorities().remove(authority);
                }
            }
            log.info("删除权限");
            this.authorityRepository.deleteAll(delete);
        }

    }

}
