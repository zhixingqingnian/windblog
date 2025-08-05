package com.zhixing.weblog.admin.event.subscriber;

import com.zhixing.weblog.admin.event.ReadArticleEvent;
import com.zhixing.weblog.comm.domain.mapper.ArticleMapper;
import com.zhixing.weblog.comm.domain.mapper.StatisticsArticlePVMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Slf4j
public class ReadArticleSubscriber implements ApplicationListener<ReadArticleEvent> {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private StatisticsArticlePVMapper statisticsArticlePVMapper;

    @Override
    @Async("threadPoolTaskExecutor")
    public void onApplicationEvent(ReadArticleEvent event) {
        Long articleId = event.getArticleId();
        String threadName = Thread.currentThread().getName();
        log.info("---》 threadName：{}", threadName);
        log.info("---》 文章阅读事件消费成功：articleId：{}", articleId);
        // 执行文章阅读量 +1
        articleMapper.increaseReadNum(articleId);
        log.info("==> 文章阅读量 +1 操作成功，articleId: {}", articleId);
        // 当日文章 PV 访问量 +1
        LocalDate currDate = LocalDate.now();
        statisticsArticlePVMapper.increasePVCount(currDate);
        log.info("==> 当日文章 PV 访问量 +1 操作成功，date: {}", currDate);
    }

}
