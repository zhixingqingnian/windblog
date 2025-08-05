package com.zhixing.weblog.admin.service;

import com.zhixing.weblog.admin.model.vo.blogsettings.UpdateBlogSettingsReqVO;
import com.zhixing.weblog.comm.utils.Response;

public interface AdminBlogSettingsService {
    /**
     * 更新博客信息
     *
     * @param updateBlogSettingsReqVO
     * @return
     */
    Response updateBlogSettings(UpdateBlogSettingsReqVO updateBlogSettingsReqVO);

    /**
     * 获取博客详情
     *
     * @return
     */
    Response findBlogSettingsInfo();
}
