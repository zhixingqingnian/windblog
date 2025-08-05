/*
 * @Description:
 * @Version: 1.0
 * @Author: yzq
 * @Date: 2025-06-12 15:45:48
 * @FilePath: \weblog\src\api\admin\category\index.ts
 * @LastEditors: yzq
 * @LastEditTime: 2025-06-16 10:08:54
 */
import request from "@/utils/request";
import type { ApiReq, ApiRes } from "./interface";
import type { Api } from "@/api/interface";
import { afterApiPrefix } from "@/api/admin";

/**
 * @description: 分类分页数据
 * @return {*}
 */
export const categoryPage = (data: ApiReq.CategoryPage) => {
  return request<Api.ApiPageReponse<ApiRes.CategoryPage>>({
    method: "post",
    url: `${afterApiPrefix}/category/page`,
    data,
  });
};
/**
 * @description: 新增分类
 * @return {*}
 */
export const addCategory = (data: ApiReq.SaveCategory) => {
  return request<any>({
    method: "post",
    url: `${afterApiPrefix}/category/save`,
    data,
  });
};

/**
 * @description: 删除分类
 * @return {*}
 */
export const deleteCategory = (data: { id: number }) => {
  return request<any>({
    method: "post",
    url: `${afterApiPrefix}/category/delete`,
    data,
  });
};

/**
 * @description: 分类列表
 * @return {*}
 */
export const categoryList = () => {
  return request<ApiRes.CategoryList[]>({
    method: "post",
    url: `${afterApiPrefix}/category/list`,
  });
};
