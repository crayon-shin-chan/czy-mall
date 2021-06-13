package czy.spring.boot.starter.auth.ienum;

import czy.spring.boot.starter.common.exception.IError;

/**
 * 认证错误枚举
 */
public enum AuthError implements IError {

    /* 未知错误 */
    NEED_LOGIN(401,"需要登陆"),
    ACCESS_DENY(403,"访问拒绝"),
    NEED_USER_NAME(451,"需要用户名"),
    USER_PASSWORD_INCORRECT(452,"用户密码不正确"),
    USER_NAME_EXIST(453,"用户名已存在"),
    EMAIL_EXIST(454,"邮箱已存在"),
    PHONE_EXIST(455,"手机号已存在"),
    USER_ID_NOT_EXIST(456,"用户ID不存在"),
    ADMIN_USER_READONLY(457,"管理员用户不允许修改"),
    SCHEDULE_USER_READONLY(458,"调度任务用户不允许修改"),
    ROLE_ID_NOT_EXIST(459,"角色ID不存在"),
    ROLE_NAME_EXIST(460,"角色名称已存在"),
    ROLE_NOT_ALLOW_DELETE(461,"角色不允许删除"),
    ROLE_ALREADY_ENABLED(462,"角色已经启用"),
    ROLE_ALREADY_DISABLED(463,"角色已经禁用"),
    AUTHORITY_ID_NOT_EXIST(464,"权限ID不存在"),
    AUTHORITY_ALREADY_ENABLED(465,"权限已经启用"),
    AUTHORITY_ALREADY_DISABLED(466,"权限已经禁用");

    private int code;
    private String message;

    AuthError(int code,String message){
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

}
