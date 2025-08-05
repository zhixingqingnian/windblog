package com.zhixing.weblog.admin.model.vo.article;

import com.zhixing.weblog.comm.model.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "分页查询文章 VO")
public class FindArticlePageReqVO extends BasePageQuery {
    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "创建起始时间")
    private LocalDate startDate;

    @ApiModelProperty(value = "创建结束时间")
    private LocalDate endDate;
}
