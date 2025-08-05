package com.zhixing.weblog.web.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zhixing.weblog.comm.domain.dos.TagDO;
import com.zhixing.weblog.comm.domain.mapper.TagMapper;
import com.zhixing.weblog.comm.utils.Response;
import com.zhixing.weblog.web.model.vo.tag.FindTagListResVO;
import com.zhixing.weblog.web.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;

    @Override
    public Response findTagList() {
        // 查询所有分类
        List<TagDO> tagDOS = tagMapper.selectList(Wrappers.emptyWrapper());
        // DO 转VO
        List<FindTagListResVO> vos = null;
        if (!CollectionUtils.isEmpty(tagDOS)) {
            vos = tagDOS.stream().map(tagDO -> FindTagListResVO.builder()
                    .id(tagDO.getId())
                    .name(tagDO.getName())
                    .build()).collect(Collectors.toList());
        }

        return Response.success(vos);
    }
}
