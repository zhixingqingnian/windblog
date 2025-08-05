package com.zhixing.weblog.comm.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhixing.weblog.comm.domain.dos.ToolContentDO;

public interface ToolContentMapper extends BaseMapper<ToolContentDO> {
    /**
     * 根据工具名称查询
     *
     * @param name
     * @return
     */
    default ToolContentDO selectByName(String name) {
        LambdaQueryWrapper<ToolContentDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ToolContentDO::getName, name);
        return selectOne(wrapper);
    }

    /**
     * 根据工具id查询
     *
     * @param toolId
     * @return
     */
    default ToolContentDO selectByToolId(Long toolId) {
        LambdaQueryWrapper<ToolContentDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ToolContentDO::getToolId, toolId);
        return selectOne(wrapper);
    }
}
