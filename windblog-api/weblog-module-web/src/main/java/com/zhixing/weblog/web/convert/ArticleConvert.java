package com.zhixing.weblog.web.convert;

import com.zhixing.weblog.comm.domain.dos.ArticleDO;
import com.zhixing.weblog.web.model.vo.article.FIndIndexArticlePageResVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ArticleConvert {
    /**
     * 初始化 convert 实例
     */
    ArticleConvert INSTANCE = Mappers.getMapper(ArticleConvert.class);

    /**
     * 将 DO 转 VO
     *
     * @param bean
     * @return
     */
    FIndIndexArticlePageResVO convertDOToVO(ArticleDO bean);

}
