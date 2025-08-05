package com.zhixing.weblog.web.model.vo.article;

import com.zhixing.weblog.comm.model.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "首页查询文章分页 VO")
public class FIndIndexArticlePageReqVO extends BasePageQuery {

    @ApiModelProperty(value = "分类id")
    private Long categoryId;

    @ApiModelProperty(value = "标签id")
    private Long tagId;
}
