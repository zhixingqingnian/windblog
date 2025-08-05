package com.zhixing.weblog.admin.model.vo.article;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "发布文章 VO")
public class PublishArticleReqVO {
    @NotBlank(message = "文章标题不能为空")
    @Length(min = 1, max = 40, message = "文章标题字数在1~40个")
    @ApiModelProperty(value = "标题")
    private String title;

    @NotBlank(message = "文章内容不能为空")
    @ApiModelProperty(value = "内容")
    private String content;

    @NotBlank(message = "文章封面不能为空")
    @ApiModelProperty(value = "封面")
    private String cover;

    @ApiModelProperty(value = "摘要")
    private String summary;

    @NotNull(message = "文章分类不能为空")
    @ApiModelProperty(value = "分类")
    private Long categoryId;

    @NotEmpty(message = "文章标签不能为空")
    @ApiModelProperty(value = "标签")
    private List<String> tags;
}
