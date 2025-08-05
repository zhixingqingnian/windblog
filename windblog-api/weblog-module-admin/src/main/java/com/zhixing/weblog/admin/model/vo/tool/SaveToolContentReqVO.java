package com.zhixing.weblog.admin.model.vo.tool;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "添加/编辑工具内容")
public class SaveToolContentReqVO {
    @ApiModelProperty(value = "工具编号")
    private Long id;

    @NotNull(message = "所属菜单不能为空")
    @ApiModelProperty(value = "菜单编号")
    private Long toolId;

    @NotBlank(message = "工具名称不能为空")
    @Length(min = 1, max = 20, message = "分类名称字数限制在 1~20 之间")
    @ApiModelProperty(value = "工具名称")
    private String name;

    @NotBlank(message = "工具链接不能为空")
    @ApiModelProperty(value = "工具链接")
    private String link;

    @NotBlank(message = "工具图标不能为空")
    @ApiModelProperty(value = "工具图标")
    private String icon;
}
