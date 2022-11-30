package com.jack.demopro.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
    public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            return null;
        }

        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, userAccount, checkPassword)) {
            return null;
        }

        return userService.userRegister(userAccount, userPassword, checkPassword);
    }

    @PostMapping("/login")
    public User userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            return null;
        }

        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userAccount)) {
            return null;
        }

        return userService.doLogin(userAccount, userPassword, request);
    }

    @GetMapping("/current")
    public User getCurrent(HttpServletRequest request) {
        Object object = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) object;
        if (user == null) {
            return null;
        }

        Long userId = user.getId();
        // todo 校验用户是否合法
        User currentUser = userService.getById(userId);

        return userService.getSafetyUser(currentUser);
    }

    @GetMapping("/search")
    public List<User> searchUser(String userName, HttpServletRequest request) {

        if (!isAdmin(request)) {
            return new ArrayList<>();
        }

        QueryWrapper queryWrapper = new QueryWrapper();
        if (StringUtils.isNotBlank(userName)) {
            queryWrapper.like("username", userName);
        }

        List<User> userList= userService.list();
        return userList.stream().map(user -> {
            return userService.getSafetyUser(user);
        }).collect(Collectors.toList());
    }

    @PostMapping("/delete")
    public boolean deleteUser(@RequestBody long id, HttpServletRequest request) {
        if (!isAdmin(request)) {
            return false;
        }

        if (id <= 0) {
            return false;
        }

        return userService.removeById(id);
    }

    private boolean isAdmin(HttpServletRequest request) {
        Object object = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) object;

        return user != null && user.getUserRole() == ADMIN_ROLE;
    }
}
