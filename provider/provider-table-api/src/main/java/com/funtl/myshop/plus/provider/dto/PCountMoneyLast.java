package com.funtl.myshop.plus.provider.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PCountMoneyLast implements Serializable {
    @ApiModelProperty(value = "年月")
    private String yearMonLast;

    @ApiModelProperty(value = "日")
    private String daysLast;

    @ApiModelProperty(value = "台数")
    private Integer pCountLast;

    @ApiModelProperty(value = "金额")
    private Integer pMoneyLast;
   /* private Long orgAuto;
    private Long salesAuto;

    private String fName;*/
}
