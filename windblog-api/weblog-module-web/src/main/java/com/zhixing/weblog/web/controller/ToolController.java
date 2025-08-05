package com.zhixing.weblog.web.controller;

import com.zhixing.weblog.comm.aspect.ApiOperationLog;
import com.zhixing.weblog.comm.utils.Response;
import com.zhixing.weblog.web.model.vo.tool.FindToolContentListReqVO;
import com.zhixing.weblog.web.service.TagService;
import com.zhixing.weblog.web.service.ToolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "工具")
public class ToolController {
    @Autowired
    private ToolService toolService;

    @PostMapping("/tool/list")
    @ApiOperation(value = "获取工具列表")
    @ApiOperationLog(description = "获取工具列表")
    public Response findToolList() {
        return toolService.findToolList();
    }

    @PostMapping("/tool/content/list")
    @ApiOperation(value = "获取工具内容列表")
    @ApiOperationLog(description = "获取工具内容列表")
    public Response findToolContentList(@Validated @RequestBody FindToolContentListReqVO findToolContentListReqVO) {
        return toolService.findToolContentList(findToolContentListReqVO);
    }

}
