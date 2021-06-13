package czy.spring.boot.starter.auth.comparator;

import czy.spring.boot.starter.auth.entity.Authority;
import czy.spring.boot.starter.common.annotion.*;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AuthorityComparator implements Comparator<Authority> {

    public static final AuthorityComparator INSTANCE = new AuthorityComparator();

    private static Map<String,Integer> classMap = new HashMap<>();

    static{
        classMap.put(Read.class.getSimpleName(),1);
        classMap.put(Create.class.getSimpleName(),2);
        classMap.put(Update.class.getSimpleName(),3);
        classMap.put(Delete.class.getSimpleName(),4);
        classMap.put(Import.class.getSimpleName(),5);
        classMap.put(Export.class.getSimpleName(),6);
        classMap.put(Metadata.class.getSimpleName(),7);
    }

    @Override
    public int compare(Authority o1, Authority o2) {

        Integer a1 = classMap.get(o1.getName());
        Integer a2 = classMap.get(o2.getName());

        if(Objects.nonNull(a1) && Objects.nonNull(a2)){
            return a1 - a2;
        }

        if(Objects.isNull(a1) && Objects.isNull(a2)){
            return o1.getName().hashCode() - o2.getName().hashCode();
        }

        if(Objects.isNull(a1) && Objects.nonNull(a2)){
            return 1;
        }
        if(Objects.nonNull(a1) && Objects.isNull(a2)){
            return -1;
        }

        return 0;
    }
}
