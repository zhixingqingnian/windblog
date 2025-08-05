package com.zhixing.weblog.jwt.filter;

import com.zhixing.weblog.jwt.utils.JwtTokenHelper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * Token 校验
 */
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    @Value("${jwt.tokenPrefix}")
    private String tokenPrefix;

    @Value("${jwt.tokenHeaderKey}")
    private String tokenHeaderKey;
    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/admin")) {
            // 从请求头中获取 key 为 Authorization 的值
            String header = request.getHeader(tokenHeaderKey);
            // 判断 value 的值是否以 Bearer 开头
            if (StringUtils.startsWith(header, tokenPrefix)) {
                // 截取 Token 令牌
                String token = StringUtils.substring(header, 7);
                log.info("Token：{}", token);
                // 判断 token 是否为空
                if (StringUtils.isNotBlank(token)) {
                    try {
                        // 校验 token 是否可用
                        jwtTokenHelper.validateToken(token);
                    } catch (SignatureException | MalformedJwtException | UnsupportedJwtException |
                             IllegalArgumentException e) {
                        authenticationEntryPoint.commence(request, response, new AuthenticationServiceException("Token 不可用"));
                        return;
                    } catch (ExpiredJwtException e) {
                        authenticationEntryPoint.commence(request, response, new AuthenticationServiceException("Token 已失效"));
                        return;
                    }
                    // 从 token 中解析出用户名
                    String username = jwtTokenHelper.getUsernameByToken(token);
                    if (StringUtils.isNotBlank(username) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
                        // 根据用户名获取用户详情
                        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                        // 将用户信息存入 authenticationToken 方便后续校验
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        // 将 authenticationToken 存入  ThreadLocal
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
            }
        }
        // 继续执行写一个过滤器
        filterChain.doFilter(request, response);
    }
}
