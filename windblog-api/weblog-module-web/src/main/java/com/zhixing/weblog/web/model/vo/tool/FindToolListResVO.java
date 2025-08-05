package com.zhixing.weblog.web.model.vo.tool;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindToolListResVO {
    private Long id;
    private String name;
    private String icon;
}
