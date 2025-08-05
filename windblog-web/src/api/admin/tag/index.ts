import request from "@/utils/request";
import type { ApiReq, ApiRes } from "./interface";
import type { Api } from "@/api/interface";
import { afterApiPrefix } from "@/api/admin";

/**
 * @description: 标签分页数据
 * @return {*}
 */
export const tagPage = (data: ApiReq.TagPage) => {
  return request<Api.ApiPageReponse<ApiRes.TagPage>>({
    method: "post",
    url: `${afterApiPrefix}/tag/page`,
    data,
  });
};

/**
 * @description: 标签分页数据
 * @return {*}
 */
export const tagList = (data: { name: string }) => {
  return request<ApiRes.TagList[]>({
    method: "post",
    url: `${afterApiPrefix}/tag/list`,
    loading: false,
    data,
  });
};
/**
 * @description: 批量添加标签
 * @return {*}
 */
export const batchAddTag = (data: ApiReq.BatchAddTag) => {
  return request<any>({
    method: "post",
    url: `${afterApiPrefix}/tag/batch/add`,
    data,
  });
};
/**
 * @description: 编辑标签
 * @return {*}
 */
export const modifyTag = (data: ApiReq.ModifyTag) => {
  return request<any>({
    method: "post",
    url: `${afterApiPrefix}/tag/modify`,
    data,
  });
};
/**
 * @description: 删除标签
 * @return {*}
 */
export const deleteTag = (data: { id: number }) => {
  return request<any>({
    method: "post",
    url: `${afterApiPrefix}/tag/delete`,
    data,
  });
};
