package com.zhixing.weblog.jwt.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhixing.weblog.comm.utils.Response;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 参数设置
 */
public class ResultUtil {
    /**
     * 成功参数设置
     *
     * @param response
     * @param result
     * @throws IOException
     */
    public static void success(HttpServletResponse response, Response<?> result) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        writer.write(mapper.writeValueAsString(result));
        writer.flush();
        writer.close();
    }

    /**
     * 失败参数设置
     *
     * @param response
     * @param result
     * @throws IOException
     */
    public static void fail(HttpServletResponse response, Response<?> result) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        writer.write(mapper.writeValueAsString(result));
        writer.flush();
        writer.close();
    }

    /**
     * 失败参数设置
     *
     * @param response
     * @param status
     * @param result
     * @throws IOException
     */
    public static void fail(HttpServletResponse response, int status, Response<?> result) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setStatus(status);
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        writer.write(mapper.writeValueAsString(result));
        writer.flush();
        writer.close();
    }
}
