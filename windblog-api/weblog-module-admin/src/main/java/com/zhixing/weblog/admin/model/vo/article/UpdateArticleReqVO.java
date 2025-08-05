package com.zhixing.weblog.admin.model.vo.article;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "更新文章 VO")
public class UpdateArticleReqVO {
    @NotNull(message = "文章id不能为空")
    @ApiModelProperty(value = "文章id")
    private Long id;

    @NotBlank(message = "文章标题不能为空")
    @ApiModelProperty(value = "文章标题")
    private String title;

    @NotBlank(message = "文章封面不能为空")
    @ApiModelProperty(value = "文章封面")
    private String cover;

    @NotBlank(message = "文章内容不能为空")
    @ApiModelProperty(value = "文章内容")
    private String content;

    @NotNull(message = "文章分类不能为空")
    @ApiModelProperty(value = "文章分类")
    private Long categoryId;

    @NotEmpty(message = "文章标签不能为空")
    @ApiModelProperty(value = "文章标签")
    private List<String> tags;

    @ApiModelProperty(value = "文章摘要")
    private String summary;
}
