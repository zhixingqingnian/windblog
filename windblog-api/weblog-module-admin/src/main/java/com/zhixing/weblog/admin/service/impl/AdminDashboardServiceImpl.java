package com.zhixing.weblog.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhixing.weblog.admin.model.vo.dashboard.FindDashboardPVStatisticsInfoResVO;
import com.zhixing.weblog.admin.model.vo.dashboard.FindDashboardPublishArticleStatisticsReqVO;
import com.zhixing.weblog.admin.model.vo.dashboard.FindDashboardStatisticsInfoResVO;
import com.zhixing.weblog.admin.service.AdminDashboardService;
import com.zhixing.weblog.comm.constant.Constants;
import com.zhixing.weblog.comm.domain.dos.ArticleDO;
import com.zhixing.weblog.comm.domain.dos.ArticlePublishCountDO;
import com.zhixing.weblog.comm.domain.dos.StatisticsArticlePVDO;
import com.zhixing.weblog.comm.domain.mapper.ArticleMapper;
import com.zhixing.weblog.comm.domain.mapper.CategoryMapper;
import com.zhixing.weblog.comm.domain.mapper.StatisticsArticlePVMapper;
import com.zhixing.weblog.comm.domain.mapper.TagMapper;
import com.zhixing.weblog.comm.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdminDashboardServiceImpl implements AdminDashboardService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private StatisticsArticlePVMapper articlePVMapper;

    @Override
    public Response findDashboardStatistics() {
        // 文章总数
        Long articleTotalCount = articleMapper.selectCount(Wrappers.emptyWrapper());
        // 分类总数
        Long categoryTotalCount = categoryMapper.selectCount(Wrappers.emptyWrapper());
        // 标签总数
        Long tagTotalCount = tagMapper.selectCount(Wrappers.emptyWrapper());
        // 总浏览量
        List<ArticleDO> articleDOS = articleMapper.selectAllReadNum();
        Long pvTotalCount = 0L;
        if (!CollectionUtils.isEmpty(articleDOS)) {
            pvTotalCount = articleDOS.stream().mapToLong(ArticleDO::getReadNum).sum();
        }
        // 组装成 VO 类
        FindDashboardStatisticsInfoResVO vo = FindDashboardStatisticsInfoResVO.builder()
                .articleTotalCount(articleTotalCount)
                .categoryTotalCount(categoryTotalCount)
                .tagTotalCount(tagTotalCount)
                .pvTotalCount(pvTotalCount)
                .build();
        return Response.success(vo);
    }

    @Override
    public Response findDashboardPublishArticleStatistics(FindDashboardPublishArticleStatisticsReqVO findDashboardPublishArticleStatisticsReqVO) {
        Byte type = findDashboardPublishArticleStatisticsReqVO.getType();
        // 当前日期
        LocalDate currDate = LocalDate.now();
        // 当前日期倒退
        LocalDate startDate = currDate.minusWeeks(1);
        if (type == 1) {
            startDate = currDate.minusMonths(1);
        } else if (type == 2) {
            startDate = currDate.minusYears(1);
        }
        // 查找指定时间内，每日发布的文章数量
        List<ArticlePublishCountDO> articlePublishCountDOS = articleMapper.selectDateArticlePublishCount(startDate, currDate.plusDays(1));
        // DO 转 Map
        Map<LocalDate, Long> dateArticleCountMap = articlePublishCountDOS.stream()
                .collect(Collectors.toMap(ArticlePublishCountDO::getDate, ArticlePublishCountDO::getCount));
        // 有序 MAp，返回的数据以升序排列
        Map<String, Long> resultMap = new LinkedHashMap<>();
        // 保留原始开始日期
        LocalDate originalStartDate = startDate;
        // 处理不同类型的数据
        if (type == 2) {
            // 按年统计
            Map<YearMonth, Long> monthLongMap = new TreeMap<>();
            for (LocalDate date = originalStartDate; !date.isAfter(currDate); date = date.plusDays(1)) {
                YearMonth month = YearMonth.from(date);
                Long count = dateArticleCountMap.getOrDefault(date, 0l);
                monthLongMap.merge(month, count, Long::sum);
            }
            // 确保包含所有月份
            YearMonth startMonth = YearMonth.from(originalStartDate);
            YearMonth endMonth = YearMonth.from(currDate);
            for (YearMonth month = startMonth; !month.isAfter(endMonth); month = month.plusMonths(1)) {
                // 格式化 yyyy-MM
                String monthKey = month.toString();
                resultMap.put(monthKey, monthLongMap.getOrDefault(month, 0L));
            }
        } else {
            // 按周/月统计 - 按天处理
            for (LocalDate date = originalStartDate;
                 !date.isAfter(currDate);
                 date = date.plusDays(1)) {
                Long count = dateArticleCountMap.get(date);
                // 格式化为 "yyyy-MM-dd" 作为键
                resultMap.put(date.toString(), Objects.isNull(count) ? 0 : count);
            }
        }
        return Response.success(resultMap);
    }

    @Override
    public Response findDashboardPVStatistics() {
        // 查询最近一周的 pv 访问量记录
        List<StatisticsArticlePVDO> articlePVDOS = articlePVMapper.selectLatestWeekRecords();

        Map<LocalDate, Long> pvDateCountMap = Maps.newHashMap();
        if (!CollectionUtils.isEmpty(articlePVDOS)) {
            // 转 Map，方便后续通过日期获取 PV 访问量
            pvDateCountMap = articlePVDOS.stream()
                    .collect(Collectors.toMap(StatisticsArticlePVDO::getPvDate, StatisticsArticlePVDO::getPvCount));
        }
        FindDashboardPVStatisticsInfoResVO vo = null;
        // 日期集合
        List<String> pvDates = Lists.newArrayList();
        // PV 集合
        List<Long> pvCounts = Lists.newArrayList();

        // 当前日期
        LocalDate currDate = LocalDate.now();
        // 一周前
        LocalDate temDate = currDate.minusWeeks(1);
        // 从一周前开始循环
        for (; temDate.isBefore(currDate) || temDate.isEqual(currDate); temDate = temDate.plusDays(1)) {
            // 设置对应日期的 pv 访问量
            pvDates.add(temDate.format(Constants.MONTH_DAY_FORMATTER));
            Long pvCount = pvDateCountMap.get(temDate);
            pvCounts.add(Objects.isNull(pvCount) ? 0 : pvCount);
        }
        vo = FindDashboardPVStatisticsInfoResVO.builder()
                .pvDates(pvDates)
                .pvCounts(pvCounts)
                .build();
        return Response.success(vo);
    }
}
