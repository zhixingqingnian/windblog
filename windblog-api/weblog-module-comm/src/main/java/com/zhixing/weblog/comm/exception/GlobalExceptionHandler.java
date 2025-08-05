package com.zhixing.weblog.comm.exception;

import com.zhixing.weblog.comm.enums.ResponseCodeEnum;
import com.zhixing.weblog.comm.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * 全局异常处理
 */
// 捕获应用中抛出的所有异常
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 捕获自定义业务异常
     *
     * @param servlet
     * @param exception
     * @return
     */
    @ExceptionHandler({BizException.class})
    @ResponseBody
    public Response<Object> handleBizException(HttpServletRequest servlet, BizException exception) {
        log.warn("{} request fail，errCode：{}，errorMessage：{}", servlet.getRequestURL(), exception.getErrorCode(), exception.getErrorMessage());
        return Response.fail(exception);
    }

    /**
     * 捕获其他异常
     *
     * @param servlet
     * @param exception
     * @return
     */
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public Response<Object> handleException(HttpServletRequest servlet, Exception exception) {
        log.error("{} request error, ", servlet.getRequestURI(), exception);
        return Response.fail(ResponseCodeEnum.SYSTEM_ERROR);
    }

    /**
     * 捕获参数校验异常
     *
     * @param servlet
     * @param exception
     * @return
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public Response<Object> handleMethodArgumentNotValidException(HttpServletRequest servlet, MethodArgumentNotValidException exception) {
        // 参数错误码
        String errorCode = ResponseCodeEnum.PARAM_NOT_VALID.getErrorCode();
        // 获取 BindingResult
        BindingResult bindingResult = exception.getBindingResult();
        StringBuffer sb = new StringBuffer();
        // 获取校验未通过字段，组合错误信息
        Optional.ofNullable(bindingResult.getFieldErrors()).ifPresent(errors -> {
            errors.forEach(error ->
                    sb.append(error.getField())
                            .append(" ")
                            .append(error.getDefaultMessage())
                            .append("，当前值：'")
                            .append(error.getRejectedValue())
                            .append("'；")
            );
        });
        // 错误信息
        String errorMessage = sb.toString();
        log.warn("{} request error, errorCode: {}, errorMessage: {}", servlet.getRequestURI(), errorCode, errorMessage);
        return Response.fail(errorMessage, errorCode);
    }

    /**
     * 捕获权限异常
     *
     * @param e
     * @throws AccessDeniedException
     */
    @ExceptionHandler({AccessDeniedException.class})
    public void throwAccessDeniedException(AccessDeniedException e) throws AccessDeniedException {
        log.info("=====捕获到 AccessDeniedException");
        throw e;
    }
}
