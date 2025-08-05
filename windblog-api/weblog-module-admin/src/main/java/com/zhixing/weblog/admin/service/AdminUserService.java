package com.zhixing.weblog.admin.service;

import com.zhixing.weblog.admin.model.vo.user.FindUserPageReqVO;
import com.zhixing.weblog.admin.model.vo.user.FindUserPageResVO;
import com.zhixing.weblog.admin.model.vo.user.UpdateAdminUserPasswordReqVO;
import com.zhixing.weblog.comm.utils.PageResponse;
import com.zhixing.weblog.comm.utils.Response;

public interface AdminUserService {
    /**
     * 修改密码
     *
     * @param updateAdminUserPasswordReqVO
     * @return
     */
    Response updatePassword(UpdateAdminUserPasswordReqVO updateAdminUserPasswordReqVO);

    /**
     * 获取当前登录用户信息
     *
     * @return
     */
    Response findUserInfo();

    /**
     * 获取用户信息列表
     *
     * @return
     */
    Response<PageResponse<FindUserPageResVO>> findUserPage(FindUserPageReqVO findUserPageReqVO);
}
