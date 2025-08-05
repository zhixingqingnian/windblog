package com.zhixing.weblog.comm.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SelectResVO {

    // 下拉展示文字
    private String label;

    // 下拉的 value 值
    private Object value;
}
