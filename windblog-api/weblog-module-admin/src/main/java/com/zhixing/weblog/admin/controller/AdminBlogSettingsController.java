package com.zhixing.weblog.admin.controller;

import com.zhixing.weblog.admin.model.vo.blogsettings.UpdateBlogSettingsReqVO;
import com.zhixing.weblog.admin.service.AdminBlogSettingsService;
import com.zhixing.weblog.comm.aspect.ApiOperationLog;
import com.zhixing.weblog.comm.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/blog/settings")
@Api(tags = "Admin 博客设置模块")
public class AdminBlogSettingsController {
    @Autowired
    private AdminBlogSettingsService adminBlogSettingsService;

    @PostMapping("/save")
    @ApiOperation(value = "博客基础信息修改")
    @ApiOperationLog(description = "博客基础信息修改")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response updateBlogSettings(@RequestBody @Validated UpdateBlogSettingsReqVO updateBlogSettingsReqVO) {
        return adminBlogSettingsService.updateBlogSettings(updateBlogSettingsReqVO);
    }

    @PostMapping("/detail")
    @ApiOperation(value = "博客基础信息")
    @ApiOperationLog(description = "博客基础信息")
    public Response findBlogSettingsInfo() {
        return adminBlogSettingsService.findBlogSettingsInfo();
    }
}
