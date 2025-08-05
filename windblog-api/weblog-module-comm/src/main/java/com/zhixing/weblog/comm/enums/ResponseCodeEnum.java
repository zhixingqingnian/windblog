package com.zhixing.weblog.comm.enums;

import com.zhixing.weblog.comm.exception.BaseExceptionInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 异常信息枚举
 */
@Getter
//生成构造方法
@AllArgsConstructor
public enum ResponseCodeEnum implements BaseExceptionInterface {
    // ----------- 通用异常状态码 -----------
    SYSTEM_ERROR("10000", "出错啦，后台小哥正在努力修复中..."),
    PARAM_NOT_VALID("10001", "参数错误"),
    LOGIN_FAIL("20000", "登录失败"),
    USERNAME_OR_PWD_ERROR("20001", "用户名或密码错误"),
    UNAUTHORIZED("20002", "无访问权限，请先登录！"),
    FORBIDDEN("20003", "演示账号仅支持查询操作！"),
    USERNAME_NOT_FOUND("20004", "该用户不存在"),
    CATEGORY_NAME_IS_EXISTED("20005", "该分类已存在，请勿重复添加！"),
    TOOL_NAME_IS_EXISTED("20005", "该工具已存在，请勿重复添加！"),
    FILE_UPLOAD_FAILED("20006", "文件上传失败！"),
    CATEGORY_NOT_FOUND("20007", "分类不存在"),
    ARTICLE_NOT_FOUND("20007", "文章不存在"),
    TOOL_ID_IS_BIND("20008", "该菜单已被使用，请检查后删除！"),
    // ----------- 业务异常状态码 -----------
    PRODUCT_NOT_FOUND("20000", "该产品不存在（测试使用）"),
    ;
    // 异常码
    private String errorCode;
    // 异常信息
    private String errorMessage;
}
