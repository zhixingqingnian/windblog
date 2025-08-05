package com.zhixing.weblog.admin.service;

import com.zhixing.weblog.admin.model.vo.tool.*;
import com.zhixing.weblog.comm.utils.PageResponse;
import com.zhixing.weblog.comm.utils.Response;

public interface AdminToolService {
    /**
     * 分页查询工具数据
     *
     * @param findToolPageReqVO
     * @return
     */
    Response<PageResponse<FindToolPageResVO>> findToolPage(FindToolPageReqVO findToolPageReqVO);

    /**
     * 新增/编辑工具
     *
     * @param saveToolReqVO
     * @return
     */
    Response saveTool(SaveToolReqVO saveToolReqVO);


    /**
     * 删除工具
     *
     * @param DeleteToolReq
     * @return
     */
    Response deleteTool(DeleteToolReq deleteToolReq);


    /**
     * 工具列表
     *
     * @return
     */
    Response toolList();

    /**
     * 分页查询工具内容数据
     *
     * @param findToolContentPageReqVO
     * @return
     */
    Response<PageResponse<FindToolContentPageResVO>> findToolContentPage(FindToolContentPageReqVO findToolContentPageReqVO);

    /**
     * 新增/编辑工具
     *
     * @param saveToolReqVO
     * @return
     */
    Response saveToolContent(SaveToolContentReqVO saveToolReqVO);

    /**
     * 删除工具内容
     *
     * @param DeleteToolContentReq
     * @return
     */
    Response deleteToolContent(DeleteToolContentReq deleteToolContentReq);
}
