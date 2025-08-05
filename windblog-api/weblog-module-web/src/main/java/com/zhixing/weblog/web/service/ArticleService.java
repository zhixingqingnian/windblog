package com.zhixing.weblog.web.service;

import com.zhixing.weblog.comm.utils.Response;
import com.zhixing.weblog.web.model.vo.article.FIndIndexArticlePageReqVO;
import com.zhixing.weblog.web.model.vo.article.FindArticleDetailReqVO;

public interface ArticleService {
    /**
     * 获取首页文章分页数据
     *
     * @param fIndIndexArticlePageReqVO
     * @return
     */
    Response findArticlePage(FIndIndexArticlePageReqVO fIndIndexArticlePageReqVO);

    /**
     * 获取文章详情
     *
     * @param findArticleDetailReqVO
     * @return
     */
    Response findArticleDetail(FindArticleDetailReqVO findArticleDetailReqVO);
}
