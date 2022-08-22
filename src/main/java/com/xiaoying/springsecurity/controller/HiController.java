package com.xiaoying.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description：
 * @Date： 2022/2/18
 * @Author：小影
 */
@RestController
public class HiController {

    @GetMapping("/hi")
    public String hi() {
        System.out.println("hi,来了老弟");
        return "hi";
    }
}
