package com.zhixing.weblog.web.service;

import com.zhixing.weblog.comm.utils.Response;

public interface TagService {
    /**
     * 获取标签列表
     *
     * @return
     */
    Response findTagList();
}

