/*
 * @Description:
 * @Version: 1.0
 * @Author: yzq
 * @Date: 2025-06-05 18:22:06
 * @FilePath: \weblog\src\api\index.ts
 * @LastEditors: yzq
 * @LastEditTime: 2025-06-10 17:30:07
 */
import request from "@/utils/request";
import type { Api } from "@/api/interface";
import type { ApiReq, ApiRes } from "./interface";
/**
 * @description: 分页文章数据
 * @return {*}
 */
export const articlePage = (data: ApiReq.ArticlePage) => {
  return request<Api.ApiPageReponse<ApiRes.ArticlePage>>({
    method: "post",
    url: "/article/page",
    data,
  });
};

/**
 * @description: 文章详情数据
 * @return {*}
 */
export const articleDetail = (data: { articleId: number }) => {
  return request<ApiRes.ArticleDetail>({
    method: "post",
    url: "/article/detail",
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
    url: "/category/list",
  });
};
/**
 * @description: 标签列表
 * @return {*}
 */
export const tagList = () => {
  return request<ApiRes.CategoryList[]>({
    method: "post",
    url: "/tag/list",
  });
};

/**
 * @description: 博客详情
 * @return {*}
 */
export const blogDetail = () => {
  return request<ApiRes.BlogDetail>({
    method: "post",
    url: "/blog/settings/detail",
  });
};

/**
 * @description: 工具列表
 * @return {*}
 */
export const toolList = () => {
  return request<ApiRes.ToolList[]>({
    method: "post",
    url: "/tool/list",
  });
};

/**
 * @description: 工具内容列表
 * @return {*}
 */
export const toolContentList = (data:{ id: number }) => {
  return request<ApiRes.ToolContentList[]>({
    method: "post",
    url: "/tool/content/list",
    data
  });
};
