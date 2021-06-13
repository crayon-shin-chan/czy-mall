package czy.spring.boot.starter.admin.entity;

import czy.spring.boot.starter.admin.ienum.ApplicationType;
import czy.spring.boot.starter.jpa.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;

/**
 * 应用一般对应前端
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "应用实体")
public class Application extends BaseEntity {

    @NotNull
    @Column(unique = true)
    @Length(min = 2,max = 20)
    @ApiModelProperty("应用名称")
    private String name;

    @NotNull
    @ApiModelProperty("应用类型")
    @Enumerated(EnumType.STRING)
    private ApplicationType type;

    @Column(unique = true)
    @ApiModelProperty("应用ID")
    private String appId;

    @ApiModelProperty("应用密钥")
    private String appSecret;

    @ApiModelProperty("应用私钥")
    private String privateKey;

    @ApiModelProperty("应用公钥")
    private String publicKey;

    @NotNull
    @ApiModelProperty("是否启用")
    private Boolean enable;

    @OneToMany(mappedBy = "application")
    @ApiModelProperty("应用菜单列表")
    private Set<Menu> menus;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Application that = (Application) o;
        return name.equals(that.name) &&
                appId.equals(that.appId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, appId);
    }

}
