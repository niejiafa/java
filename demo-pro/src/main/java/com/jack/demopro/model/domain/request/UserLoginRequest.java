package com.jack.demopro.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录请求体
 *
 * @author jiafa
 */
@Data
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID = 3369576796466758377L;

    private String userAccount;
    private String userPassword;
    private String checkPassword;
}
