package com.zhixing.weblog.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.zhixing.weblog.admin.convert.ArticleDetailConvert;
import com.zhixing.weblog.admin.model.vo.article.*;
import com.zhixing.weblog.admin.service.AdminArticleService;
import com.zhixing.weblog.comm.domain.dos.*;
import com.zhixing.weblog.comm.domain.mapper.*;
import com.zhixing.weblog.comm.enums.ResponseCodeEnum;
import com.zhixing.weblog.comm.exception.BizException;
import com.zhixing.weblog.comm.utils.PageResponse;
import com.zhixing.weblog.comm.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdminArticleServiceImpl implements AdminArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ArticleContentMapper articleContentMapper;
    @Autowired
    private ArticleCategoryRelMapper articleCategoryRelMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private ArticleTagRelMapper articleTagRelMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response publishArticle(PublishArticleReqVO publishArticleReqVO) {
        // VO 转 DO
        ArticleDO articleDO = ArticleDO.builder()
                .title(publishArticleReqVO.getTitle())
                .cover(publishArticleReqVO.getCover())
                .summary(publishArticleReqVO.getSummary())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
        articleMapper.insert(articleDO);

        // 拿到插入记录的主键id
        Long articleId = articleDO.getId();

        // VO 转 DO
        ArticleContentDO articleContentDO = ArticleContentDO.builder()
                .articleId(articleId)
                .content(publishArticleReqVO.getContent())
                .build();
        articleContentMapper.insert(articleContentDO);

        // 关联分类
        Long categoryId = publishArticleReqVO.getCategoryId();
        // 校验分类是否存在
        CategoryDO categoryDO = categoryMapper.selectById(categoryId);
        if (Objects.isNull(categoryDO)) {
            log.warn("--》 分类不存在，categoryId：{}", categoryId);
            throw new BizException(ResponseCodeEnum.CATEGORY_NOT_FOUND);
        }
        // VO 转 DO
        ArticleCategoryRelDO articleCategoryRelDO = ArticleCategoryRelDO.builder()
                .articleId(articleId)
                .categoryId(categoryId)
                .build();
        articleCategoryRelMapper.insert(articleCategoryRelDO);

        // 关联标签
        List<String> publishTags = publishArticleReqVO.getTags();
        insertTags(articleId, publishTags);
        return Response.success();
    }

    /**
     * 保存标签
     *
     * @param tags
     */
    public void insertTags(Long articleId, List<String> tags) {
        // 筛选提交的标签(表中不存在)
        List<String> notExistTags = null;
        // 筛选提交的标签(表中已存在)
        List<String> existedTags = null;

        // 查询所有标签
        List<TagDO> tagDOS = tagMapper.selectList(null);

        // 表中没有标签
        if (CollectionUtils.isEmpty(tagDOS)) {
            notExistTags = tags;
        } else {
            List<String> tagIds = tagDOS.stream().map(tagDO -> String.valueOf(tagDO.getId())).collect(Collectors.toList());
            // 获取已存在标签
            existedTags = tags.stream().filter(tag -> tagIds.contains(tag)).collect(Collectors.toList());
            // 获取不存在标签
            notExistTags = tags.stream().filter(tag -> !tagIds.contains(tag)).collect(Collectors.toList());

            // 标签名称、id集合
            Map<String, Long> tagNameIdMap = tagDOS.stream().collect(Collectors.toMap(tagDO -> tagDO.getName().toLowerCase(), TagDO::getId));

            // 使用迭代器进行删除
            Iterator<String> iterator = notExistTags.iterator();
            while (iterator.hasNext()) {
                String notExistTag = iterator.next();
                // 转小写。若 Map 中存在相同的 key，则表示是重复的标签
                if (tagNameIdMap.containsKey(notExistTag.toLowerCase())) {
                    // 清除重复标签
                    iterator.remove();
                    // 将对应的 id 添加到已存在标签集合
                    existedTags.add(String.valueOf(tagNameIdMap.get(notExistTag.toLowerCase())));
                }
            }
        }

        // 将提交的，已存在表中的标签关联文章
        if (!CollectionUtils.isEmpty(existedTags)) {
            List<ArticleTagRelDO> articleTagRelDOS = Lists.newArrayList();
            existedTags.forEach(tagId -> {
                ArticleTagRelDO articleTagRelDO = ArticleTagRelDO.builder()
                        .articleId(articleId)
                        .tagId(Long.valueOf(tagId))
                        .build();
                articleTagRelDOS.add(articleTagRelDO);
            });
            // 批量插入
            articleTagRelMapper.insertBatchSomeColumn(articleTagRelDOS);
        }

        // 将提交的，不存在表中的标签入库保存
        if (!CollectionUtils.isEmpty(notExistTags)) {
            // 入库后拿到 Id 关联文章
            List<ArticleTagRelDO> articleTagRelDOS = Lists.newArrayList();
            notExistTags.forEach(tagName -> {
                TagDO tagDO = TagDO.builder()
                        .name(tagName)
                        .createTime(LocalDateTime.now())
                        .updateTime(LocalDateTime.now())
                        .build();
                tagMapper.insert(tagDO);
                // 拿到标签的 id
                Long tagId = tagDO.getId();

                ArticleTagRelDO articleTagRelDO = ArticleTagRelDO.builder()
                        .articleId(articleId)
                        .tagId(tagId)
                        .build();
                articleTagRelDOS.add(articleTagRelDO);
            });
            // 批量插入
            articleTagRelMapper.insertBatchSomeColumn(articleTagRelDOS);
        }
    }

    @Override
    public Response deleteArticle(DeleteArticleReqVO deleteArticleReqVO) {
        Long articleId = deleteArticleReqVO.getId();
        // 删除文章
        articleMapper.deleteById(articleId);
        // 删除文章内容
        articleContentMapper.deleteByArticleId(articleId);
        // 删除关联分类
        articleCategoryRelMapper.deleteByArticleId(articleId);
        // 删除关联标签
        articleTagRelMapper.deleteByArticleId(articleId);
        return Response.success();
    }

    @Override
    public Response<PageResponse<FindArticlePageResVO>> findArticlePage(FindArticlePageReqVO findArticlePageReqVO) {
        // 获取当前页，页码
        Long current = findArticlePageReqVO.getCurrent();
        Long pageSize = findArticlePageReqVO.getPageSize();
        String title = findArticlePageReqVO.getTitle();
        title = StringUtils.isBlank(title) ? "" : title;
        LocalDate startDate = findArticlePageReqVO.getStartDate();
        LocalDate endDate = findArticlePageReqVO.getEndDate();
        // 执行分页查询
        Page<ArticleDO> articleDOPage = articleMapper.selectPageList(current, pageSize, title, startDate, endDate);
        List<ArticleDO> articleDOS = articleDOPage.getRecords();

        // DO 转 VO
        List<FindArticlePageResVO> vos = null;
        if (!CollectionUtils.isEmpty(articleDOS)) {
            vos = articleDOS.stream()
                    .map(articleDO -> FindArticlePageResVO.builder()
                            .id(articleDO.getId())
                            .title(articleDO.getTitle())
                            .cover(articleDO.getCover())
                            .createTime(articleDO.getCreateTime())
                            .updateTime(articleDO.getUpdateTime())
                            .build())
                    .collect(Collectors.toList());
        }
        return PageResponse.success(articleDOPage, vos);
    }

    @Override
    public Response findArticleDetail(FindArticleDetailReqVO findArticleDetailReqVO) {
        Long articleId = findArticleDetailReqVO.getId();
        ArticleDO articleDO = articleMapper.selectById(articleId);

        if (Objects.isNull(articleDO)) {
            log.warn("--》查询的文章不存在，articleId：{}", articleId);
            throw new BizException(ResponseCodeEnum.ARTICLE_NOT_FOUND);
        }
        // 文章内容
        ArticleContentDO articleContentDO = articleContentMapper.selectByArticleId(articleId);
        // 文章分类
        ArticleCategoryRelDO articleCategoryRelDO = articleCategoryRelMapper.selectByArticleId(articleId);
        // 对应标签
        List<ArticleTagRelDO> articleTagRelDOS = articleTagRelMapper.selectByArticleId(articleId);
        // 获取对应标签的id集合
        List<Long> tagIds = articleTagRelDOS.stream().map(ArticleTagRelDO::getTagId).collect(Collectors.toList());

        // DO 转 VO
        FindArticleDetailResVO vos = ArticleDetailConvert.INSTANCE.convertDOToVO(articleDO);
        vos.setContent(articleContentDO.getContent());
        vos.setCategoryId(articleCategoryRelDO.getCategoryId());
        vos.setTagIds(tagIds);

        return Response.success(vos);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response updateArticle(UpdateArticleReqVO updateArticleReqVO) {
        Long articleId = updateArticleReqVO.getId();

        // VO 转 DO
        ArticleDO articleDO = ArticleDO.builder()
                .id(articleId)
                .title(updateArticleReqVO.getTitle())
                .cover(updateArticleReqVO.getCover())
                .summary(updateArticleReqVO.getSummary())
                .updateTime(LocalDateTime.now())
                .build();
        int count = articleMapper.updateById(articleDO);
        if (count == 0) {
            log.warn("--》该文章不存在，articleId：{}", articleId);
            throw new BizException(ResponseCodeEnum.ARTICLE_NOT_FOUND);
        }

        // 更新内容
        ArticleContentDO articleContentDO = ArticleContentDO.builder()
                .articleId(articleId)
                .content(updateArticleReqVO.getContent())
                .build();
        articleContentMapper.updateByArticleId(articleContentDO);

        // 更新分类
        Long categoryId = updateArticleReqVO.getCategoryId();
        // 校验分类是否存在
        CategoryDO categoryDO = categoryMapper.selectById(categoryId);
        if (Objects.isNull(categoryDO)) {
            log.warn("--》该分类不存在，categoryId：{}", categoryId);
            throw new BizException(ResponseCodeEnum.CATEGORY_NOT_FOUND);
        }
        // 删除关联分类，再插入新分类
        articleCategoryRelMapper.deleteByArticleId(articleId);
        ArticleCategoryRelDO articleCategoryRelDO = ArticleCategoryRelDO.builder()
                .articleId(articleId)
                .categoryId(categoryId)
                .build();
        articleCategoryRelMapper.insert(articleCategoryRelDO);

        // 更新标签,删除关联标签，再插入新标签
        articleTagRelMapper.deleteByArticleId(articleId);
        List<String> publishTags = updateArticleReqVO.getTags();
        insertTags(articleId, publishTags);
        return Response.success();
    }
}
