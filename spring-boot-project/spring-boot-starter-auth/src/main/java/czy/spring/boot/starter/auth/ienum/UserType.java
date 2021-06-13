package czy.spring.boot.starter.auth.ienum;

import czy.spring.boot.starter.common.ienum.BaseType;

/**
 * 管理员用户类型枚举
 */
public enum UserType implements BaseType {

    ADMIN_USER_TYPE("后台管理员");

    private String desc;

    UserType(String desc){
        this.desc = desc;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
