package czy.spring.boot.starter.admin.ienum;

/* 菜单显示模式 */
public enum MenuDisplayMode {

    ALLAYS,//总是显示
    HAVE_CHILDREN,//包含子菜单时显示
    HAVE_PERMISSION;//用户具有指定权限时显示

}
