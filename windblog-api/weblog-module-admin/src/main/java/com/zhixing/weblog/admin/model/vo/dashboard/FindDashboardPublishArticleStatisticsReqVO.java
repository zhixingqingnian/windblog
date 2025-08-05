package com.zhixing.weblog.admin.model.vo.dashboard;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "仪表盘发布文章统计信息 VO")
public class FindDashboardPublishArticleStatisticsReqVO {

    @NotNull(message = "时间周期不能为空")
    @ApiModelProperty(value = "查询类型 0：周；1：月；2：年")
    private Byte type;
}
