package czy.spring.boot.starter.common.util;

import org.springframework.data.domain.Sort;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;

public class OrderUtil {

    public static <T, U extends Comparable<? super U>> Comparator<T> getComparator(Sort.Order order, Function<T,U> function){
        Comparator<T> comparator = Comparator.comparing(function);
        if(Objects.nonNull(order.getDirection()) || order.getDirection().isAscending()){
            return comparator;
        }else{
            return comparator.reversed();
        }
    }

}
