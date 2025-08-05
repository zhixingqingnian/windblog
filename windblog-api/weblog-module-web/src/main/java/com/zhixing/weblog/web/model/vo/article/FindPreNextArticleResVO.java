package com.zhixing.weblog.web.model.vo.article;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindPreNextArticleResVO {
    // 文章id
    private Long articleId;
    // 文章标题
    private String articleTitle;
}
