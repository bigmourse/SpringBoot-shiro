package com.xuan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xuan.mapper")
public class SpringbootshiroApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootshiroApplication.class, args);
    }

}
