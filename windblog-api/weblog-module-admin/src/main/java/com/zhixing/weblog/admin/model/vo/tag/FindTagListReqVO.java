package com.zhixing.weblog.admin.model.vo.tag;

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
@ApiModel(value = "下拉查询")
public class FindTagListReqVO {
    @ApiModelProperty(value = "标签名称")
    private String name;
}
