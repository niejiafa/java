package com.jack.demopro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.jack.demopro.mapper")
public class DemoProApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoProApplication.class, args);
    }

}
