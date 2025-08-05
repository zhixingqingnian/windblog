package com.zhixing.weblog.admin.service;

import com.zhixing.weblog.admin.model.vo.article.*;
import com.zhixing.weblog.comm.utils.PageResponse;
import com.zhixing.weblog.comm.utils.Response;

public interface AdminArticleService {
    /**
     * 发布文章
     *
     * @param publishArticleReqVO
     * @return
     */
    Response publishArticle(PublishArticleReqVO publishArticleReqVO);

    /**
     * 删除文章
     *
     * @param deleteArticleReqVO
     * @return
     */
    Response deleteArticle(DeleteArticleReqVO deleteArticleReqVO);

    /**
     * 分页查询文章数据
     *
     * @param findArticlePageReqVO
     * @return
     */
    Response<PageResponse<FindArticlePageResVO>> findArticlePage(FindArticlePageReqVO findArticlePageReqVO);

    /**
     * 查询文章详情
     *
     * @param findArticleDetailReqVO
     * @return
     */
    Response findArticleDetail(FindArticleDetailReqVO findArticleDetailReqVO);

    /**
     * 更新文章
     *
     * @param updateArticleReqVO
     * @return
     */
    Response updateArticle(UpdateArticleReqVO updateArticleReqVO);
}
