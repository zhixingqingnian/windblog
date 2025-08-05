package com.zhixing.weblog.admin.model.vo.article;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "查询文章详情 VO")
public class FindArticleDetailReqVO {
    @NotNull(message = "文章id不能为空")
    @ApiModelProperty(value = "文章id")
    private Long id;
}
