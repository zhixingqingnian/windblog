package com.zhixing.weblog.web.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.zhixing.weblog.admin.event.ReadArticleEvent;
import com.zhixing.weblog.comm.domain.dos.*;
import com.zhixing.weblog.comm.domain.mapper.*;
import com.zhixing.weblog.comm.enums.ResponseCodeEnum;
import com.zhixing.weblog.comm.exception.BizException;
import com.zhixing.weblog.comm.utils.PageResponse;
import com.zhixing.weblog.comm.utils.Response;
import com.zhixing.weblog.web.convert.ArticleConvert;
import com.zhixing.weblog.web.markdown.MarkdownHelper;
import com.zhixing.weblog.web.model.vo.article.*;
import com.zhixing.weblog.web.model.vo.category.FindCategoryListResVO;
import com.zhixing.weblog.web.model.vo.tag.FindTagListResVO;
import com.zhixing.weblog.web.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ArticleContentMapper articleContentMapper;
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    private ArticleCategoryRelMapper articleCategoryRelMapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private ArticleTagRelMapper articleTagRelMapper;

    @Autowired
    private ApplicationEventPublisher eventPublisher;


    @Override
    public Response<PageResponse<FIndIndexArticlePageResVO>> findArticlePage(FIndIndexArticlePageReqVO fIndIndexArticlePageReqVO) {
        // 分页、分类、标签
        Long current = fIndIndexArticlePageReqVO.getCurrent();
        Long pageSize = fIndIndexArticlePageReqVO.getPageSize();
        Long categoryRelId = fIndIndexArticlePageReqVO.getCategoryId();
        Long tagRelId = fIndIndexArticlePageReqVO.getTagId();

        // 文章、文章分类、文章标签id
        List<Long> articleFilterIds = new ArrayList<>();
        List<Long> articleCategoryIds = new ArrayList<>();
        List<Long> articleTagIds = new ArrayList<>();

        // 获取关联分类文章id
        if (Objects.nonNull(categoryRelId)) {
            List<ArticleCategoryRelDO> articleCategoryRelDOS = articleCategoryRelMapper.selectByCategoryId(categoryRelId);
            articleCategoryIds = articleCategoryRelDOS.stream().map(articleCategoryRelDO -> articleCategoryRelDO.getArticleId()).collect(Collectors.toList());
            articleFilterIds = articleCategoryIds;
        }
        // 获取关联标签文章id
        if (Objects.nonNull(tagRelId)) {
            List<ArticleTagRelDO> articleTagRelDOS = articleTagRelMapper.selectByTagId(tagRelId);
            articleTagIds = articleTagRelDOS.stream().map(articleTagRelDO -> articleTagRelDO.getArticleId()).collect(Collectors.toList());
            articleFilterIds = articleTagIds;
        }
        // 取交集
        if (Objects.nonNull(categoryRelId) && Objects.nonNull(tagRelId)) {
            articleFilterIds = articleCategoryIds.stream()
                    .distinct()
                    .filter(articleTagIds::contains)
                    .collect(Collectors.toList());
        }
        // 文章主体记录
        Page<ArticleDO> articleDOPage = articleMapper.selectPageListByArticleIds(current, pageSize, articleFilterIds);
        if ((Objects.nonNull(categoryRelId) || Objects.nonNull(tagRelId)) && CollectionUtils.isEmpty(articleFilterIds)) {
            articleDOPage = new Page<>();
        }
        // 返回分页数据
        List<ArticleDO> articleDOS = articleDOPage.getRecords();
        // DO 转 VO
        List<FIndIndexArticlePageResVO> vos = null;
        if (!CollectionUtils.isEmpty(articleDOS)) {
            vos = articleDOS.stream()
                    .map(articleDO -> ArticleConvert.INSTANCE.convertDOToVO(articleDO))
                    .collect(Collectors.toList());

            // 拿到所有文章的 id 集合
            List<Long> articleIds = articleDOS.stream().map(ArticleDO::getId).collect(Collectors.toList());

            // 设置文章所属分类
            List<CategoryDO> categoryDOS = categoryMapper.selectList(Wrappers.lambdaQuery());
            Map<Long, String> categoryIdNameMap = categoryDOS.stream().collect(Collectors.toMap(CategoryDO::getId, CategoryDO::getName));
            List<ArticleCategoryRelDO> articleCategoryRelDOS = articleCategoryRelMapper.selectByArticleIds(articleIds);
            // 过滤关联数据
            vos.forEach(vo -> {
                Long currArticleId = vo.getId();
                Optional<ArticleCategoryRelDO> optional = articleCategoryRelDOS.stream().filter(rel -> Objects.equals(rel.getArticleId(), currArticleId)).findAny();
                // 若不为空
                if (optional.isPresent()) {
                    ArticleCategoryRelDO articleCategoryRelDO = optional.get();
                    Long categoryId = articleCategoryRelDO.getCategoryId();
                    String categoryName = categoryIdNameMap.get(categoryId);
                    FindCategoryListResVO findCategoryListResVO = FindCategoryListResVO.builder()
                            .id(categoryId)
                            .name(categoryName)
                            .build();
                    // 设置当前 vo 类中
                    vo.setCategory(findCategoryListResVO);
                }
            });

            // 设置文章所属标签
            List<TagDO> tagDOS = tagMapper.selectList(Wrappers.lambdaQuery());
            Map<Long, String> tagIdNameMap = tagDOS.stream().collect(Collectors.toMap(TagDO::getId, TagDO::getName));
            List<ArticleTagRelDO> articleTagRelDOS = articleTagRelMapper.selectByArticleIds(articleIds);
            vos.forEach(vo -> {
                Long currArticleId = vo.getId();
                List<ArticleTagRelDO> articleTagRelDOList = articleTagRelDOS.stream().filter(rel -> Objects.equals(rel.getArticleId(), currArticleId)).collect(Collectors.toList());
                List<FindTagListResVO> findTagListResVOS = Lists.newArrayList();
                articleTagRelDOList.forEach(articleTagRelDO -> {
                    Long tagId = articleTagRelDO.getTagId();
                    String tagName = tagIdNameMap.get(tagId);

                    FindTagListResVO findTagListResVO = FindTagListResVO.builder()
                            .id(tagId)
                            .name(tagName)
                            .build();
                    findTagListResVOS.add(findTagListResVO);
                });
                vo.setTags(findTagListResVOS);
            });
        }
        return PageResponse.success(articleDOPage, vos);
    }

    @Override
    public Response findArticleDetail(FindArticleDetailReqVO findArticleDetailReqVO) {
        Long articleId = findArticleDetailReqVO.getArticleId();
        ArticleDO articleDO = articleMapper.selectById(articleId);

        // 判断文章是否存在
        if (Objects.isNull(articleDO)) {
            log.warn("---》 该文章不存在，articleId：{}", articleId);
            throw new BizException(ResponseCodeEnum.ARTICLE_NOT_FOUND);
        }

        // 查询正文
        ArticleContentDO articleContentDO = articleContentMapper.selectByArticleId(articleId);
        // DO 转 VO
        FindArticleDetailResVO vo = FindArticleDetailResVO.builder()
                .title(articleDO.getTitle())
                .createTime(articleDO.getCreateTime())
                .content(MarkdownHelper.convertMarkdownToHtml(articleContentDO.getContent()))
                .readNum(articleDO.getReadNum())
                .build();

        // 查询所属分类
        ArticleCategoryRelDO articleCategoryRelDO = articleCategoryRelMapper.selectByArticleId(articleId);
        CategoryDO categoryDO = categoryMapper.selectById(articleCategoryRelDO.getCategoryId());
        vo.setCategoryId(categoryDO.getId());
        vo.setCategoryName(categoryDO.getName());

        // 查询标签
        List<ArticleTagRelDO> articleTagRelDOS = articleTagRelMapper.selectByArticleId(articleId);
        List<Long> tagIds = articleTagRelDOS.stream().map(ArticleTagRelDO::getTagId).collect(Collectors.toList());
        List<TagDO> tagDOS = tagMapper.selectByIds(tagIds);
        // DO 转 VO
        List<FindTagListResVO> tagVos = tagDOS.stream()
                .map(tagDO -> FindTagListResVO.builder().id(tagDO.getId()).name(tagDO.getName()).build())
                .collect(Collectors.toList());
        vo.setTags(tagVos);

        // 上一篇
        ArticleDO prevArticleDO = articleMapper.selectPreArticle(articleId);
        if (Objects.nonNull(prevArticleDO)) {
            FindPreNextArticleResVO preArticleVO = FindPreNextArticleResVO.builder()
                    .articleId(prevArticleDO.getId())
                    .articleTitle(prevArticleDO.getTitle())
                    .build();
            vo.setPreArticle(preArticleVO);
        }

        // 下一篇
        ArticleDO nextArticleDO = articleMapper.selectNextArticle(articleId);
        if (Objects.nonNull(nextArticleDO)) {
            FindPreNextArticleResVO nextArticleVO = FindPreNextArticleResVO.builder()
                    .articleId(nextArticleDO.getId())
                    .articleTitle(nextArticleDO.getTitle())
                    .build();
            vo.setPreArticle(nextArticleVO);
        }

        // 发布文章阅读事件
        eventPublisher.publishEvent(new ReadArticleEvent(this, articleId));

        return Response.success(vo);
    }
}
