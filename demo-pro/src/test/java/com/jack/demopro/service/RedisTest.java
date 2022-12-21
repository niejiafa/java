package com.jack.demopro.service;
import java.util.Date;

import com.jack.demopro.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;

@SpringBootTest
public class RedisTest {

    @Resource RedisTemplate redisTemplate;

    @Test
    void test() {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("jackString", "string");
        valueOperations.set("jackInt", "1");
        valueOperations.set("jackDouble", "2.0");

        Object jackString = valueOperations.get("jackString");
        Assertions.assertTrue("string".equals((String)jackString));
    }
}
