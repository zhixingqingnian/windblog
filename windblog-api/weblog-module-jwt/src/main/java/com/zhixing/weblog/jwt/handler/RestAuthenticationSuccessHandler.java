package com.zhixing.weblog.jwt.handler;

import com.zhixing.weblog.comm.utils.Response;
import com.zhixing.weblog.jwt.model.LoginResVO;
import com.zhixing.weblog.jwt.utils.JwtTokenHelper;
import com.zhixing.weblog.jwt.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证成功处理
 */
@Component
@Slf4j
public class RestAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 从 authentication 对象中获取用户的的 UserDetails 实例
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        // 通过用户名生成 token
        String username = userDetails.getUsername();
        String token = jwtTokenHelper.generateToken(username);
        // 返回 token
        LoginResVO loginResVO = LoginResVO.builder().token(token).build();
        ResultUtil.success(response, Response.success(loginResVO));
    }
}
