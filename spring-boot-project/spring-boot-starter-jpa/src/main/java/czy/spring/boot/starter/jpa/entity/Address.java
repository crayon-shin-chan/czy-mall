package czy.spring.boot.starter.jpa.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

/* 地址内嵌实体 */
@Data
@Embeddable
@NoArgsConstructor
@ApiModel(description = "省市区地址实体")
public class Address {

    /* 省编码 */
    private String province;

    /* 市编码 */
    private String city;

    /* 县编码 */
    private String county;

    /* 详细地址 */
    private String detail;

}
