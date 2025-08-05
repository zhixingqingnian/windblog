package com.zhixing.weblog.web.service;

import com.zhixing.weblog.comm.utils.Response;

public interface CategoryService {
    /**
     * 获取分类列表
     *
     * @return
     */
    Response findCategoryList();
}

