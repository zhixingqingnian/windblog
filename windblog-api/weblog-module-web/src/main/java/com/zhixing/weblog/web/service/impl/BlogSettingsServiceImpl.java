package com.zhixing.weblog.web.service.impl;

import com.zhixing.weblog.comm.domain.dos.BlogSettingsDO;
import com.zhixing.weblog.comm.domain.mapper.BlogSettingsMapper;
import com.zhixing.weblog.comm.utils.Response;
import com.zhixing.weblog.web.convert.BlogSettingsConvert;
import com.zhixing.weblog.web.model.vo.blogsettings.FindBlogSettingsDetailRspVO;
import com.zhixing.weblog.web.service.BlogSettingsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BlogSettingsServiceImpl implements BlogSettingsService {

    @Autowired
    private BlogSettingsMapper blogSettingsMapper;

    /**
     * 获取博客设置信息
     *
     * @return
     */
    @Override
    public Response findDetail() {
        // 查询博客设置信息（约定的 ID 为 1）
        BlogSettingsDO blogSettingsDO = blogSettingsMapper.selectById(1L);
        // DO 转 VO
        FindBlogSettingsDetailRspVO vo = BlogSettingsConvert.INSTANCE.convertDOToVO(blogSettingsDO);

        return Response.success(vo);
    }
}
