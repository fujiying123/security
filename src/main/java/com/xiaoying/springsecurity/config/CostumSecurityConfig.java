package com.xiaoying.springsecurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import javax.print.attribute.standard.MediaSize;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Collections;

/**
 * @Description：自定义配置secruity加密配置，jdbc存储用户信息
 * @Date： 2022/2/20
 * @Author：小影
 */
@Configuration
@EnableWebSecurity
public class CostumSecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().
                anyRequest().authenticated()
                .and().formLogin().loginPage("/login.html")
                .loginProcessingUrl("/login").permitAll()
                .failureUrl("/error")
                .defaultSuccessUrl("/ok", true).permitAll()
                .usernameParameter("xx")
                .passwordParameter("oo")
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
                        request.getSession().setAttribute("message",e.getMessage());
                        request.getRequestDispatcher("/error").forward(request,response);
                    }
                })
                .and()
                .csrf()
                .csrfTokenRepository(new HttpSessionCsrfTokenRepository());
    }


    /**
    * @Description: 在内存中保存用户信息
    * @Param:
    * @return:
    * @Author: 小影
    * @Date: 2022/2/20
    */
//    @Bean
//    public UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        User user = new User("a", passwordEncoder().encode("1"), true, true, true, true, Collections.singletonList(new SimpleGrantedAuthority("zz")));
//        manager.createUser(user);
//        manager.createUser(User.withUsername("122").password(passwordEncoder().encode("123")).roles("admin").build());
//        return manager;
//    }


    @Autowired
    DataSource dataSource;

    @Bean
    protected UserDetailsService userDetailsService() {
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
        if(manager.userExists("xiaoying")) {
            System.out.println("用户已注册");
        } else {
            manager.createUser(User.withUsername("xiaoying").password("123").roles("admin").build());
        }
        return manager;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
