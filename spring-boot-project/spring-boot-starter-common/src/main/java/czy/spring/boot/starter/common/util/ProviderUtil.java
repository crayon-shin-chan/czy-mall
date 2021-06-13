package czy.spring.boot.starter.common.util;

import czy.spring.boot.starter.common.interfaces.Provider;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProviderUtil {

    public static <T> Set<T> get(List<? extends Provider<T>> providers){

        Set<T> result = new HashSet<>();

        if(CollectionUtils.isEmpty(providers)){
            return result;
        }

        for(int i=0;i<providers.size();i++){

            result.addAll(providers.get(i).get());

        }

        return result;

    }

}
