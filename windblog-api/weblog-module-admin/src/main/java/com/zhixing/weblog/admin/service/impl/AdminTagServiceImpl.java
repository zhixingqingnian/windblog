package com.zhixing.weblog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhixing.weblog.admin.model.vo.tag.*;
import com.zhixing.weblog.admin.service.AdminTagService;
import com.zhixing.weblog.comm.domain.dos.TagDO;
import com.zhixing.weblog.comm.domain.mapper.TagMapper;
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
public class AdminTagServiceImpl extends ServiceImpl<TagMapper, TagDO> implements AdminTagService {
    @Autowired
    private TagMapper tagMapper;

    @Override
    public Response batchAddTag(BatchAddTagReqVO batchAddTagReqVO) {
        // VO 转 DO
        List<TagDO> tagDOS = batchAddTagReqVO.getTags()
                .stream().map(tag -> TagDO.builder()
                        .name(tag.trim())
                        .createTime(LocalDateTime.now())
                        .updateTime(LocalDateTime.now())
                        .build())
                .collect(Collectors.toList());
        // 批量插入
        try {
            saveBatch(tagDOS);
        } catch (Exception e) {
            log.warn("该标签已存在", e);
        }
        return Response.success();
    }

    @Override
    public Response modifyTag(ModifyTagReqVo modifyTagReqVo) {
        String tagName = modifyTagReqVo.getName();
        Long tagId = modifyTagReqVo.getId();
        // 构建 DO 类
        TagDO modifyTagDO = TagDO.builder()
                .id(tagId)
                .name(tagName.trim())
                .updateTime(LocalDateTime.now())
                .build();
        // 执行 update
        tagMapper.updateById(modifyTagDO);
        return Response.success();
    }

    @Override
    public Response<PageResponse<FindTagPageResVO>> findTagPage(FindTagPageReqVO findTagPageReqVO) {
        // 获取当前页码、每页展示数量
        Long current = findTagPageReqVO.getCurrent();
        Long pageSize = findTagPageReqVO.getPageSize();
        // 分页对象
        Page<TagDO> page = new Page<>(current, pageSize);
        // 构建查询条件
        LambdaQueryWrapper<TagDO> wrapper = new LambdaQueryWrapper<>();
        String name = findTagPageReqVO.getName();
        // 当 name 为空时设置默认值
        name = StringUtils.isBlank(name) ? "" : name;
        LocalDate startDate = findTagPageReqVO.getStartDate();
        LocalDate endDate = findTagPageReqVO.getEndDate();
        wrapper
                // 模糊查询姓名
                .like(TagDO::getName, name.trim())
                // 创建时间大于开始时间
                .ge(Objects.nonNull(startDate), TagDO::getCreateTime, startDate)
                // 创建时间小于结束时间
                .le(Objects.nonNull(endDate), TagDO::getCreateTime, endDate)
                // 按创建时间倒叙排列
                .orderByDesc(TagDO::getCreateTime);
        // 执行分页查询
        Page<TagDO> tagDOPage = tagMapper.selectPage(page, wrapper);
        List<TagDO> tagDOS = tagDOPage.getRecords();
        // DO 转 VO
        List<FindTagPageResVO> vos = null;
        if (!CollectionUtils.isEmpty(tagDOS)) {
            vos = tagDOS.stream()
                    .map(tagDO -> FindTagPageResVO.builder()
                            .id(tagDO.getId())
                            .name(tagDO.getName())
                            .createTime(tagDO.getCreateTime())
                            .updateTime(tagDO.getUpdateTime())
                            .build())
                    .collect(Collectors.toList());
        }
        return PageResponse.success(tagDOPage, vos);
    }

    @Override
    public Response findTagList(FindTagListReqVO findTagListReqVO) {
        LambdaQueryWrapper<TagDO> wrapper = new LambdaQueryWrapper<>();
        String name = findTagListReqVO.getName();
        String tagName = StringUtils.isBlank(name) ? "" : name;
        wrapper.like(TagDO::getName, tagName.trim()).orderByDesc(TagDO::getCreateTime);
        List<TagDO> tagDOS = tagMapper.selectList(wrapper);

        // DO 转 VO
        List<SelectResVO> selectResVOS = tagDOS.stream().map(tagDO -> SelectResVO.builder()
                .label(tagDO.getName())
                .value(tagDO.getId())
                .build()).collect(Collectors.toList());
        return Response.success(selectResVOS);
    }

    @Override
    public Response deleteTag(DeleteTagReqVO deleteTagReqVO) {
        Long tagId = deleteTagReqVO.getId();
        tagMapper.deleteById(tagId);
        return Response.success();
    }
}
