package com.zhixing.weblog.comm.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.zhixing.weblog.comm.domain.dos.TagDO;

import java.util.List;

public interface TagMapper extends BaseMapper<TagDO> {
    /**
     * 根据标签名称查询
     *
     * @param tagNmae
     * @return
     */
    default TagDO selectByName(String tagNmae) {
        LambdaQueryWrapper<TagDO> wrapper = new LambdaQueryWrapper();
        wrapper.eq(TagDO::getName, tagNmae);
        return selectOne(wrapper);
    }

    /**
     * 根据标签id批量查询
     *
     * @param tagIds
     * @return
     */
    default List<TagDO> selectByIds(List<Long> tagIds) {
        return selectList(Wrappers.<TagDO>lambdaQuery()
                .in(TagDO::getId, tagIds));
    }
}
