package com.zhixing.weblog.web.convert;

import com.zhixing.weblog.comm.domain.dos.BlogSettingsDO;
import com.zhixing.weblog.web.model.vo.blogsettings.FindBlogSettingsDetailRspVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BlogSettingsConvert {
    /**
     * 初始化 convert 实例
     */
    BlogSettingsConvert INSTANCE = Mappers.getMapper(BlogSettingsConvert.class);

    /**
     * 将 DO 转 VO
     *
     * @param bean
     * @return
     */
    FindBlogSettingsDetailRspVO convertDOToVO(BlogSettingsDO bean);
}
