package com.funtl.myshop.plus.provider.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
    * 用户信息表
    */
@Data
@Table(name = "[aspnet_Membership]")
public class AspnetMembership implements Serializable {
    /**
    * 用户id（微软自动生成）
    */
    @Id
    @Column(name = "UserId")
    private Object userId;

    /**
    * 应用id（微软自动生成）
    */
    @Column(name = "ApplicationId")
    private Object applicationId;

    @Column(name = "Password")
    private String password;

    @Column(name = "PasswordCode")
    private String passwordCode;

    @Column(name = "PasswordFormat")
    private Integer passwordFormat;

    /**
     * 密码
     */
    @Column(name = "PasswordSalt")
    private String passwordSalt;

    @Column(name = "MobilePIN")
    private String mobilePIN;

    /**
    * 邮箱
    */
    @Column(name = "Email")
    private String email;

    @Column(name = "LoweredEmail")
    private String loweredEmail;

    @Column(name = "PasswordQuestion")
    private String passwordQuestion;

    @Column(name = "PasswordAnswer")
    private String passwordAnswer;

    /**
    * 是否通过
    */
    @Column(name = "IsApproved")
    private Boolean isApproved;

    /**
    * 是否锁定(0未锁定 1已锁定 密码输错三次即锁定)
    */
    @Column(name = "IsLockedOut")
    private Boolean isLockedOut;

    /**
    * 创建日期
    */
    @Column(name = "CreateDate")
    private Date createDate;

    /**
    * 最后一次登录日期
    */
    @Column(name = "LastLoginDate")
    private Date lastLoginDate;

    /**
    * 最后一次修改密码的日期
    */
    @Column(name = "LastPasswordChangedDate")
    private Date lastPasswordChangedDate;

    /**
    * 最后一次锁定日期
    */
    @Column(name = "LastLockoutDate")
    private Date lastLockoutDate;

    @Column(name = "FailedPasswordAttemptCount")
    private Integer failedPasswordAttemptCount;

    @Column(name = "FailedPasswordAttemptWindowStart")
    private Date failedPasswordAttemptWindowStart;

    @Column(name = "FailedPasswordAnswerAttemptCount")
    private Integer failedPasswordAnswerAttemptCount;

    @Column(name = "FailedPasswordAnswerAttemptWindowStart")
    private Date failedPasswordAnswerAttemptWindowStart;

    @Column(name = "Comment")
    private String comment;

    private static final long serialVersionUID = 1L;
}
