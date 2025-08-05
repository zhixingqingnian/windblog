package com.zhixing.weblog.comm.aspect;

import java.lang.annotation.*;

/**
 * 接口注释
 */
// 注解在何时有效
@Retention(RetentionPolicy.RUNTIME)
// 在哪些地方使用 METHOD：方法上
@Target({ElementType.METHOD})
// 生成文档时展示关于注解的信息
@Documented
public @interface ApiOperationLog {
    /**
     * Api 功能描述
     *
     * @return
     */
    String description() default "";
}
