package czy.spring.boot.starter.jpa.convert;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 字符串列表转换器
 */
public class StringListConvert implements AttributeConverter<List<String>,String> {

    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        if(CollectionUtils.isEmpty(attribute)){
            return null;
        }
        return attribute.stream().collect(Collectors.joining(","));
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        if(StringUtils.isEmpty(dbData)){
            return new ArrayList<>();
        }
        return Arrays.stream(dbData.split(",")).collect(Collectors.toList());
    }
}
