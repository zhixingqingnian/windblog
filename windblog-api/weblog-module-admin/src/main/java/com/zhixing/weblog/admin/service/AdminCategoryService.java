package com.zhixing.weblog.admin.service;

import com.zhixing.weblog.admin.model.vo.category.FindCategoryPageResVO;
import com.zhixing.weblog.admin.model.vo.category.SaveCategoryReqVO;
import com.zhixing.weblog.admin.model.vo.category.DeleteCategoryReqVO;
import com.zhixing.weblog.admin.model.vo.category.FindCategoryPageReqVO;
import com.zhixing.weblog.comm.utils.PageResponse;
import com.zhixing.weblog.comm.utils.Response;

public interface AdminCategoryService {
    /**
     * 添加/编辑分类
     *
     * @param saveCategoryReqVO
     * @return
     */
    Response saveCategory(SaveCategoryReqVO saveCategoryReqVO);

    /**
     * 分页查询分类
     *
     * @param findCategoryPageReqVO
     * @return
     */
    Response<PageResponse<FindCategoryPageResVO>> findCategoryPage(FindCategoryPageReqVO findCategoryPageReqVO);

    /**
     * 删除分类
     *
     * @param deleteCategoryReqVO
     * @return
     */
    Response deleteCategory(DeleteCategoryReqVO deleteCategoryReqVO);

    /**
     * 获取分类列表
     *
     * @return
     */
    Response findCategoryList();
}
