package czy.spring.boot.starter.common.util;

import czy.spring.boot.starter.common.interfaces.Action;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ActionUtil {

    /* 执行多个动作，获取返回值 */
    public static Map<String,Object> doAction(List<? extends Action> actions){

        Map<String,Object> result = new HashMap<>();

        if(CollectionUtils.isEmpty(actions)){
            return result;
        }

        for(Action action:actions){

            Object o = action.doAction();

            if(Objects.nonNull(o) && Objects.nonNull(o)){
                result.put(action.getName(),o);
            }

        }

        return result;

    }

}
