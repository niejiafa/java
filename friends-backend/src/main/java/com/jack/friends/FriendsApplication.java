package com.jack.friends;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.jack.demopro.mapper")
public class FriendsApplication {

    public static void main(String[] args) {
        SpringApplication.run(FriendsApplication.class, args);
    }

}
