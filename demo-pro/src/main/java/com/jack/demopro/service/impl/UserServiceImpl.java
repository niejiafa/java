package com.jack.demopro.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jack.demopro.common.ErrorCode;
import com.jack.demopro.exception.BusinessException;
import com.jack.demopro.model.domain.User;
import com.jack.demopro.mapper.UserMapper;
import com.jack.demopro.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.jack.demopro.constant.UserConstant.USER_LOGIN_STATE;

/**
 * @author zhoushaoxiang
 * @description 针对表【user(用户)】的数据库操作Service实现
 * @createDate 2022-11-25 20:32:58
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;
    /**
     * 盐值 混淆密码
     */
    public static final String SALT = "jack";

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        // 1. 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARMS_ERROR, "账号过短");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARMS_ERROR, "密码过短");
        }
        // 账户不能包含特殊字符
        Matcher matcher = Pattern.compile("^[/|\\\\]*$").matcher(userAccount);
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARMS_ERROR, "账户包含特殊字符");
        }
        // 密码与校验密码相同
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARMS_ERROR, "密码与校验密码不相同");
        }
        // 账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_account", userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARMS_ERROR, "账户重复");
        }

        // 2.加密
        String md5Password = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        // 3.插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(md5Password);
        boolean save = this.save(user);
        if (!save) {
            throw new BusinessException(ErrorCode.PARMS_ERROR, "插入数据失败");
        }

        return user.getId();
    }

    @Override
    public User doLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 1. 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARMS_ERROR, "请求数据为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARMS_ERROR, "请求数据为空");
        }
        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARMS_ERROR, "请求数据为空");
        }
        // 账户不能包含特殊字符
        Matcher matcher = Pattern.compile("^[/|\\\\]*$").matcher(userAccount);
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARMS_ERROR, "账户包含特殊字符");
        }

        // 2.加密
        String md5Password = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_account", userAccount);
        queryWrapper.eq("user_password", md5Password);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            log.info("user login failed, userAccount cannot match userPassword");
            throw new BusinessException(ErrorCode.NULL_ERROR, "数据为空");
        }

        // 3.用户脱敏
        User safetyUser = getSafetyUser(user);

        // 4.记录用户登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, user);

        return safetyUser;
    }

    /**
     * 用户脱敏
     *
     * @param originUser
     * @return
     */
    @Override
    public User getSafetyUser(User originUser) {
        if (originUser == null) {
            throw new BusinessException(ErrorCode.PARMS_ERROR, "请求数据为空");
        }

        User safetyUser = new User();
        safetyUser.setId(originUser.getId());
        safetyUser.setUsername(originUser.getUsername());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setAvataUrl(originUser.getAvataUrl());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setPhone(originUser.getPhone());
        safetyUser.setEmail(originUser.getEmail());
        safetyUser.setUserRole(originUser.getUserRole());
        safetyUser.setUserStatus(originUser.getUserStatus());
        safetyUser.setCreateTime(originUser.getCreateTime());

        return safetyUser;
    }

    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    @Override
    public int userLogout(HttpServletRequest request) {
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }
}




