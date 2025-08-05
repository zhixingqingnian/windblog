package com.zhixing.weblog.admin.model.vo.article;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "删除文章 VO")
public class DeleteArticleReqVO {
    @NotNull(message = "文章id不能为空")
    private Long id;
}
