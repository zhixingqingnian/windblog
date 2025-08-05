package com.zhixing.weblog.web.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.zhixing.weblog.comm.domain.dos.*;
import com.zhixing.weblog.comm.domain.mapper.*;
import com.zhixing.weblog.comm.utils.PageResponse;
import com.zhixing.weblog.comm.utils.Response;
import com.zhixing.weblog.web.convert.ArticleConvert;
import com.zhixing.weblog.web.model.vo.article.FIndIndexArticlePageReqVO;
import com.zhixing.weblog.web.model.vo.article.FIndIndexArticlePageResVO;
import com.zhixing.weblog.web.model.vo.category.FindCategoryListResVO;
import com.zhixing.weblog.web.model.vo.tag.FindTagListResVO;
import com.zhixing.weblog.web.service.ArticleService;
import com.zhixing.weblog.web.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Response findCategoryList() {
        // 查询所有分类
        List<CategoryDO> categoryDOS = categoryMapper.selectList(Wrappers.emptyWrapper());
        // DO 转VO
        List<FindCategoryListResVO> vos = null;
        if (!CollectionUtils.isEmpty(categoryDOS)) {
            vos = categoryDOS.stream().map(categoryDO -> FindCategoryListResVO.builder()
                    .id(categoryDO.getId())
                    .name(categoryDO.getName())
                    .build()).collect(Collectors.toList());
        }

        return Response.success(vos);
    }
}
