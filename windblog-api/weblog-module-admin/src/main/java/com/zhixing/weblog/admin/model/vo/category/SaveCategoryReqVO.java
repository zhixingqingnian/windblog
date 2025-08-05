package com.zhixing.weblog.admin.model.vo.category;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "添加/编辑分类")
public class SaveCategoryReqVO {
    @ApiModelProperty(value = "分类编号")
    private Long id;

    @NotBlank(message = "分类名称不能为空")
    @Length(min = 1, max = 20, message = "分类名称字数限制在 1~20 之间")
    @ApiModelProperty(value = "分类名称")
    private String name;
}
