package com.xiaoying.springsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
* @Description: 防止csrf攻击，开启SpringSecurity
* @Param:
* @return:
* @Author: 小影
* @Date: 2022/2/18
*/
@SpringBootApplication
public class SpringSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityApplication.class, args);
    }

}
