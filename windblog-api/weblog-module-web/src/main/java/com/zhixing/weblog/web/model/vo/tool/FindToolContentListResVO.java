package com.zhixing.weblog.web.model.vo.tool;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindToolContentListResVO {
    private Long id;
    private Long toolId;
    private String name;
    private String link;
    private String icon;
}
