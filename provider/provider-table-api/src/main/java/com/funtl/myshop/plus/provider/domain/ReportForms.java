package com.funtl.myshop.plus.provider.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(value = "营业报表返回属性")
public class ReportForms implements Serializable {
    private Long salesAuto;

    private String fName;

    private Long orgAuto;


    /**
     * 部门
     */
    @ApiModelProperty(value = "部门")
    private String orgName;

    /**
     * 目标台数
     */
    @ApiModelProperty(value = "目标台数")
    private Integer targetNum;

    /**
     * 实际台数
     */
    @ApiModelProperty(value = "实际台数")
    private Integer realNum;

    /**
     * 目标报件户数
     */
    @ApiModelProperty(value = "目标报件户数")
    private Integer targetPaperNum;

    /**
     * 试算报件户数
     */
    @ApiModelProperty(value = "试算报件户数")
    private Integer proPaperNum;

    /**
     * 回租报件户数
     */
//    private Integer rentPaperNum;

    /**
     * 目标营业额
     */
    @ApiModelProperty(value = "目标营业额")
    private Long targetVolume;

    /**
     * 实际营业额
     */
    @ApiModelProperty(value = "实际营业额")
    private Integer realVolume;

    @ApiModelProperty(value = "报件达成率")
    private Double paperLv;

    @ApiModelProperty(value = "台数达成率")
    private Double countLv;

    @ApiModelProperty(value = "营业额达成率")
    private Double volumeLv;

}
