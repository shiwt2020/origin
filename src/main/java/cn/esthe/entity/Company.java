package cn.esthe.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "hhd")
@ApiModel(value = "公司实体类")
@Entity
public class Company implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "主键Id")
    private String id;

    @ApiModelProperty(value = "公司名称")
    @Column(name = "name")
    private String name;

    @ApiModelProperty(value = "公司电话")
    @Column(name = "phone")
    private String phone;

    @ApiModelProperty(value = "公司状态")
    @Column(name = "state")
    private Integer state;

}