package com.zhixing.weblog.admin.controller;

import com.zhixing.weblog.admin.model.vo.dashboard.FindDashboardPublishArticleStatisticsReqVO;
import com.zhixing.weblog.admin.service.AdminDashboardService;
import com.zhixing.weblog.comm.aspect.ApiOperationLog;
import com.zhixing.weblog.comm.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/dashboard")
@Api(tags = "Admin 仪表盘")
public class AdminDashboardController {
    @Autowired
    private AdminDashboardService adminDashboardService;

    @PostMapping("/statistics")
    @ApiOperation(value = "获取后台仪表盘基础统计信息")
    @ApiOperationLog(description = "获取后台仪表盘基础统计信息")
    public Response findDashboardStatistics() {
        return adminDashboardService.findDashboardStatistics();
    }

    @PostMapping("/publishArticle/statistics")
    @ApiOperation(value = "获取后台仪表盘文章发布统计信息")
    @ApiOperationLog(description = "获取后台仪表盘文章发布统计信息")
    public Response findDashboardPublishArticleStatistics(@Validated @RequestBody FindDashboardPublishArticleStatisticsReqVO findDashboardPublishArticleStatisticsReqVO) {
        return adminDashboardService.findDashboardPublishArticleStatistics(findDashboardPublishArticleStatisticsReqVO);
    }

    @PostMapping("/pv/statistics")
    @ApiOperation(value = "获取后台仪表盘最近一周 PV 访问量信息")
    @ApiOperationLog(description = "获取后台仪表盘最近一周 PV 访问量信息")
    public Response findDashboardPVStatistics() {
        return adminDashboardService.findDashboardPVStatistics();
    }


}
