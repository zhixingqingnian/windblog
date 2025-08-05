package com.zhixing.weblog.web.model.vo.article;

import com.zhixing.weblog.web.model.vo.tag.FindTagListResVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindArticleDetailResVO {
    // 文章标题
    private String title;
    // 文章内容
    private String content;
    // 发布时间
    private LocalDateTime createTime;
    // 分类id
    private Long categoryId;
    // 分类名称
    private String categoryName;
    // 阅读量
    private Long readNum;
    // 标签集合
    private List<FindTagListResVO> tags;
    // 上一篇文章
    private FindPreNextArticleResVO preArticle;
    // 下一篇文章
    private FindPreNextArticleResVO nextArticle;
}
