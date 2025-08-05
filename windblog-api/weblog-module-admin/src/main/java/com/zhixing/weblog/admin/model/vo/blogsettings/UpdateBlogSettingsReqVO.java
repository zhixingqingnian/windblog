package com.zhixing.weblog.admin.model.vo.blogsettings;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "博客基础信息修改")
public class UpdateBlogSettingsReqVO {
    @NotBlank(message = "博客logo不能为空")
    @ApiModelProperty(value = "博客logo")
    private String logo;

    @NotBlank(message = "博客名称不能为空")
    @ApiModelProperty(value = "博客名称")
    private String name;

    @NotBlank(message = "作者名称不能为空")
    @ApiModelProperty(value = "作者名称")
    private String author;

    @NotBlank(message = "博客简介不能为空")
    @ApiModelProperty(value = "博客简介")
    private String introduction;

    @NotBlank(message = "作者头像不能为空")
    @ApiModelProperty(value = "作者头像")
    private String avatar;

    private String githubHomepage;

    private String csdnHomepage;

    private String giteeHomepage;

    private String juejinHomepage;

}
