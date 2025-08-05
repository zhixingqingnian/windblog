package com.zhixing.weblog.admin.model.vo.tag;

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
@ApiModel(value = "删除标签")
public class DeleteTagReqVO {
    @NotNull(message = "分类 ID 不能为空")
    @ApiModelProperty(value = "标签id")
    private Long id;
}
