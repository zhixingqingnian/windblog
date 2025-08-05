package com.zhixing.weblog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhixing.weblog.admin.model.vo.tag.FindTagPageResVO;
import com.zhixing.weblog.admin.model.vo.tool.*;
import com.zhixing.weblog.admin.service.AdminToolService;
import com.zhixing.weblog.comm.domain.dos.TagDO;
import com.zhixing.weblog.comm.domain.dos.ToolContentDO;
import com.zhixing.weblog.comm.domain.dos.ToolDO;
import com.zhixing.weblog.comm.domain.mapper.ToolContentMapper;
import com.zhixing.weblog.comm.domain.mapper.ToolMapper;
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
public class AdminToolServiceImpl implements AdminToolService {
    @Autowired
    private ToolMapper toolMapper;

    @Autowired
    private ToolContentMapper toolContentMapper;

    @Override
    public Response<PageResponse<FindToolPageResVO>> findToolPage(FindToolPageReqVO findToolPageReqVO) {
        // 获取当前页码、每页展示数量
        Long current = findToolPageReqVO.getCurrent();
        Long pageSize = findToolPageReqVO.getPageSize();
        // 分页对象
        Page<ToolDO> page = new Page<>(current, pageSize);
        // 构建查询条件
        LambdaQueryWrapper<ToolDO> wrapper = new LambdaQueryWrapper<>();
        String name = findToolPageReqVO.getName();
        // 当 name 为空时设置默认值
        name = StringUtils.isBlank(name) ? "" : name;
        LocalDate startDate = findToolPageReqVO.getStartDate();
        LocalDate endDate = findToolPageReqVO.getEndDate();
        wrapper
                // 模糊查询姓名
                .like(ToolDO::getName, name.trim())
                // 创建时间大于开始时间
                .ge(Objects.nonNull(startDate), ToolDO::getCreateTime, startDate)
                // 创建时间小于结束时间
                .le(Objects.nonNull(endDate), ToolDO::getCreateTime, endDate)
                // 按创建时间倒叙排列
                .orderByDesc(ToolDO::getCreateTime);
        // 执行分页查询
        Page<ToolDO> toolDOPage = toolMapper.selectPage(page, wrapper);
        List<ToolDO> toolDOS = toolDOPage.getRecords();
        // DO 转 VO
        List<FindToolPageResVO> vos = null;
        if (!CollectionUtils.isEmpty(toolDOS)) {
            vos = toolDOS.stream()
                    .map(toolDO -> FindToolPageResVO.builder()
                            .id(toolDO.getId())
                            .name(toolDO.getName())
                            .icon(toolDO.getIcon())
                            .createTime(toolDO.getCreateTime())
                            .updateTime(toolDO.getUpdateTime())
                            .build())
                    .collect(Collectors.toList());
        }
        return PageResponse.success(toolDOPage, vos);
    }

    @Override
    public Response toolList() {
        // 查询所有类
        List<ToolDO> toolDOS = toolMapper.selectList(null);
        // DO 转 VO
        List<SelectResVO> vos = null;
        if (!CollectionUtils.isEmpty(toolDOS)) {
            vos = toolDOS.stream().map(toolDO -> SelectResVO.builder()
                    .label(toolDO.getName())
                    .value(toolDO.getId())
                    .build()).collect(Collectors.toList());
        }

        return Response.success(vos);
    }

    @Override
    public Response saveTool(SaveToolReqVO saveToolReqVO) {
        // 获取添加/编辑参数信息
        Long toolId = saveToolReqVO.getId();
        String toolName = saveToolReqVO.getName();
        String toolIcon = saveToolReqVO.getIcon();
        // 判断此工具是否存在
        ToolDO toolDO = toolMapper.selectByName(toolName);
        if (Objects.isNull(toolId) && Objects.nonNull(toolDO)) {
            log.warn("工具名称：{}，已存在", toolName);
            throw new BizException(ResponseCodeEnum.TOOL_NAME_IS_EXISTED);
        }
        // 构建 DO 类
        ToolDO insertToolDO = ToolDO.builder()
                .name(toolName)
                .icon(toolIcon)
                .build();
        ToolDO updateToolDO = ToolDO.builder()
                .id(toolId)
                .name(toolName)
                .icon(toolIcon)
                .updateTime(LocalDateTime.now())
                .build();
        if (Objects.nonNull(toolId)) {
            // 执行 update
            toolMapper.updateById(updateToolDO);
        } else {
            // 执行 insert
            toolMapper.insert(insertToolDO);
        }
        return Response.success();
    }

