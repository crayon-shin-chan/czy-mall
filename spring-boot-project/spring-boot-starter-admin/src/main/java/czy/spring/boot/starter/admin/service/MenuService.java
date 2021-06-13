package czy.spring.boot.starter.admin.service;

import czy.spring.boot.starter.admin.constant.CacheConstant;
import czy.spring.boot.starter.admin.entity.Menu;
import czy.spring.boot.starter.admin.entity.Menu_;
import czy.spring.boot.starter.admin.ienum.AdminError;
import czy.spring.boot.starter.admin.repository.MenuRepository;
import czy.spring.boot.starter.common.exception.HttpException;
import czy.spring.boot.starter.common.service.BaseService;
import czy.spring.boot.starter.common.util.OrderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.function.Predicate;

@Service
@CacheConfig(cacheNames = CacheConstant.MENU)
public class MenuService extends BaseService<Menu> {

    @Autowired
    private MenuRepository menuRepository;

    @Override
    protected boolean cacheable() {
        return true;//启用缓存
    }

    @Override
    @Cacheable(key = "'map'")
    public Map<Long, Menu> getCache() {
        return super.getCache();
    }

    @Override
    @CachePut(key = "'map'")
    public Map<Long, Menu> updateCache() {
        return super.updateCache();
    }

    @Override
    public Predicate<Menu> pageCachePredicate(Menu payload) {
        /* 基本谓词 */
        Predicate<Menu> root = super.pageCachePredicate(payload);
        if(StringUtils.hasText(payload.getName())){
            root = root.and(a->a.getName().contains(payload.getName()));
        }
        if(StringUtils.hasText(payload.getPath())){
            root = root.and((a->a.getPath().contains(payload.getPath())));
        }
        if(Objects.nonNull(payload.getTitle())){
            root = root.and(a->a.getTitle().equals(payload.getTitle()));
        }
        if(Objects.nonNull(payload.getComponent())){
            root = root.and(a->a.getComponent().equals(payload.getComponent()));
        }
        if(Objects.nonNull(payload.getUrl())){
            root = root.and(a->a.getUrl().equals(payload.getUrl()));
        }
        if(Objects.nonNull(payload.getLevel())){
            root = root.and(a->a.getLevel().equals(payload.getLevel()));
        }
        if(Objects.nonNull(payload.getPermission())){
            root = root.and(a->a.getPermission().contains(payload.getPermission()));
        }
        if(Objects.nonNull(payload.getEnable())){
            root = root.and(a->a.getEnable().equals(payload.getEnable()));
        }
        if(Objects.nonNull(payload.getApplication()) && Objects.nonNull(payload.getApplication().getId())){
            root = root.and(a->( Objects.nonNull(a.getApplication()) && a.getApplication().getId().equals(payload.getApplication().getId())));
        }
        if(Objects.nonNull(payload.getParent()) && Objects.nonNull(payload.getParent().getId())){
            root = root.and(a->( Objects.nonNull(a.getParent()) && a.getParent().getId().equals(payload.getParent().getId())));
        }
        return root;
    }

    @Override
    public Comparator<Menu> pageCacheComparator(Sort sort) {
        Comparator<Menu> root = super.pageCacheComparator(sort);
        List<Sort.Order> orders = sort.toList();
        /* 遍历所有排序条件 */
        for(Sort.Order order:orders){
            if(StringUtils.hasText(order.getProperty())){
                switch (order.getProperty()){
                    case Menu_.NAME:
                        root.thenComparing(OrderUtil.getComparator(order,Menu::getName));
                        break;
                    case Menu_.PATH:
                        root.thenComparing(OrderUtil.getComparator(order,Menu::getPath));
                        break;
                    case Menu_.ORDER:
                        root.thenComparing(OrderUtil.getComparator(order,Menu::getOrder));
                        break;
                    case Menu_.ENABLE:
                        root.thenComparing(OrderUtil.getComparator(order,Menu::getEnable));
                        break;
                    default:
                        break;
                }
            }
        }
        return root;
    }

