package com.jack.friends.service;
import java.util.Date;

import com.jack.friends.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    public void testAddUser() {
        User user = new User();
        user.setUsername("Rose");
        user.setUserAccount("Cat");
        user.setAvataUrl("https://profile.csdnimg.cn/3/C/4/3_weixin_44171249");
        user.setGender(0);
        user.setUserPassword("123");
        user.setPhone("18321274679");
        user.setEmail("18321274679@163.com");
        user.setUserStatus(0);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setIsDelete(0);

        boolean save = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(save);
    }

    @Test
    void userRegister() {
        String userAccount = "Mary";
        String password = "123456789";
        String checkPassword = "123456789";
//        long result = userService.userRegister(userAccount, password, checkPassword);
    }
}