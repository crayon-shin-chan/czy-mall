package czy.spring.boot.starter.jpa.util;


import czy.spring.boot.starter.jpa.entity.BaseEntity;
import czy.spring.boot.starter.jpa.entity.TreeEntity;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class TreeUtil {

    /**
     * 返回列表转换树
     */
    public static <T extends TreeEntity> List<T> tree(List<T> list){

        if(CollectionUtils.isEmpty(list)){
            return list;
        }

        /* 去重去空排序 */
        list = list.stream().filter(Objects::nonNull).distinct().sorted().collect(Collectors.toList());

        /* 根据ID分组 */
        Map<Long,List<T>> idMap = list.stream().collect(Collectors.groupingBy(BaseEntity::getId));

        /* 根据parent ID分组 */
        Map<Long, List<T>> parentIdMap = list.stream().filter(t->(Objects.nonNull(t.getParent())&&Objects.nonNull(t.getParent().getId()))).collect(Collectors.groupingBy(t->t.getParent().getId(),Collectors.toList()));
        /* 遍历所有ID */
        for(Map.Entry<Long,List<T>> entry:idMap.entrySet()){
            /* 获取自己的子列表 */
            List<T> children = parentIdMap.get(entry.getKey());
            if(!CollectionUtils.isEmpty(children)){
                /* 设置子节点 */
                entry.getValue().get(0).setChildren(children);
            }
        }

        /* 根据级别分组 */
        Map<Integer,List<T>> map = list.stream().filter(Objects::nonNull).distinct()
                .collect(Collectors.groupingBy(TreeEntity::getLevel));

        /* 级别排序 */
        List<Integer> levels = map.keySet().stream().sorted().collect(Collectors.toList());

        /* 返回最高级 */
        return map.get(levels.get(0));
    }

}
