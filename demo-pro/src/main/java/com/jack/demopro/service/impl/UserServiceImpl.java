package com.jack.demopro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jack.demopro.model.domain.User;
import com.jack.demopro.mapper.UserMapper;
import com.jack.demopro.service.UserService;
import org.springframework.stereotype.Service;

/**
* @author zhoushaoxiang
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2022-11-25 20:32:58
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

}




