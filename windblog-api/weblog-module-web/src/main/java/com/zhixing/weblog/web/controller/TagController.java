package com.zhixing.weblog.web.controller;

import com.zhixing.weblog.comm.aspect.ApiOperationLog;
import com.zhixing.weblog.comm.utils.Response;
import com.zhixing.weblog.web.service.CategoryService;
import com.zhixing.weblog.web.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "文章")
public class TagController {
    @Autowired
    private TagService tagService;

    @PostMapping("/tag/list")
    @ApiOperation(value = "获取标签列表")
    @ApiOperationLog(description = "获取标签列表")
    public Response findTagList() {
        return tagService.findTagList();
    }

}