    @Override
    protected void createCheck(Menu payload) {
        if(this.menuRepository.countByPathAndApplication(payload.getPath(),payload.getApplication())>0){
            throw new HttpException(AdminError.MENU_PATH_EXIST);
        }
    }

    @Override
    protected Menu updateCheck(Menu payload) {
        Menu exist =  super.updateCheck(payload);
        /* 如果路径或者所属应用修改，检查重复性 */
        if(
                !payload.getPath().equals(exist.getPath())
                        ||
                !payload.getApplication().getId().equals(exist.getApplication().getId())
        ){
            if(this.menuRepository.countByPathAndApplication(payload.getPath(),payload.getApplication())>0){
                throw new HttpException(AdminError.MENU_PATH_EXIST);
            }
        }
        return exist;
    }

    @Override
    public Predicate<Menu> existCachePredicate(Menu payload) {
        return m-> { return
                (m.getName().equals(payload.getName()) && m.getApplication().getId().equals(payload.getApplication().getId())) ||
                (m.getPath().equals(payload.getPath()) && m.getApplication().getId().equals(payload.getApplication().getId())) ||
                (m.getTitle().equals(payload.getTitle()) && m.getApplication().getId().equals(payload.getApplication().getId()));
        };
    }

    @Override
    public Specification<Menu> pageSpecification(Menu payload) {
        return new Specification<Menu>() {
            @Override
            public javax.persistence.criteria.Predicate toPredicate(Root<Menu> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<javax.persistence.criteria.Predicate> predicates = new ArrayList<>();//条件列表
                if(StringUtils.hasText(payload.getName())){
                    predicates.add(criteriaBuilder.like(root.get(Menu_.name),payload.getName()));//添加菜单名称like条件
                }
                if(StringUtils.hasText(payload.getPath())){
                    predicates.add(criteriaBuilder.like(root.get(Menu_.path),payload.getPath()));//添加菜单路径like条件
                }
                if(Objects.nonNull(payload.getTitle())){
                    predicates.add(criteriaBuilder.like(root.get(Menu_.title),payload.getTitle()));//添加菜单标题like条件
                }
                if(Objects.nonNull(payload.getComponent())){
                    predicates.add(criteriaBuilder.like(root.get(Menu_.component),payload.getComponent()));//添加菜单组件like条件
                }
                if(Objects.nonNull(payload.getUrl())){
                    predicates.add(criteriaBuilder.like(root.get(Menu_.url),payload.getUrl()));//添加菜单urllike条件
                }
                if(Objects.nonNull(payload.getLevel())){
                    predicates.add(criteriaBuilder.equal(root.get(Menu_.level),payload.getLevel()));//添加菜单级别equal条件
                }
                if(Objects.nonNull(payload.getPermission())){
                    predicates.add(criteriaBuilder.like(root.get(Menu_.permission),payload.getPermission()));//添加菜单权限like条件
                }
                if(Objects.nonNull(payload.getEnable())){
                    predicates.add(criteriaBuilder.equal(root.get(Menu_.enable),payload.getEnable()));//添加菜单enable equal条件
                }
                if(Objects.nonNull(payload.getApplication()) && Objects.nonNull(payload.getApplication().getId())){
                    predicates.add(criteriaBuilder.equal(root.get(Menu_.application),payload.getApplication()));//添加菜单所属应用equal条件
                }
                if(Objects.nonNull(payload.getParent()) && Objects.nonNull(payload.getParent().getId())){
                    predicates.add(criteriaBuilder.equal(root.get(Menu_.parent),payload.getParent()));//添加菜单所属父级equal条件
                }
                /* 返回一个且条件 */
                return criteriaBuilder.and(predicates.toArray(new javax.persistence.criteria.Predicate[predicates.size()]));
            }
        };
    }
}
