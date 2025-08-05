package com.zhixing.weblog.admin.convert;

import com.zhixing.weblog.admin.model.vo.article.FindArticleDetailResVO;
import com.zhixing.weblog.comm.domain.dos.ArticleDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ArticleDetailConvert {
    // 初始化 convert 实例
    ArticleDetailConvert INSTANCE = Mappers.getMapper(ArticleDetailConvert.class);

    /**
     * DO 转 VO
     *
     * @param bean
     * @return
     */
    FindArticleDetailResVO convertDOToVO(ArticleDO bean);
}
