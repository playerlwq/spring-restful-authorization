package com.scienjus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Spring-Boot启动类
 * @author ScienJus
 * @date 2015/7/31.
 */
@SpringBootApplication
//@ComponentScan({"com.scienjus.utils"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
