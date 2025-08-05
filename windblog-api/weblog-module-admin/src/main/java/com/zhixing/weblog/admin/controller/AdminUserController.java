package com.zhixing.weblog.admin.controller;

import com.zhixing.weblog.admin.model.vo.user.FindUserPageReqVO;
import com.zhixing.weblog.admin.model.vo.user.FindUserPageResVO;
import com.zhixing.weblog.admin.model.vo.user.UpdateAdminUserPasswordReqVO;
import com.zhixing.weblog.admin.service.AdminUserService;
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
@Api(tags = "Admin 用户模块")
public class AdminUserController {
    @Autowired
    private AdminUserService adminUserService;

    @PostMapping("/password/update")
    @ApiOperation(value = "修改用户密码")
    @ApiOperationLog(description = "修改用户密码")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response updatePassword(@RequestBody @Validated UpdateAdminUserPasswordReqVO updateAdminUserPasswordReqVO) {
        return adminUserService.updatePassword(updateAdminUserPasswordReqVO);
    }

    @PostMapping("/user/info")
    @ApiOperation(value = "获取用户信息")
    @ApiOperationLog(description = "获取用户信息")
    public Response findUserInfo() {
        return adminUserService.findUserInfo();
    }

    @PostMapping("/user/page")
    @ApiOperation(value = "获取用户分页数据")
    @ApiOperationLog(description = "获取用户分页数据")
    public Response<PageResponse<FindUserPageResVO>> findUserPage(@RequestBody @Validated FindUserPageReqVO findUserPageReqVO) {
        return adminUserService.findUserPage(findUserPageReqVO);
    }

}
