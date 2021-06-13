package czy.spring.boot.starter.common.interfaces;

/** 动作接口，动作用于各个模块之间的解耦操作
 *  如登录动作可以在其他模块中定义，但是登录控制器中不需要知道到底是哪些模块的动作
 *  只需要注入动作列表，登录完成后调用即可
 *  动作可以有返回值，返回值应该以动作名称存储
 *  动作参数不应该指定
 *  动作一般只有查询等操作，不会进行数据修改
 */
public interface Action {

    /* 动作名称 */
    String getName();

    /* 执行动作 */
    Object doAction();


}
