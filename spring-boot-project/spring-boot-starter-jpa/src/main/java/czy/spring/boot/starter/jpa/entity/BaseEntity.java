package czy.spring.boot.starter.jpa.entity;

import czy.spring.boot.starter.jpa.listener.AuditingNameEntityListener;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/** 实体基类，使用{@link MappedSuperclass}注解，属性被子类继承
 *  {@link AuditingEntityListener}实现了审计功能
 *
 * {@link Data}包含了以下功能；
 * {@link Getter}，生成getter方法
 * {@link Setter}，生成setter方法
 * {@link RequiredArgsConstructor}，生成包含必须属性(即带有约束的属性)的构造函数
 * {@link ToString}，生成带有所有字段的tostring方法
 * {@link EqualsAndHashCode}，重写equals、hashCode方法
 *
 * {@link NoArgsConstructor}，生成无参构造函数
 */
@Data
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class, AuditingNameEntityListener.class})
public abstract class BaseEntity implements Serializable {

    /** 主键 */
    @Id
    @ApiModelProperty(value = "主键ID",example = "1")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* 创建人ID */
    @CreatedBy
    @ApiModelProperty(value = "创建人ID",example = "1")
    private Long createBy;

    @ApiModelProperty("创建人用户名")
    private String createByName;

    /* 创建时间 */
    @CreatedDate
    @ApiModelProperty("创建日期")
    private Date createdDate;

    /* 最后更新人ID */
    @LastModifiedBy
    @ApiModelProperty(value = "最后更新人ID",example = "1")
    private Long updateBy;

    @ApiModelProperty("最后更新人用户名")
    private String updateByName;

    /* 最后更新时间 */
    @LastModifiedDate
    @ApiModelProperty("最后更新日期")
    private Date updatedDate;

    /** 实体版本 */
    @Version
    @ApiModelProperty(value = "版本号",example = "1")
    private Long version;

}
