package com.zhixing.weblog.admin.model.vo.dashboard;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "查询仪表盘基础统计信息 VO")
public class FindDashboardStatisticsInfoResVO {

    @ApiModelProperty(value = "文章总数")
    private Long articleTotalCount;

    @ApiModelProperty(value = "分类总数")
    private Long categoryTotalCount;

    @ApiModelProperty(value = "标签总数")
    private Long tagTotalCount;

    @ApiModelProperty(value = "总浏览量")
    private Long pvTotalCount;
}
