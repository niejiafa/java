package com.jack.demopro.service;

import com.jack.demopro.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhoushaoxiang
 * @description 针对表【user(用户)】的数据库操作Service
 * @createDate 2022-11-25 20:32:58
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userAccount   账号
     * @param userPassword  密码
     * @param checkPassword 校验密码
     * @return 新用户id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 用户登录
     *
     * @param userAccount  账号
     * @param userPassword 密码
     * @param request
     * @return 脱敏后的用户信息
     */
    User doLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户脱敏
     *
     * @param originUser
     * @return
     */
    User getSafetyUser(User originUser);

    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    int userLogout(HttpServletRequest request);
}
