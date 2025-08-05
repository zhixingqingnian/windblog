package com.zhixing.weblog.comm.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhixing.weblog.comm.domain.dos.CategoryDO;

public interface CategoryMapper extends BaseMapper<CategoryDO> {
    /**
     * 根据分类名称查询
     *
     * @param categoryName
     * @return
     */
    default CategoryDO selectByName(String categoryName) {
        LambdaQueryWrapper<CategoryDO> wrapper = new LambdaQueryWrapper();
        wrapper.eq(CategoryDO::getName, categoryName);
        return selectOne(wrapper);
    }
}
