package com.funtl.myshop.plus.business.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "员工数据")
public class EmpParamDto implements Serializable {

    @ApiModelProperty(value = "员工id")
    private Long empBaseAuto;

    @ApiModelProperty(value = "部门id")
    private Long orgAuto;

    @ApiModelProperty(value = "部门名称")
    private String orgName;

    @ApiModelProperty(value = "职位表id")
    private Integer incTitleAuto;

    @ApiModelProperty(value = "职位")
    private String title;

    @ApiModelProperty(value = "员工姓名")
    private String fName;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "启用状态 0启用 1停用")
    private Integer isOn;

    @ApiModelProperty(value = "是否为主管 0是 1否")
    private Integer isBoss;

    @ApiModelProperty(value = "所属组")
    private String orgGroupName;

    @ApiModelProperty(value = "角色名")
    private String roleName;

    /*@ApiModelProperty(value = "分机")
    private String mobilePIN;

    @ApiModelProperty(value = "邮箱")
    private String email;*/
}