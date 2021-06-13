package czy.spring.boot.starter.auth.service;

import czy.spring.boot.starter.auth.AuthProperties;
import czy.spring.boot.starter.auth.constant.CacheConstant;
import czy.spring.boot.starter.auth.entity.Role;
import czy.spring.boot.starter.auth.entity.Role_;
import czy.spring.boot.starter.auth.ienum.AuthError;
import czy.spring.boot.starter.auth.repository.RoleRepository;
import czy.spring.boot.starter.common.exception.HttpException;
import czy.spring.boot.starter.common.service.BaseService;
import czy.spring.boot.starter.common.util.OrderUtil;
import czy.spring.boot.starter.jpa.entity.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 角色服务层操作
 */
@Service
@CacheConfig(cacheNames = CacheConstant.ROLE)
public class RoleService extends BaseService<Role> {

    @Autowired
    private AuthProperties authProperties;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    protected boolean cacheable() {
        return true;//开启角色缓存
    }

    /**
     * 角色缓存，包含了权限，权限更新后会更新角色缓存
     * @return
     */
    @Override
    @Cacheable(key = "'map'")
    public Map<Long, Role> getCache() {
        List<Role> list = this.roleRepository.findAllWithAuthorities();
        /* 转换为Map，一个ID映射一个实体对象 */
        return list.stream().collect(Collectors.toMap(BaseEntity::getId, a -> a,(k1, k2)->k1,TreeMap::new));
    }

    @Override
    @CachePut(key = "'map'")
    public Map<Long, Role> updateCache() {
        List<Role> list = this.roleRepository.findAllWithAuthorities();
        /* 转换为Map，一个ID映射一个实体对象 */
        return list.stream().collect(Collectors.toMap(BaseEntity::getId, a -> a,(k1, k2)->k1,TreeMap::new));
    }

    @Override
    public Predicate<Role> pageCachePredicate(Role payload) {
        /* 基本谓词 */
        Predicate<Role> root = super.pageCachePredicate(payload);
        if(StringUtils.hasText(payload.getName())){
            root = root.and((a->a.getName().contains(payload.getName())));
        }
        if(StringUtils.hasText(payload.getDescription())){
            root = root.and(a->a.getDescription().contains(payload.getDescription()));
        }
        if(Objects.nonNull(payload.getEnable())){
            root = root.and(a->a.getEnable().equals(payload.getEnable()));
        }
        return root;
    }

    @Override
    public Comparator<Role> pageCacheComparator(Sort sort) {
        Comparator<Role> root = super.pageCacheComparator(sort);
        List<Sort.Order> orders = sort.toList();
        /* 遍历所有排序条件 */
        for(Sort.Order order:orders){
            if(StringUtils.hasText(order.getProperty())){
                switch (order.getProperty()){
                    case Role_.NAME:
                        root.thenComparing(OrderUtil.getComparator(order,Role::getName));
                        break;
                    case Role_.DESCRIPTION:
                        root.thenComparing(OrderUtil.getComparator(order,Role::getDescription));
                        break;
                    case Role_.ENABLE:
                        root.thenComparing(OrderUtil.getComparator(order,Role::getEnable));
                        break;
                    default:
                        break;
                }
            }
        }
        return root;
    }

    @Override
    protected void createCheck(Role payload) {
        super.createCheck(payload);
        if(this.roleRepository.existsByName(payload.getName())){
            throw new HttpException(AuthError.ROLE_NAME_EXIST);
        }
    }

    @Override
    protected Role updateCheck(Role payload) {
        Role exist =  super.updateCheck(payload);
        if(!exist.getName().equals(payload.getName()) && this.roleRepository.existsByName(payload.getName())){
            throw new HttpException(AuthError.ROLE_NAME_EXIST);
        }
        return exist;
    }

    @Override
    protected Role deleteCheck(Role payload) {
        Role exist =  super.deleteCheck(payload);
        if(exist.getName().equals(this.authProperties.getAdminRole().getName())){
            throw new HttpException(AuthError.ROLE_NOT_ALLOW_DELETE);
        }
        if(exist.getName().equals(this.authProperties.getDefaultRole().getName())){
            throw new HttpException(AuthError.ROLE_NOT_ALLOW_DELETE);
        }
        return exist;
    }

    @Override
    protected List<Role> batchDeleteCheck(List<Long> ids) {
        List<Role> list =  super.batchDeleteCheck(ids);
        for(Role role:list){
            if(role.getName().equals(this.authProperties.getAdminRole().getName())){
                throw new HttpException(AuthError.ROLE_NOT_ALLOW_DELETE);
            }
            if(role.getName().equals(this.authProperties.getDefaultRole().getName())){
                throw new HttpException(AuthError.ROLE_NOT_ALLOW_DELETE);
            }
        }
        return list;
    }

    @Override
    public ExampleMatcher existExampleMatcher() {
        /* 角色存在查询 */
        return super.existExampleMatcher()
                /* 角色名称匹配 */
                .withMatcher(Role_.NAME, ExampleMatcher.GenericPropertyMatchers.caseSensitive());
    }

    /**
     * 角色授权方法，改变角色关联的角色
     * @param role
     */
    public void authorize(Role role){
        Optional<Role> optional = this.roleRepository.findById(role.getId());
        if(!optional.isPresent()){
            throw new HttpException(AuthError.ROLE_ID_NOT_EXIST);
        }
        Role exist = optional.get();
        exist.setAuthorities(role.getAuthorities());
        this.roleRepository.save(exist);
    }

    /**
     * 启用角色
     * @param id
     */
    public void enable(Long id){
        Optional<Role> optional = this.repository.findById(id);
        if(!optional.isPresent()){
            throw new HttpException(AuthError.ROLE_ID_NOT_EXIST);
        }
        Role role = optional.get();
        if(role.getEnable()){
            throw new HttpException(AuthError.ROLE_ALREADY_ENABLED);
        }
        role.setEnable(true);
        this.repository.save(role);
        this.updateCache();
    }

    /**
     * 批量启用角色
     * @param ids：角色列表
     */
    public void batchEnable(List<Long> ids){
        List<Role> list = this.repository.findAllById(ids);
        if(list.size()<ids.size()){
            throw new HttpException(AuthError.ROLE_ID_NOT_EXIST);
        }
        for(Role authority : list){
            if(authority.getEnable()){
                throw new HttpException(AuthError.ROLE_ALREADY_ENABLED);
            }
            authority.setEnable(true);
        }
        this.repository.saveAll(list);
        this.updateCache();
    }

    /**
     * 禁用角色
     * @param id
     */
    public void disable(Long id){
        Optional<Role> optional = this.repository.findById(id);
        if(!optional.isPresent()){
            throw new HttpException(AuthError.ROLE_ID_NOT_EXIST);
        }
        Role authority = optional.get();
        if(!authority.getEnable()){
            throw new HttpException(AuthError.ROLE_ALREADY_DISABLED);
        }
        authority.setEnable(false);
        this.repository.save(authority);
        this.updateCache();
    }

    /**
     * 批量禁用角色
     * @param ids：角色列表
     */
    public void batchDisable(List<Long> ids){
        List<Role> list = this.repository.findAllById(ids);
        if(list.size()<ids.size()){
            throw new HttpException(AuthError.ROLE_ID_NOT_EXIST);
        }
        for(Role authority : list){
            if(!authority.getEnable()){
                throw new HttpException(AuthError.ROLE_ALREADY_DISABLED);
            }
            authority.setEnable(false);
        }
        this.repository.saveAll(list);
        this.updateCache();
    }
}
