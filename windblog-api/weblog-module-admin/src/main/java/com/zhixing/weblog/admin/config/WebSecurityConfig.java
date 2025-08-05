package com.zhixing.weblog.admin.config;

import com.zhixing.weblog.jwt.config.JwtAuthenticationSecurityConfig;
import com.zhixing.weblog.jwt.filter.TokenAuthenticationFilter;
import com.zhixing.weblog.jwt.handler.RestAccessDeniedHandler;
import com.zhixing.weblog.jwt.handler.RestAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security 配置类
 */
@Configuration
// 启动 Spring Security
@EnableWebSecurity
// 启用方法级别的安全性设置
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationSecurityConfig jwtAuthenticationSecurityConfig;

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private RestAccessDeniedHandler restAccessDeniedHandler;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // 禁用 csrf
        httpSecurity.csrf().disable()
                // 禁用表单登录
                .formLogin().disable()
                // 设置用户登录认证相关配置
                .apply(jwtAuthenticationSecurityConfig)
                .and()
                .authorizeHttpRequests()
                // 认证所有以 /admin 为前缀的地址
                .mvcMatchers("/admin/**").authenticated()
                // 其余放行
                .anyRequest().permitAll()
                .and()
                // 处理用户未登录访问受保护资源
                .httpBasic().authenticationEntryPoint(restAuthenticationEntryPoint)
                // 处理用户登录成功访问受保护资源，权限不足
                .and()
                .exceptionHandling().accessDeniedHandler(restAccessDeniedHandler)
                .and()
                // 前后端分离，无需创建会话
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 将 Token 校验过滤器添加到用户认证过滤器之前
                .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    /**
     * token 校验过滤器
     *
     * @return
     */
    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter();
    }
}
