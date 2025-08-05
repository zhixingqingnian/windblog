package com.zhixing.weblog.comm.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhixing.weblog.comm.domain.dos.ArticleDO;
import com.zhixing.weblog.comm.domain.dos.ArticlePublishCountDO;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public interface ArticleMapper extends BaseMapper<ArticleDO> {
    default Page<ArticleDO> selectPageList(Long current, Long size, String title, LocalDate startDate, LocalDate endDate) {
        // 分页对象
        Page<ArticleDO> page = new Page<>(current, size);
        // 构建查询条件
        LambdaQueryWrapper<ArticleDO> wrapper = Wrappers.<ArticleDO>lambdaQuery()
                .like(StringUtils.isNotBlank(title), ArticleDO::getTitle, title)
                .ge(Objects.nonNull(startDate), ArticleDO::getCreateTime, startDate)
                .le(Objects.nonNull(endDate), ArticleDO::getCreateTime, endDate)
                .orderByDesc(ArticleDO::getCreateTime);
        return selectPage(page, wrapper);
    }

    default Page<ArticleDO> selectPageListByArticleIds(Long current, Long size, List<Long> articleIds) {
        // 分页对象
        Page<ArticleDO> page = new Page<>(current, size);
        // 构建查询条件
        LambdaQueryWrapper<ArticleDO> wrapper = Wrappers.<ArticleDO>lambdaQuery()
                .in(!CollectionUtils.isEmpty(articleIds), ArticleDO::getId, articleIds)
                .orderByDesc(ArticleDO::getCreateTime);
        return selectPage(page, wrapper);
    }

    /**
     * 查寻上一篇文章
     *
     * @param articleId
     * @return
     */
    default ArticleDO selectPreArticle(Long articleId) {
        return selectOne(Wrappers.<ArticleDO>lambdaQuery()
                .orderByDesc(ArticleDO::getId) // 按文章id升序排列
                .gt(ArticleDO::getId, articleId) // 查询比当前文章id大的
                .last("limit 1")); // 第一条记录为上一篇文章
    }

    /**
     * 查寻上一篇文章
     *
     * @param articleId
     * @return
     */
    default ArticleDO selectNextArticle(Long articleId) {
        return selectOne(Wrappers.<ArticleDO>lambdaQuery()
                .orderByDesc(ArticleDO::getId) // 按文章id升序排列
                .lt(ArticleDO::getId, articleId) // 查询比当前文章id小的
                .last("limit 1")); // 第一条记录为下一篇文章
    }

    /**
     * 阅读量 +1
     *
     * @param articleId
     * @return
     */
    default int increaseReadNum(Long articleId) {
        return update(null, Wrappers.<ArticleDO>lambdaUpdate()
                .setSql("read_num = read_num + 1")
                .eq(ArticleDO::getId, articleId));
    }

    /**
     * 查询所有文章的浏览量
     *
     * @return
     */
    default List<ArticleDO> selectAllReadNum() {
        return selectList(Wrappers.<ArticleDO>lambdaQuery()
                .select(ArticleDO::getReadNum));

    }

    @Select("SELECT DATE(create_time) AS date,COUNT(*) AS count\n" +
            "FROM article\n" +
            "WHERE create_time >= #{startDate} AND create_time < #{endDate}\n" +
            "GROUP BY DATE(create_time)")
    List<ArticlePublishCountDO> selectDateArticlePublishCount(LocalDate startDate, LocalDate endDate);

}
