package com.zhixing.weblog.admin.controller;

import com.zhixing.weblog.admin.model.vo.tool.*;
import com.zhixing.weblog.admin.service.AdminToolService;
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
@Api(tags = "Admin 工具模块")
public class AdminToolController {
    @Autowired
    private AdminToolService adminToolService;

    @PostMapping("/tool/page")
    @ApiOperation(value = "工具分页数据")
    @ApiOperationLog(description = "工具分页数据")
    public Response<PageResponse<FindToolPageResVO>> findToolPage(@RequestBody @Validated FindToolPageReqVO findToolPageReqVO) {
        return adminToolService.findToolPage(findToolPageReqVO);
    }

    @PostMapping("/tool/list")
    @ApiOperation(value = "工具列表数据")
    @ApiOperationLog(description = "工具列表数据")
    public Response toolList() {
        return adminToolService.toolList();
    }

    @PostMapping("/tool/save")
    @ApiOperation(value = "新增/编辑工具")
    @ApiOperationLog(description = "新增/编辑工具")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response saveTool(@RequestBody @Validated SaveToolReqVO saveToolReqVO) {
        return adminToolService.saveTool(saveToolReqVO);
    }

    @PostMapping("/tool/delete")
    @ApiOperation(value = "删除工具")
    @ApiOperationLog(description = "删除工具")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response deleteTool(@RequestBody @Validated DeleteToolReq deleteToolReq) {
        return adminToolService.deleteTool(deleteToolReq);
    }

    @PostMapping("/tool/content/page")
    @ApiOperation(value = "工具内容分页数据")
    @ApiOperationLog(description = "工具内容分页数据")
    public Response<PageResponse<FindToolContentPageResVO>> findTagPage(@RequestBody @Validated FindToolContentPageReqVO findToolContentPageReqVO) {
        return adminToolService.findToolContentPage(findToolContentPageReqVO);
    }

    @PostMapping("/tool/content/save")
    @ApiOperation(value = "新增/编辑工具内容")
    @ApiOperationLog(description = "新增/编辑工具内容")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response saveToolContent(@RequestBody @Validated SaveToolContentReqVO saveToolContentReqVO) {
        return adminToolService.saveToolContent(saveToolContentReqVO);
    }

    @PostMapping("/tool/content/delete")
    @ApiOperation(value = "删除工具")
    @ApiOperationLog(description = "删除工具")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response del(@RequestBody @Validated DeleteToolContentReq deleteToolContentReq) {
        return adminToolService.deleteToolContent(deleteToolContentReq);
    }

}
