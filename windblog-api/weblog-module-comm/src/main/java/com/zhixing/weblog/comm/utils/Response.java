package com.zhixing.weblog.comm.utils;

import com.zhixing.weblog.comm.exception.BaseExceptionInterface;
import com.zhixing.weblog.comm.exception.BizException;
import lombok.Data;

import java.io.Serializable;

/**
 * 定义请求返回参数
 *
 * @param <T>
 */
@Data
public class Response<T> implements Serializable {
    // 是否成功，默认为true
    private boolean success = true;
    // 响应消息
    private String message;
    // 响应码
    private String code;
    // 响应数据
    private T data;

    /**
     * 请求成功响应
     *
     * @param <T>
     * @return
     */
    public static <T> Response<T> success() {
        Response<T> response = new Response<>();
        return response;
    }

    public static <T> PageResponse<T> pageSuccess() {
        PageResponse pageResponse = new PageResponse();
        return pageResponse;
    }

    public static <T> Response<T> success(T data) {
        Response<T> response = new Response<>();
        response.setData(data);
        return response;
    }

    /**
     * 请求失败响应
     *
     * @param <T>
     * @return
     */
    public static <T> Response<T> fail() {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        return response;
    }

    public static <T> Response<T> fail(String errorMsg) {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setMessage(errorMsg);
        return response;
    }

    public static <T> Response<T> fail(String errorMsg, String errorCode) {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setMessage(errorMsg);
        response.setCode(errorCode);
        return response;
    }

    public static <T> Response<T> fail(BizException bizException) {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setMessage(bizException.getErrorMessage());
        response.setCode(bizException.getErrorCode());
        return response;
    }

    public static <T> Response<T> fail(BaseExceptionInterface baseExceptionInterface) {
        Response<T> response = new Response<>();
        response.setSuccess(false);
        response.setMessage(baseExceptionInterface.getErrorMessage());
        response.setCode(baseExceptionInterface.getErrorCode());
        return response;
    }
}
