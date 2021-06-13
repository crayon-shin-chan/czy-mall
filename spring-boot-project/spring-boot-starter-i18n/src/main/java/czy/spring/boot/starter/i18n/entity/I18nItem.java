package czy.spring.boot.starter.i18n.entity;


import czy.spring.boot.starter.jpa.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Data
@Entity
@ApiModel("国际化实体")
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "code_lang",columnNames = {"code","lang"})//指定code、lang唯一
})
public class I18nItem extends BaseEntity {

    @NotNull
    @Length(min = 3,max = 15)
    @ApiModelProperty("国际化编码")
    private String code;

    @NotNull
    @ApiModelProperty("国际化语言")
    private String lang;

    @NotNull
    @ApiModelProperty("指定语言文本")
    private String value;

    @ApiModelProperty("国际化描述")
    private String description;

    @NotNull
    @ApiModelProperty("是否启用")
    private Boolean enable;

}
