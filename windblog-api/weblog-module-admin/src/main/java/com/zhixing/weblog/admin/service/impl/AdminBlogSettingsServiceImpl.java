package com.zhixing.weblog.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhixing.weblog.admin.convert.BlogSettingsConvert;
import com.zhixing.weblog.admin.model.vo.blogsettings.FindBlogSettingsResVO;
import com.zhixing.weblog.admin.model.vo.blogsettings.UpdateBlogSettingsReqVO;
import com.zhixing.weblog.admin.service.AdminBlogSettingsService;
import com.zhixing.weblog.comm.domain.dos.BlogSettingsDO;
import com.zhixing.weblog.comm.domain.mapper.BlogSettingsMapper;
import com.zhixing.weblog.comm.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminBlogSettingsServiceImpl extends ServiceImpl<BlogSettingsMapper, BlogSettingsDO> implements AdminBlogSettingsService {

    @Autowired
    private BlogSettingsMapper blogSettingsMapper;

    @Override
    public Response updateBlogSettings(UpdateBlogSettingsReqVO updateBlogSettingsReqVO) {
        // VO 转 DO
        BlogSettingsDO blogSettingsDO = BlogSettingsConvert.INSTANCE.convertVOToDO(updateBlogSettingsReqVO);
        blogSettingsDO.setId(1L);
        // 保存或更新
        saveOrUpdate(blogSettingsDO);
        return Response.success();
    }

    @Override
    public Response findBlogSettingsInfo() {
        // 查询 id 为 1 的记录
        BlogSettingsDO blogSettingsDO = blogSettingsMapper.selectById(1L);
        // DO 转 VO
        FindBlogSettingsResVO blogSettingsResVO = BlogSettingsConvert.INSTANCE.convertDOToVO(blogSettingsDO);

        return Response.success(blogSettingsResVO);
    }
}
