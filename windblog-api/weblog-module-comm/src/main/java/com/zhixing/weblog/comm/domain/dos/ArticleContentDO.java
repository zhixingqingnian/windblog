package com.zhixing.weblog.comm.domain.dos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("article_content")
public class ArticleContentDO {
    @TableId(type = IdType.AUTO)
    private Long id;

    private Long articleId;

    private String content;
}
