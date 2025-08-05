package com.zhixing.weblog.web.controller;

import com.zhixing.weblog.comm.aspect.ApiOperationLog;
import com.zhixing.weblog.comm.utils.PageResponse;
import com.zhixing.weblog.comm.utils.Response;
import com.zhixing.weblog.web.model.vo.article.FIndIndexArticlePageReqVO;
import com.zhixing.weblog.web.model.vo.article.FIndIndexArticlePageResVO;
import com.zhixing.weblog.web.model.vo.article.FindArticleDetailReqVO;
import com.zhixing.weblog.web.service.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "文章")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping("/article/page")
    @ApiOperation(value = "获取首页文章分页数据")
    @ApiOperationLog(description = "获取首页文章分页数据")
    public Response<PageResponse<FIndIndexArticlePageResVO>> findArticlePage(@RequestBody @Validated FIndIndexArticlePageReqVO fIndIndexArticlePageReqVO) {
        return articleService.findArticlePage(fIndIndexArticlePageReqVO);
    }

    @PostMapping("/article/detail")
    @ApiOperation(value = "获取首页文章详情")
    @ApiOperationLog(description = "获取首页文章详情")
    public Response findArticleDetail(@RequestBody @Validated FindArticleDetailReqVO findArticleDetailReqVO) {
        return articleService.findArticleDetail(findArticleDetailReqVO);
    }

}
