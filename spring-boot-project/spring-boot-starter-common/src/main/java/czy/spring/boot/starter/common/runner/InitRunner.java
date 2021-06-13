package czy.spring.boot.starter.common.runner;

import czy.spring.boot.starter.common.interfaces.InitAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 初始化运行器，运行{@link czy.spring.boot.starter.common.interfaces.InitAction}
 */
@Slf4j
@Component
@Order(Integer.MIN_VALUE)
public class InitRunner implements ApplicationRunner{

    @Autowired(required = false)
    private List<InitAction> initActions;

    @Override
    public void run(ApplicationArguments args) throws Exception {


        if(CollectionUtils.isEmpty(initActions)){
            log.info("初始化动作不存在");
            return;
        }
        log.info("开始运行初始化动作："+initActions.size());
        initActions.forEach(action->{
            log.info("运行初始化动作："+action.getName()+","+action.getClass().getName());
            action.doAction();
        });


    }

}
