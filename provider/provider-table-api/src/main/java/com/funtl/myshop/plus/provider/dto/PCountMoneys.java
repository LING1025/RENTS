package com.funtl.myshop.plus.provider.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PCountMoneys implements Serializable {
    @ApiModelProperty(value = "年月")
    private String yearMon;

    @ApiModelProperty(value = "日")
    private String days;

    @ApiModelProperty(value = "台数")
    private Integer pCount;

    @ApiModelProperty(value = "金额")
    private Integer pMoney;
/*
    private Long orgAuto;
    private Long salesAuto;

    private String fName;*/
}
