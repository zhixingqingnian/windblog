package com.zhixing.weblog.comm.aspect;

import com.zhixing.weblog.comm.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 接口切面
 */
// 标记此类为切面
@Aspect
// 标记为组件
@Component
// 可直接使用log打印日志
@Slf4j
public class ApiOperationLogAspect {
    // 以自定义 @ApiOperationLog 注解为切点，凡是添加 @ApiOperationLog 的方法，都会执行环绕中的代码
    @Pointcut("@annotation(com.zhixing.weblog.comm.aspect.ApiOperationLog)")
    public void apiOperationLog() {
    }

    /**
     * 环绕
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("apiOperationLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            // 请求开始时间
            long startTime = System.currentTimeMillis();
            // MDC跟踪id
            MDC.put("traceId", UUID.randomUUID().toString());
            // 获取被请求的类和方法
            String className = joinPoint.getTarget().getClass().getSimpleName();
            String methodName = joinPoint.getSignature().getName();
            // 请求入参
            Object[] args = joinPoint.getArgs();
            // 入参转JSON字符串
            String argsJsonStr = Arrays.stream(args).map(toJsonStr()).collect(Collectors.joining(", "));
            // 功能描述信息
            String description = getApiOperationLogDescription(joinPoint);
            // 请求参数日志
            log.info("===== 请求开始：[{}]，入参：{}，请求类：{}，请求方法：{} =====", description, argsJsonStr, className, methodName);
            // 执行切点方法
            Object result = joinPoint.proceed();
            // 执行耗时
            long executionTime = System.currentTimeMillis() - startTime;
            // 返回参数日志
            log.info("===== 请求结束：[{}]，耗时：{}ms，返回参数：{} =====", description, executionTime, JsonUtil.toJsonString(result));
            return result;
        } finally {
            MDC.clear();
        }
    }

    /**
     * 获取注解描述信息
     *
     * @param joinPoint
     * @return
     */
    private String getApiOperationLogDescription(ProceedingJoinPoint joinPoint) {
        // 1.从ProceedingJoinPoint获取MethodSignature
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 2.使用MethodSignature获取当前被注解的Method
        Method method = signature.getMethod();
        // 3.从Method中提取LogExecution注解
        ApiOperationLog apiOperationLog = method.getAnnotation(ApiOperationLog.class);
        // 从LogExecution注解中获取description属性
        return apiOperationLog.description();
    }

    /**
     * 转JSON字符串
     *
     * @return
     */
    private Function<Object, String> toJsonStr() {
        return arg -> JsonUtil.toJsonString(arg);
    }

}
