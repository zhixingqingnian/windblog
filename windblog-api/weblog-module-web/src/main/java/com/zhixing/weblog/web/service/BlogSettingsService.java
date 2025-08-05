package com.zhixing.weblog.web.service;

import com.zhixing.weblog.comm.utils.Response;

public interface BlogSettingsService {
    /**
     * 获取博客设置信息
     *
     * @return
     */
    Response findDetail();
}
