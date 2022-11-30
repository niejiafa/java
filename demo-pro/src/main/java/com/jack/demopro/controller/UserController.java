package com.jack.demopro.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jack.demopro.common.BaseResponse;
import com.jack.demopro.common.ErrorCode;
import com.jack.demopro.common.ResultUtils;
import com.jack.demopro.exception.BusinessException;
import com.jack.demopro.model.domain.User;
import com.jack.demopro.model.domain.request.UserLoginRequest;
import com.jack.demopro.model.domain.request.UserRegisterRequest;
import com.jack.demopro.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.jack.demopro.constant.UserConstant.ADMIN_ROLE;
import static com.jack.demopro.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户接口
 *
 * @author jiafa
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARMS_ERROR, "参数为空");
        }

        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, userAccount, checkPassword)) {
            return null;
        }

        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        return ResultUtils.success(result);
    }

    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARMS_ERROR, "参数为空");
        }

        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userAccount)) {
            throw new BusinessException(ErrorCode.PARMS_ERROR, "参数为空");
        }

        User user = userService.doLogin(userAccount, userPassword, request);
        return ResultUtils.success(user);
    }

    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.PARMS_ERROR, "参数为空");
        }

        Integer result = userService.userLogout(request);

        return ResultUtils.success(result);
    }

    @GetMapping("/current")
    public BaseResponse<User> getCurrent(HttpServletRequest request) {
        Object object = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) object;
        if (user == null) {
            throw new BusinessException(ErrorCode.PARMS_ERROR, "参数为空");
        }

        Long userId = user.getId();
        // todo 校验用户是否合法
        User currentUser = userService.getById(userId);

        User result = userService.getSafetyUser(currentUser);

        return ResultUtils.success(result);
    }

    @GetMapping("/search")
    public BaseResponse<List<User>> searchUser(String userName, HttpServletRequest request) {

        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.PARMS_ERROR);
        }

        QueryWrapper queryWrapper = new QueryWrapper();
        if (StringUtils.isNotBlank(userName)) {
            queryWrapper.like("username", userName);
        }

        List<User> userList= userService.list();
        List<User> list = userList.stream().map(user -> {
            return userService.getSafetyUser(user);
        }).collect(Collectors.toList());

        return ResultUtils.success(list);
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody long id, HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH, "无权限");
        }

        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARMS_ERROR, "参数为空");
        }

        boolean result = userService.removeById(id);

        return ResultUtils.success(result);
    }

    private boolean isAdmin(HttpServletRequest request) {
        Object object = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) object;

        return user != null && user.getUserRole() == ADMIN_ROLE;
    }
}
