package com.zhixing.weblog.admin.service;

import com.zhixing.weblog.admin.model.vo.dashboard.FindDashboardPublishArticleStatisticsReqVO;
import com.zhixing.weblog.comm.utils.Response;

public interface AdminDashboardService {
    /**
     * 获取仪表盘基础信息
     *
     * @return
     */
    Response findDashboardStatistics();

    /**
     * 获取文章发布统计
     *
     * @return
     */
    Response findDashboardPublishArticleStatistics(FindDashboardPublishArticleStatisticsReqVO findDashboardPublishArticleStatisticsReqVO);

    /**
     * 获取最近一周的 pv 访问量统计信息
     *
     * @return
     */
    Response findDashboardPVStatistics();
}
