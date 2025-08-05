package com.zhixing.weblog.admin.convert;

import com.zhixing.weblog.admin.model.vo.blogsettings.FindBlogSettingsResVO;
import com.zhixing.weblog.admin.model.vo.blogsettings.UpdateBlogSettingsReqVO;
import com.zhixing.weblog.comm.domain.dos.BlogSettingsDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BlogSettingsConvert {
    // 初始化 convert 实例
    BlogSettingsConvert INSTANCE = Mappers.getMapper(BlogSettingsConvert.class);

    /**
     * 将 VO 转为 DO
     *
     * @param bean
     * @return
     */
    BlogSettingsDO convertVOToDO(UpdateBlogSettingsReqVO bean);

    /**
     * 将 DO 转为 VO
     *
     * @param bean
     * @return
     */
    FindBlogSettingsResVO convertDOToVO(BlogSettingsDO bean);
}