    @Override
    public Response deleteTool(DeleteToolReq deleteToolReq) {
        Long id = deleteToolReq.getId();
        ToolContentDO toolContentDO = toolContentMapper.selectByToolId(id);
        String name = toolContentDO.getName();
        if (Objects.nonNull(toolContentDO)) {
            log.warn("工具名称：{}，下存在内容，不允许删除！", name);
            throw new BizException(ResponseCodeEnum.TOOL_ID_IS_BIND);
        }
        toolMapper.deleteById(id);
        return Response.success();
    }

    @Override
    public Response<PageResponse<FindToolContentPageResVO>> findToolContentPage(FindToolContentPageReqVO findToolContentPageReqVO) {
        // 获取当前页码、每页展示数量
        Long current = findToolContentPageReqVO.getCurrent();
        Long pageSize = findToolContentPageReqVO.getPageSize();
        // 分页对象
        Page<ToolContentDO> page = new Page<>(current, pageSize);
        // 构建查询条件
        LambdaQueryWrapper<ToolContentDO> wrapper = new LambdaQueryWrapper<>();
        String name = findToolContentPageReqVO.getName();
        Long toolId = findToolContentPageReqVO.getToolId();
        // 当 name 为空时设置默认值
        name = StringUtils.isBlank(name) ? "" : name;
        LocalDate startDate = findToolContentPageReqVO.getStartDate();
        LocalDate endDate = findToolContentPageReqVO.getEndDate();
        wrapper
                // 模糊查询姓名
                .like(ToolContentDO::getName, name.trim())
                // 查询所属菜单id
                .eq(Objects.nonNull(toolId), ToolContentDO::getToolId, toolId)
                // 创建时间大于开始时间
                .ge(Objects.nonNull(startDate), ToolContentDO::getCreateTime, startDate)
                // 创建时间小于结束时间
                .le(Objects.nonNull(endDate), ToolContentDO::getCreateTime, endDate)
                // 按创建时间倒叙排列
                .orderByDesc(ToolContentDO::getCreateTime);
        // 执行分页查询
        Page<ToolContentDO> toolContentDOPage = toolContentMapper.selectPage(page, wrapper);
        List<ToolContentDO> toolContentDOS = toolContentDOPage.getRecords();
        // DO 转 VO
        List<FindToolContentPageResVO> vos = null;
        if (!CollectionUtils.isEmpty(toolContentDOS)) {
            vos = toolContentDOS.stream()
                    .map(toolContentDO -> FindToolContentPageResVO.builder()
                            .id(toolContentDO.getId())
                            .toolId(toolContentDO.getToolId())
                            .name(toolContentDO.getName())
                            .icon(toolContentDO.getIcon())
                            .link(toolContentDO.getLink())
                            .createTime(toolContentDO.getCreateTime())
                            .updateTime(toolContentDO.getUpdateTime())
                            .build())
                    .collect(Collectors.toList());
        }
        return PageResponse.success(toolContentDOPage, vos);
    }

    @Override
    public Response saveToolContent(SaveToolContentReqVO saveToolContentReqVO) {
        // 获取参数信息
        Long id = saveToolContentReqVO.getId();
        Long toolId = saveToolContentReqVO.getToolId();
        String name = saveToolContentReqVO.getName();
        String link = saveToolContentReqVO.getLink();
        String icon = saveToolContentReqVO.getIcon();

        // 判断此工具是否存在
        ToolContentDO toolContentDO = toolContentMapper.selectByName(name);
        if (Objects.isNull(id) && Objects.nonNull(toolContentDO)) {
            log.warn("工具名称：{}，已存在", name);
            throw new BizException(ResponseCodeEnum.TOOL_NAME_IS_EXISTED);
        }
        // 构建 DO 类
        ToolContentDO insertToolContentDO = ToolContentDO.builder()
                .toolId(toolId)
                .name(name)
                .link(link)
                .icon(icon)
                .build();
        ToolContentDO updateToolContentDO = ToolContentDO.builder()
                .id(id)
                .toolId(toolId)
                .name(name)
                .link(link)
                .icon(icon)
                .updateTime(LocalDateTime.now())
                .build();
        if (Objects.nonNull(id)) {
            // 执行 update
            toolContentMapper.updateById(updateToolContentDO);
        } else {
            // 执行 insert
            toolContentMapper.insert(insertToolContentDO);
        }
        return Response.success();
    }

    @Override
    public Response deleteToolContent(DeleteToolContentReq deleteToolContentReq) {
        Long id = deleteToolContentReq.getId();
        toolContentMapper.deleteById(id);
        return Response.success();
    }
}
