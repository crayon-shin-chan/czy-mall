package czy.spring.boot.starter.i18n.service;


import czy.spring.boot.starter.common.exception.HttpException;
import czy.spring.boot.starter.common.service.BaseService;
import czy.spring.boot.starter.common.util.OrderUtil;
import czy.spring.boot.starter.i18n.constant.CacheConstant;
import czy.spring.boot.starter.i18n.entity.I18nItem;
import czy.spring.boot.starter.i18n.entity.I18nItem_;
import czy.spring.boot.starter.i18n.ienum.I18nError;
import czy.spring.boot.starter.i18n.repository.I18nItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = CacheConstant.I18N)
public class I18nItemService extends BaseService<I18nItem> {

    @Autowired
    private I18nItemRepository i18nItemRepository;


    @Override
    protected boolean cacheable() {
        return true;//启用缓存
    }

    @Override
    @Cacheable(key = "'map'")
    public Map<Long, I18nItem> getCache() {
        return super.getCache();
    }

    @CachePut(key = "'map'")
    public Map<Long, I18nItem> updateCache(){
        Map<Long,I18nItem> map = super.updateCache();
        this.updateLangCodeMap(map.values());
        return map;
    }
    
    
    @Override
    public Predicate<I18nItem> pageCachePredicate(I18nItem payload) {
        /* 基本谓词 */
        Predicate<I18nItem> root = super.pageCachePredicate(payload);
        if(StringUtils.hasText(payload.getCode())){
            root = root.and(a->a.getCode().contains(payload.getCode()));
        }
        if(Objects.nonNull(payload.getLang())){
            root = root.and(a->a.getLang().equals(payload.getLang()));
        }
        if(StringUtils.hasText(payload.getValue())){
            root = root.and((a->a.getValue().contains(payload.getValue())));
        }
        if(Objects.nonNull(payload.getEnable())){
            root = root.and(a->a.getEnable().equals(payload.getEnable()));
        }
        return root;
    }

    @Override
    public Comparator<I18nItem> pageCacheComparator(Sort sort) {
        Comparator<I18nItem> root = super.pageCacheComparator(sort);
        List<Sort.Order> orders = sort.toList();
        /* 遍历所有排序条件 */
        for(Sort.Order order:orders){
            if(StringUtils.hasText(order.getProperty())){
                switch (order.getProperty()){
                    case I18nItem_.LANG:
                        root.thenComparing(OrderUtil.getComparator(order,I18nItem::getLang));
                        break;
                    case I18nItem_.CODE:
                        root.thenComparing(OrderUtil.getComparator(order,I18nItem::getCode));
                        break;
                    case I18nItem_.VALUE:
                        root.thenComparing(OrderUtil.getComparator(order,I18nItem::getValue));
                        break;
                    case I18nItem_.ENABLE:
                        root.thenComparing(OrderUtil.getComparator(order,I18nItem::getEnable));
                        break;
                    default:
                        break;
                }
            }
        }
        return root;
    }

    @Override
    protected void createCheck(I18nItem payload) {
        super.createCheck(payload);
        if(this.i18nItemRepository.existsByCodeAndLang(payload.getCode(),payload.getLang())){
            throw new HttpException(I18nError.I18N_CODE_AND_LANG_EXIST);
        }
    }

    @Override
    protected I18nItem updateCheck(I18nItem payload) {
        I18nItem exist = super.updateCheck(payload);
        if(this.i18nItemRepository.existsByCodeAndLang(payload.getCode(),payload.getLang())){
            throw new HttpException(I18nError.I18N_CODE_AND_LANG_EXIST);
        }
        return exist;
    }

    @Override
    public Predicate<I18nItem> existCachePredicate(I18nItem payload) {
        return i->{
            return i.getCode().equals(payload.getCode())&&i.getLang().equals(payload.getLang());
        };
    }
    
    /**
     * 获取lang,code与I18nItem的映射
     */
    @Cacheable(key = "'lang_code_map'")
    public Map<String,Map<String,String>> getLangCodeMap(){
        Collection<I18nItem> items = this.getCache().values();
        /* 先根据语言分组，后根据code分组，值为value */
        return items.stream().collect(Collectors.groupingBy(I18nItem::getLang,Collectors.toMap(I18nItem::getCode,I18nItem::getValue)));
    }

    /**
     * 更新lang,code与I18nItem的映射
     */
    @CachePut(key = "'lang_code_map'")
    public Map<String,Map<String,String>> updateLangCodeMap(Collection<I18nItem> list){
        /* 先根据语言分组，后根据code分组 */
        return list.stream().collect(Collectors.groupingBy(I18nItem::getLang,Collectors.toMap(I18nItem::getCode,I18nItem::getValue)));
    }

}
