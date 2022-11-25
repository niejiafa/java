package com.jack.demopro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jack.demopro.model.User;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {

    List<User> selectList(Object o);
}