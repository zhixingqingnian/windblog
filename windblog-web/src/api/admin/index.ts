import request from "@/utils/request";
import type { ApiReq, ApiRes } from "./interface";
// 后台请求前缀
export const afterApiPrefix = "/admin";

/**
 * @description: 获取用户信息
 * @return {*}
 */
export const userInfo = () => {
  return request<ApiRes.UserInfo>({
    method: "post",
    url: `${afterApiPrefix}/user/info`,
  });
};

/**
 * @description: 更新密码
 * @return {*}
 */
export const updatePassword = (data: ApiReq.UpdatePassword) => {
  return request<any>({
    method: "post",
    url: `${afterApiPrefix}/password/update`,
    data,
  });
};
/**
 * @description: 博客基础信息
 * @return {*}
 */
export const blogDetail = () => {
  return request<ApiRes.BlogDetail>({
    method: "post",
    url: `${afterApiPrefix}/blog/settings/detail`,
  });
};
/**
 * @description: 更新博客
 * @return {*}
 */
export const saveBlog = (data: ApiReq.SaveBlog) => {
  return request<any>({
    method: "post",
    url: `${afterApiPrefix}/blog/settings/save`,
    data,
  });
};

/**
 * @description: 数据统计
 * @return {*}
 */
export const statistics = () => {
  return request<ApiRes.Statistics>({
    method: "post",
    url: `${afterApiPrefix}/dashboard/statistics`,
  });
};

/**
 * @description: 文章发布数据统计
 * @return {*}
 */
export const publishArticleStatistics = (data: { type: number }) => {
  return request<{ [key: string]: number }>({
    method: "post",
    url: `${afterApiPrefix}/dashboard/publishArticle/statistics`,
    data,
  });
};

/**
 * @description: pv数据统计
 * @return {*}
 */
export const pvStatistics = () => {
  return request<ApiRes.PvStatistics>({
    method: "post",
    url: `${afterApiPrefix}/dashboard/pv/statistics`,
  });
};
