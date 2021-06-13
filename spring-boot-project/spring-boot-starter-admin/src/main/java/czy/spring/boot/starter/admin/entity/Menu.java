package czy.spring.boot.starter.admin.entity;

import czy.spring.boot.starter.admin.ienum.MenuDisplayMode;
import czy.spring.boot.starter.jpa.entity.TreeEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "菜单实体")
public class Menu extends TreeEntity<Menu> implements Comparable<Menu> {

    @NotNull
    @Length(min = 2,max = 20)
    @ApiModelProperty("菜单名称")
    private String name;

    @NotNull
    @Length(min = 2,max = 50)
    @ApiModelProperty("菜单路径")
    private String path;

    @NotNull
    @Length(min = 2,max = 20)
    @ApiModelProperty("菜单标题")
    private String title;

    @NotNull
    @Length(min = 2,max = 50)
    @ApiModelProperty("菜单组件")
    private String component;

    @NotNull
    @Length(min = 2,max = 20)
    @ApiModelProperty("菜单图标")
    private String icon;

    @URL
    @ApiModelProperty("菜单显示url")
    private String url;

    @NotNull
    @ApiModelProperty("组件是否保持")
    private Boolean keepAlive;

    @NotNull
    @Range(min = 1L)//菜单级别，最小为1
    @ApiModelProperty(value = "菜单级别",example = "1")
    private Integer level;

    @Column(name = "m_order")
    @ApiModelProperty(value = "菜单顺序",example = "1")
    private Integer order;

    @ApiModelProperty("菜单显示模式")
    @Enumerated(EnumType.STRING)
    private MenuDisplayMode mode;

    @ApiModelProperty("显示菜单需要的权限")
    private String permission;

    @NotNull
    @ApiModelProperty("菜单是否启用")
    private Boolean enable;

    /* 一个菜单只能属于一个应用 */
    @NotNull
    @ApiModelProperty("菜单所属应用")
    @ManyToOne
    private Application application;

    @ApiModelProperty("父菜单")
    @ManyToOne
    private Menu parent;

    @ApiModelProperty("子菜单")
    @OneToMany(mappedBy = "parent")
    private List<Menu> children;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return enable == menu.enable &&
                Objects.equals(name, menu.name) &&
                Objects.equals(path, menu.path) &&
                Objects.equals(component, menu.component);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, path, component, enable);
    }

    @Override
    public int compareTo(Menu o) {
        if(this.level.intValue() != o.level.intValue()){
            return this.level - o.level;
        }
        return this.order-o.order;
    }
}
