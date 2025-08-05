package com.zhixing.weblog.comm.model;

import lombok.Data;

@Data
public class BasePageQuery {
    // 当前页码
    private Long current = 1L;

    // 每页展示数量
    private Long pageSize = 10L;
}
