package com.zhixing.weblog.admin.model.vo.category;

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
@ApiModel(value = "删除分类")
public class DeleteCategoryReqVO {

    @NotNull(message = "分类 ID 不能为空")
    @ApiModelProperty(value = "分类编号")
    private Long id;
}
