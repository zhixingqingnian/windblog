package com.zhixing.weblog.admin.model.vo.user;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "获取用户信息")
public class FindUserInfoRspVO {
    // 用户名
    private String username;
}
