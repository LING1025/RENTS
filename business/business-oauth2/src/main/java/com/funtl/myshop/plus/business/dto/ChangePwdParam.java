package com.funtl.myshop.plus.business.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChangePwdParam implements Serializable {
    private String username;
    private String newPassword;
}
