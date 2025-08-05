package com.zhixing.weblog.admin.model.vo.tag;

import com.zhixing.weblog.comm.model.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "分页查询标签")
public class FindTagPageReqVO extends BasePageQuery {
    @ApiModelProperty(value = "标签名称")
    private String name;

    @ApiModelProperty(value = "创建起始时间")
    private LocalDate startDate;

    @ApiModelProperty(value = "创建结束时间")
    private LocalDate endDate;
}
