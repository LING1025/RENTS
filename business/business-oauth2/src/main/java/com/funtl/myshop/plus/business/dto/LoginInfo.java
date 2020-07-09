package com.funtl.myshop.plus.business.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 登录信息
 */

@Data
public class LoginInfo implements Serializable {
    private String name;
    private Long userAuto;
    private List<Long> roleAutos;
}
