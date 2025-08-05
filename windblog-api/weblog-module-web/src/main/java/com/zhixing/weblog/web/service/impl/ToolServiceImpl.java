package com.zhixing.weblog.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zhixing.weblog.comm.domain.dos.ToolContentDO;
import com.zhixing.weblog.comm.domain.dos.ToolDO;
import com.zhixing.weblog.comm.domain.mapper.ToolContentMapper;
import com.zhixing.weblog.comm.domain.mapper.ToolMapper;
import com.zhixing.weblog.comm.utils.Response;
import com.zhixing.weblog.web.model.vo.tag.FindTagListResVO;
import com.zhixing.weblog.web.model.vo.tool.FindToolContentListReqVO;
import com.zhixing.weblog.web.model.vo.tool.FindToolContentListResVO;
import com.zhixing.weblog.web.model.vo.tool.FindToolListResVO;
import com.zhixing.weblog.web.service.ToolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ToolServiceImpl implements ToolService {

    @Autowired
    private ToolMapper toolMapper;

    @Autowired
    private ToolContentMapper toolContentMapper;

    @Override
    public Response findToolList() {
        List<ToolDO> toolDOS = toolMapper.selectList(Wrappers.emptyWrapper());
        // DO 转VO
        List<FindToolListResVO> vos = null;
        if (!CollectionUtils.isEmpty(toolDOS)) {
            vos = toolDOS.stream().map(toolDO -> FindToolListResVO.builder()
                    .id(toolDO.getId())
                    .name(toolDO.getName())
                    .icon(toolDO.getIcon())
                    .build()).collect(Collectors.toList());
        }
        return Response.success(vos);
    }

    @Override
    public Response findToolContentList(FindToolContentListReqVO findToolContentListReqVO) {
        Long id = findToolContentListReqVO.getId();
        LambdaQueryWrapper<ToolContentDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.
                like(ToolContentDO::getToolId, id)
                .orderByDesc(ToolContentDO::getCreateTime);
        List<ToolContentDO> toolContentDOS = toolContentMapper.selectList(wrapper);
        // DO 转VO
        List<FindToolContentListResVO> vos = null;
        if (!CollectionUtils.isEmpty(toolContentDOS)) {
            vos = toolContentDOS.stream().map(toolContentDO -> FindToolContentListResVO.builder()
                    .id(toolContentDO.getId())
                    .toolId(toolContentDO.getToolId())
                    .name(toolContentDO.getName())
                    .link(toolContentDO.getLink())
                    .icon(toolContentDO.getIcon())
                    .build()).collect(Collectors.toList());
        }
        return Response.success(vos);
    }
}
