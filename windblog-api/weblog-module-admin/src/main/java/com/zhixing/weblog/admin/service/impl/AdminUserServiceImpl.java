package com.zhixing.weblog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhixing.weblog.admin.model.vo.tag.FindTagPageResVO;
import com.zhixing.weblog.admin.model.vo.user.FindUserInfoRspVO;
import com.zhixing.weblog.admin.model.vo.user.FindUserPageReqVO;
import com.zhixing.weblog.admin.model.vo.user.FindUserPageResVO;
import com.zhixing.weblog.admin.model.vo.user.UpdateAdminUserPasswordReqVO;
import com.zhixing.weblog.admin.service.AdminUserService;
import com.zhixing.weblog.comm.domain.dos.TagDO;
import com.zhixing.weblog.comm.domain.dos.UserDO;
import com.zhixing.weblog.comm.domain.mapper.UserMapper;
import com.zhixing.weblog.comm.enums.ResponseCodeEnum;
import com.zhixing.weblog.comm.utils.PageResponse;
import com.zhixing.weblog.comm.utils.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Response updatePassword(UpdateAdminUserPasswordReqVO updateAdminUserPasswordReqVO) {
        // 获取用户名、密码
        String username = updateAdminUserPasswordReqVO.getUsername();
        String password = updateAdminUserPasswordReqVO.getPassword();
        // 加密密码
        String encodePassword = passwordEncoder.encode(password);
        // 更新到数据库
        int count = userMapper.updatePasswordByUsername(username, encodePassword);
        return count == 1 ? Response.success() : Response.fail(ResponseCodeEnum.USERNAME_NOT_FOUND);
    }

    @Override
    public Response findUserInfo() {
        // 获取存储在 ThreadLocal 中的用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 拿到用户名
        String username = authentication.getName();
        return Response.success(FindUserInfoRspVO.builder().username(username).build());
    }

    @Override
    public Response<PageResponse<FindUserPageResVO>> findUserPage(FindUserPageReqVO findUserPageReqVO) {
        // 获取当前页码、每页展示数量
        Long current = findUserPageReqVO.getCurrent();
        Long pageSize = findUserPageReqVO.getPageSize();
        // 分页对象
        Page<UserDO> page = new Page<>(current, pageSize);
        LambdaQueryWrapper<UserDO> wrapper = new LambdaQueryWrapper<>();
        String username = findUserPageReqVO.getUsername();
        // 当 name 为空时设置默认值
        username = StringUtils.isBlank(username) ? "" : username;
        LocalDate startDate = findUserPageReqVO.getStartDate();
        LocalDate endDate = findUserPageReqVO.getEndDate();
        wrapper
                // 模糊查询姓名
                .like(UserDO::getUsername, username.trim())
                // 创建时间大于开始时间
                .ge(Objects.nonNull(startDate), UserDO::getCreateTime, startDate)
                // 创建时间小于结束时间
                .le(Objects.nonNull(endDate), UserDO::getCreateTime, endDate)
                // 按创建时间倒叙排列
                .orderByDesc(UserDO::getCreateTime);
        // 执行分页查询
        Page<UserDO> userDOPage = userMapper.selectPage(page, wrapper);
        List<UserDO> userDOS = userDOPage.getRecords();
        // DO 转 VO
        List<FindUserPageResVO> vos = null;
        if (!CollectionUtils.isEmpty(userDOS)) {
            vos = userDOS.stream()
                    .map(userDO -> FindUserPageResVO.builder()
                            .id(userDO.getId())
                            .username(userDO.getUsername())
                            .createTime(userDO.getCreateTime())
                            .updateTime(userDO.getUpdateTime())
                            .build())
                    .collect(Collectors.toList());
        }
        return PageResponse.success(userDOPage, vos);
    }
}
