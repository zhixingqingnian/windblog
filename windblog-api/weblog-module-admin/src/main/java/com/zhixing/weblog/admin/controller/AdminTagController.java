package com.zhixing.weblog.admin.controller;

import com.zhixing.weblog.admin.model.vo.tag.*;
import com.zhixing.weblog.admin.service.AdminTagService;
import com.zhixing.weblog.comm.aspect.ApiOperationLog;
import com.zhixing.weblog.comm.utils.PageResponse;
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
@RequestMapping("/admin")
@Api(tags = "Admin 标签模块")
public class AdminTagController {
    @Autowired
    private AdminTagService adminTagService;

    @PostMapping("/tag/batch/add")
    @ApiOperation(value = "批量添加标签")
    @ApiOperationLog(description = "批量添加标签")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response batchAddTag(@RequestBody @Validated BatchAddTagReqVO batchAddTagReqVO) {
        return adminTagService.batchAddTag(batchAddTagReqVO);
    }

    @PostMapping("/tag/modify")
    @ApiOperation(value = "修改标签")
    @ApiOperationLog(description = "修改标签")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response modifyTag(@RequestBody @Validated ModifyTagReqVo modifyTagReqVo) {
        return adminTagService.modifyTag(modifyTagReqVo);
    }

    @PostMapping("/tag/delete")
    @ApiOperation(value = "删除标签")
    @ApiOperationLog(description = "删除标签")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response deleteTag(@RequestBody @Validated DeleteTagReqVO deleteTagReqVO) {
        return adminTagService.deleteTag(deleteTagReqVO);
    }

    @PostMapping("/tag/page")
    @ApiOperation(value = "标签分页数据")
    @ApiOperationLog(description = "标签分页数据")
    public Response<PageResponse<FindTagPageResVO>> findTagPage(@RequestBody @Validated FindTagPageReqVO findTagPageReqVO) {
        return adminTagService.findTagPage(findTagPageReqVO);
    }

    @PostMapping("/tag/list")
    @ApiOperation(value = "标签下拉数据")
    @ApiOperationLog(description = "标签下拉数据")
    public Response findTagList(@RequestBody @Validated FindTagListReqVO findTagListReqVO) {
        return adminTagService.findTagList(findTagListReqVO);
    }
}
