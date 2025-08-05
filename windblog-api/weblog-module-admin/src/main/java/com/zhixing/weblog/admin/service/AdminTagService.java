package com.zhixing.weblog.admin.service;

import com.zhixing.weblog.admin.model.vo.tag.*;
import com.zhixing.weblog.comm.utils.PageResponse;
import com.zhixing.weblog.comm.utils.Response;

public interface AdminTagService {
    /**
     * 批量添加标签
     */
    Response batchAddTag(BatchAddTagReqVO batchAddTagReqVO);

    /**
     * 编辑标签
     */
    Response modifyTag(ModifyTagReqVo modifyTagReqVo);

    /**
     * 分页查询标签数据
     */
    Response<PageResponse<FindTagPageResVO>> findTagPage(FindTagPageReqVO findTagPageReqVO);

    /**
     * 获取标签列表
     *
     * @return
     */
    Response findTagList(FindTagListReqVO findTagListReqVO);

    /**
     * 删除标签
     *
     * @param deleteTagReqVO
     * @return
     */
    Response deleteTag(DeleteTagReqVO deleteTagReqVO);
}
