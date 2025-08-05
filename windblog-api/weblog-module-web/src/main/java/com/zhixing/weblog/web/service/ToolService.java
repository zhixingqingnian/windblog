package com.zhixing.weblog.web.service;

import com.zhixing.weblog.comm.utils.Response;
import com.zhixing.weblog.web.model.vo.tool.FindToolContentListReqVO;

public interface ToolService {
    /**
     * 工具列表
     *
     * @return
     */
    Response findToolList();

    /**
     * 工具内容列表
     *
     * @param findToolContentListReqVO
     * @return
     */
    Response findToolContentList(FindToolContentListReqVO findToolContentListReqVO);
}
