package com.xiaoying.springsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Description：
 * @Date： 2022/2/18
 * @Author：小影
 */
@Controller
public class MainController {

    @GetMapping("/login.html")
    public String login() {
        return "login";
    }

    @GetMapping("/ok")
    public String ok() {
        return "ok";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }

//    @GetMapping("/login.html")
//    public String login() {
//        return "login";
//    }
//
//    @GetMapping("/ok")
//    public String ok() {
//        return "ok";
//    }

}
