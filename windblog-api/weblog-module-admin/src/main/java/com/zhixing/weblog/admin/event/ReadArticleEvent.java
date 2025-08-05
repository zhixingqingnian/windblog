package com.zhixing.weblog.admin.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ReadArticleEvent extends ApplicationEvent {
    // 文章 id
    private Long articleId;

    public ReadArticleEvent(Object source, Long articleId) {
        super(source);
        this.articleId = articleId;
    }
}
