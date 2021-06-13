package czy.spring.boot.starter.jpa.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@MappedSuperclass
public class TreeEntity<T extends BaseEntity> extends BaseEntity{

    @ApiModelProperty(value = "树级别",example = "1")
    private Integer level;

    @ApiModelProperty("父级")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id",referencedColumnName = "id")
    private T parent;

    @ApiModelProperty("子级")
    @OneToMany(mappedBy = "parent")
    private List<T> children = new ArrayList<>();

}
