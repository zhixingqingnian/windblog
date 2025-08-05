package com.zhixing.weblog.admin.controller;

import com.zhixing.weblog.admin.model.vo.category.FindCategoryPageResVO;
import com.zhixing.weblog.admin.model.vo.category.SaveCategoryReqVO;
import com.zhixing.weblog.admin.model.vo.category.DeleteCategoryReqVO;
import com.zhixing.weblog.admin.model.vo.category.FindCategoryPageReqVO;
import com.zhixing.weblog.admin.service.AdminCategoryService;
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
@Api(tags = "Admin 分类模块")
public class AdminCategoryController {
    @Autowired
    private AdminCategoryService adminCategoryService;

    @PostMapping("/category/save")
    @ApiOperation(value = "保存分类")
    @ApiOperationLog(description = "保存分类")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response saveCategory(@RequestBody @Validated SaveCategoryReqVO saveCategoryReqVO) {
        return adminCategoryService.saveCategory(saveCategoryReqVO);
    }

    @PostMapping("/category/page")
    @ApiOperation(value = "分类分页数据")
    @ApiOperationLog(description = "分类分页数据")
    public Response<PageResponse<FindCategoryPageResVO>> findCategoryPage(@RequestBody @Validated FindCategoryPageReqVO findCategoryPageReqVO) {
        return adminCategoryService.findCategoryPage(findCategoryPageReqVO);
    }

    @PostMapping("/category/delete")
    @ApiOperation(value = "删除分类")
    @ApiOperationLog(description = "删除分类")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response deleteCategory(@RequestBody @Validated DeleteCategoryReqVO deleteCategoryReqVO) {
        return adminCategoryService.deleteCategory(deleteCategoryReqVO);
    }

    @PostMapping("/category/list")
    @ApiOperation(value = "分类列表")
    @ApiOperationLog(description = "分类列表")
    public Response findCategoryList() {
        return adminCategoryService.findCategoryList();
    }
}
