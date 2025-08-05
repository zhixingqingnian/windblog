import request from "@/utils/request";
import type { ApiReq, ApiRes } from "./interface";
import type { Api } from "@/api/interface";
import { afterApiPrefix } from "@/api/admin";

/**
 * @description: 文章分页数据
 * @return {*}
 */
export const articlePage = (data: ApiReq.ArticlePage) => {
  return request<Api.ApiPageReponse<ApiRes.ArticlePage>>({
    method: "post",
    url: `${afterApiPrefix}/article/page`,
    data,
  });
};
/**
 * @description: 文章详情
 * @return {*}
 */
export const articleDetail = (data: { id: number }) => {
  return request<ApiRes.ArticleDetail>({
    method: "post",
    url: `${afterApiPrefix}/article/detail`,
    data,
  });
};
/**
 * @description: 发布文章
 * @return {*}
 */
export const publishArticle = (data: ApiReq.SaveArticle) => {
  return request<any>({
    method: "post",
    url: `${afterApiPrefix}/article/publish`,
    data,
  });
};
/**
 * @description: 更新文章
 * @return {*}
 */
export const updateArticle = (data: ApiReq.SaveArticle) => {
  return request<any>({
    method: "post",
    url: `${afterApiPrefix}/article/update`,
    data,
  });
};
/**
 * @description: 删除文章
 * @return {*}
 */
export const deleteArticle = (data: ApiReq.DeleteArticle) => {
  return request<any>({
    method: "post",
    url: `${afterApiPrefix}/article/delete`,
    data,
  });
};
