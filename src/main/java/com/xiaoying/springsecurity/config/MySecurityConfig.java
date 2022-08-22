package com.xiaoying.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description：springsecurity进行拦截配置，加密配置，内存存储用户信息
 * @Date： 2022/2/18
 * @Author：小影
 */
//@Configuration
//@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login.html")
                .loginProcessingUrl("/login").permitAll()
                .failureUrl("/error")
                .defaultSuccessUrl("/ok",true).permitAll()
                .usernameParameter("xx")
                .passwordParameter("oo")
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
//                        e.printStackTrace();
//                        request.setCharacterEncoding("UTF-8");
//                        response.setCharacterEncoding("UTF-8");
//                        response.setContentType("application/json");
//                        response.getWriter().print(e.getMessage());
//                        response.getWriter().flush();
//                        response.getWriter().close();
                        request.getSession().setAttribute("message",e.getMessage());
                        request.getRequestDispatcher("/error").forward(request,response);
                    }
                })
                .and()
                .csrf()
                .csrfTokenRepository(new HttpSessionCsrfTokenRepository());
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("123").password(new BCryptPasswordEncoder().encode("123")).roles("admin");
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
