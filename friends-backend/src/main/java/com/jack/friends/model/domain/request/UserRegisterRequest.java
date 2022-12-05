package com.jack.friends.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 *
 * @author jiafa
 */
@Data
public class UserRegisterRequest implements Serializable {
    private static final long serialVersionUID = -3520845288085825478L;

    private String userAccount;
    private String userPassword;
    private String checkPassword;
}
