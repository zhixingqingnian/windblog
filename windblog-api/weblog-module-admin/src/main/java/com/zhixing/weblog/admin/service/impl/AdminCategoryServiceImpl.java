package com.zhixing.weblog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhixing.weblog.admin.model.vo.category.SaveCategoryReqVO;
import com.zhixing.weblog.admin.model.vo.category.DeleteCategoryReqVO;
import com.zhixing.weblog.admin.model.vo.category.FindCategoryPageReqVO;
import com.zhixing.weblog.admin.model.vo.category.FindCategoryPageResVO;
import com.zhixing.weblog.admin.service.AdminCategoryService;
import com.zhixing.weblog.comm.domain.dos.CategoryDO;
import com.zhixing.weblog.comm.domain.mapper.CategoryMapper;
import com.zhixing.weblog.comm.enums.ResponseCodeEnum;
import com.zhixing.weblog.comm.exception.BizException;
import com.zhixing.weblog.comm.model.vo.SelectResVO;
import com.zhixing.weblog.comm.utils.PageResponse;
import com.zhixing.weblog.comm.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdminCategoryServiceImpl implements AdminCategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Response saveCategory(SaveCategoryReqVO saveCategoryReqVO) {
        String categoryName = saveCategoryReqVO.getName();
        Long categoryId = saveCategoryReqVO.getId();
        // 判断此分类是否已存在
        CategoryDO categoryDO = categoryMapper.selectByName(categoryName);
        if (Objects.isNull(categoryId) && Objects.nonNull(categoryDO)) {
            log.warn("分类名称：{}，已存在", categoryName);
            throw new BizException(ResponseCodeEnum.CATEGORY_NAME_IS_EXISTED);
        }
        // 构建 DO 类
        CategoryDO insertCategoryDO = CategoryDO.builder()
                .name(categoryName.trim())
                .build();
        CategoryDO updateCategoryDO = CategoryDO.builder()
                .id(categoryId)
                .name(categoryName.trim())
                .updateTime(LocalDateTime.now())
                .build();
        if (Objects.nonNull(categoryId)) {
            // 执行 update
            categoryMapper.updateById(updateCategoryDO);
        } else {
            // 执行 insert
            categoryMapper.insert(insertCategoryDO);
        }
        return Response.success();
    }

    @Override
    public Response<PageResponse<FindCategoryPageResVO>> findCategoryPage(FindCategoryPageReqVO findCategoryPageReqVO) {
        // 获取当前页码、每页展示数量
        Long current = findCategoryPageReqVO.getCurrent();
        Long pageSize = findCategoryPageReqVO.getPageSize();
        // 分页对象
        Page<CategoryDO> page = new Page<>(current, pageSize);
        // 构建查询条件
        LambdaQueryWrapper<CategoryDO> wrapper = new LambdaQueryWrapper<>();
        String name = findCategoryPageReqVO.getName();
        name = StringUtils.isBlank(name) ? "" : name;
        LocalDate startDate = findCategoryPageReqVO.getStartDate();
        LocalDate endDate = findCategoryPageReqVO.getEndDate();
        wrapper
                // 模糊查询姓名
                .like(CategoryDO::getName, name.trim())
                // 创建时间大于开始时间
                .ge(Objects.nonNull(startDate), CategoryDO::getCreateTime, startDate)
                // 创建时间小于结束时间
                .le(Objects.nonNull(endDate), CategoryDO::getCreateTime, endDate)
                // 按创建时间倒叙排列
                .orderByDesc(CategoryDO::getCreateTime);
        // 执行分页查询
        Page<CategoryDO> categoryDOPage = categoryMapper.selectPage(page, wrapper);
        List<CategoryDO> categoryDOS = categoryDOPage.getRecords();
        // DO 转 VO
        List<FindCategoryPageResVO> vos = null;
        if (!CollectionUtils.isEmpty(categoryDOS)) {
            vos = categoryDOS.stream()
                    .map(categoryDO -> FindCategoryPageResVO.builder()
                            .id(categoryDO.getId())
                            .name(categoryDO.getName())
                            .createTime(categoryDO.getCreateTime())
                            .updateTime(categoryDO.getUpdateTime())
                            .build())
                    .collect(Collectors.toList());
        }
        return PageResponse.success(categoryDOPage, vos);
    }

    @Override
    public Response deleteCategory(DeleteCategoryReqVO deleteCategoryReqVO) {
        // 分类id
        Long categoryId = deleteCategoryReqVO.getId();
        // 删除分类
        categoryMapper.deleteById(categoryId);
        return Response.success();
    }

    @Override
    public Response findCategoryList() {
        // 查询所有类
        List<CategoryDO> categoryDOS = categoryMapper.selectList(null);
        // DO 转 VO
        List<SelectResVO> selectResVOS = null;
        if (!CollectionUtils.isEmpty(categoryDOS)) {
            selectResVOS = categoryDOS.stream().map(categoryDO -> SelectResVO.builder()
                    .label(categoryDO.getName())
                    .value(categoryDO.getId())
                    .build()).collect(Collectors.toList());
        }
        return Response.success(selectResVOS);
    }
}
