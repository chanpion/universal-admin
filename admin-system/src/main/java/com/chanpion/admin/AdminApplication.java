package com.chanpion.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author April Chen
 * @date 2019/5/30 16:58
 */
@SpringBootApplication
@MapperScan("com.chanpion.admin.system")
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}
