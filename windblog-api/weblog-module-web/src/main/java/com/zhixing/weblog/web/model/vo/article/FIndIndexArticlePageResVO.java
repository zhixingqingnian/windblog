package com.zhixing.weblog.web.model.vo.article;

import com.zhixing.weblog.web.model.vo.category.FindCategoryListResVO;
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
public class FIndIndexArticlePageResVO {
    private Long id;
    private String cover;
    private String title;
    private LocalDateTime createTime;
    private String summary;
    private FindCategoryListResVO category;
    private List<FindTagListResVO> tags;
}
