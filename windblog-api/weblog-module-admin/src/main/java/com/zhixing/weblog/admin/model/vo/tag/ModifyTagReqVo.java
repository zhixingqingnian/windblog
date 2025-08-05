package com.zhixing.weblog.admin.model.vo.tag;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "修改标签")
public class ModifyTagReqVo {
    @NotNull(message = "标签编号不能为空")
    @ApiModelProperty(value = "标签编号")
    private Long id;

    @NotBlank(message = "标签名称不能为空")
    @ApiModelProperty(value = "标签名称")
    private String name;
}
