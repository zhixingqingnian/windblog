package com.zhixing.weblog.comm.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhixing.weblog.comm.domain.dos.ToolDO;

public interface ToolMapper extends BaseMapper<ToolDO> {
    /**
     * 根据工具名称查询
     * @param toolName
     * @return
     */
    default ToolDO selectByName(String toolName) {
        LambdaQueryWrapper<ToolDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ToolDO::getName, toolName);
        return selectOne(wrapper);
    }
}
