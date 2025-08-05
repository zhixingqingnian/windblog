package com.zhixing.weblog.comm.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zhixing.weblog.comm.domain.dos.ArticleContentDO;

public interface ArticleContentMapper extends BaseMapper<ArticleContentDO> {
    /**
     * 根据文章id删除记录
     *
     * @param articleId
     * @return
     */
    default int deleteByArticleId(Long articleId) {
        return delete(Wrappers.<ArticleContentDO>lambdaQuery()
                .eq(ArticleContentDO::getArticleId, articleId));
    }

    /**
     * 根据文章id查询
     *
     * @param articleId
     * @return
     */
    default ArticleContentDO selectByArticleId(Long articleId) {
        return selectOne(Wrappers.<ArticleContentDO>lambdaQuery()
                .eq(ArticleContentDO::getArticleId, articleId));
    }

    /**
     * 通过文章id更新
     *
     * @param articleContentDO
     * @return
     */
    default int updateByArticleId(ArticleContentDO articleContentDO) {
        return update(articleContentDO,
                Wrappers.<ArticleContentDO>lambdaUpdate()
                        .eq(ArticleContentDO::getArticleId, articleContentDO.getArticleId()));
    }
}
