package czy.spring.boot.starter.common.interfaces;

import java.util.HashSet;
import java.util.Set;

/**
 * 供应商接口，用于解耦式提供某种数据
 * 比如权限，可以来源于部门、群组、用户组、角色
 * 可以通过Provider来分开提供
 */
public interface Provider<T> {

    /* 获取此供应商提供的数据 */
    default Set<T> get(){
        return new HashSet<>();
    }
}
