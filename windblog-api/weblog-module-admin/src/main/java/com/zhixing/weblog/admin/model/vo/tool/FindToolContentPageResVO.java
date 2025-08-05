package com.zhixing.weblog.admin.model.vo.tool;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindToolContentPageResVO {
    @ApiModelProperty(value = "工具编号")
    private Long id;

    @ApiModelProperty(value = "工具菜单编号")
    private Long toolId;

    @ApiModelProperty(value = "工具名称")
    private String name;

    @ApiModelProperty(value = "工具图标")
    private String icon;

    @ApiModelProperty(value = "工具链接")
    private String link;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;
}
