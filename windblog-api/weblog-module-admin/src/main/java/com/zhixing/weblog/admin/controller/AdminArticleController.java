package com.zhixing.weblog.admin.controller;

import com.zhixing.weblog.admin.model.vo.article.*;
import com.zhixing.weblog.admin.service.AdminArticleService;
import com.zhixing.weblog.comm.aspect.ApiOperationLog;
import com.zhixing.weblog.comm.utils.PageResponse;
import com.zhixing.weblog.comm.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@Api(tags = "Admin 文章模块")
public class AdminArticleController {
    @Autowired
    private AdminArticleService adminArticleService;

    @PostMapping("/article/publish")
    @ApiOperation(value = "发布文章")
    @ApiOperationLog(description = "发布文章")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response publishArticle(@RequestBody @Validated PublishArticleReqVO publishArticleReqVO) {
        return adminArticleService.publishArticle(publishArticleReqVO);
    }

    @PostMapping("/article/update")
    @ApiOperation(value = "更新文章")
    @ApiOperationLog(description = "更新文章")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response updateArticle(@RequestBody @Validated UpdateArticleReqVO updateArticleReqVO) {
        return adminArticleService.updateArticle(updateArticleReqVO);
    }

    @PostMapping("/article/delete")
    @ApiOperation(value = "删除文章")
    @ApiOperationLog(description = "删除文章")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response deleteArticle(@RequestBody @Validated DeleteArticleReqVO deleteArticleReqVO) {
        return adminArticleService.deleteArticle(deleteArticleReqVO);
    }

    @PostMapping("/article/page")
    @ApiOperation(value = "分页文章数据")
    @ApiOperationLog(description = "分页文章数据")
    public Response<PageResponse<FindArticlePageResVO>> deleteArticle(@RequestBody @Validated FindArticlePageReqVO findArticlePageReqVO) {
        return adminArticleService.findArticlePage(findArticlePageReqVO);
    }

    @PostMapping("/article/detail")
    @ApiOperation(value = "文章详情")
    @ApiOperationLog(description = "文章详情")
    public Response findArticleDetail(@RequestBody @Validated FindArticleDetailReqVO findArticleDetailReqVO) {
        return adminArticleService.findArticleDetail(findArticleDetailReqVO);
    }
}
