package com.zhixing.weblog.admin.model.vo.article;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "查询文章详情 VO")
public class FindArticleDetailResVO {
    @ApiModelProperty(value = "文章id")
    private Long id;

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "文章封面")
    private String cover;

    @ApiModelProperty(value = "文章内容")
    private String content;

    @ApiModelProperty(value = "文章分类")
    private Long categoryId;

    @ApiModelProperty(value = "文章标签")
    private List<Long> tagIds;

    @ApiModelProperty(value = "文章摘要")
    private String summary;
}
