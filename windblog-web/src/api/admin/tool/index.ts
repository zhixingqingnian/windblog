import request from "@/utils/request";
import type { ApiReq, ApiRes } from "./interface";
import type { Api } from "@/api/interface";
import { afterApiPrefix } from "@/api/admin";

/**
 * @description: 工具分页数据
 * @return {*}
 */
export const toolPage = (data: ApiReq.ToolPage) => {
  return request<Api.ApiPageReponse<ApiRes.ToolPage>>({
    method: "post",
    url: `${afterApiPrefix}/tool/page`,
    data,
  });
};

/**
 * @description: 工具列表数据
 * @return {*}
 */
export const toolList = () => {
  return request<Array<{ label: string; value: number }>>({
    method: "post",
    url: `${afterApiPrefix}/tool/list`,
  });
};

/**
 * @description: 新增/编辑工具
 * @return {*}
 */
export const saveTool = (data: ApiReq.SaveTool) => {
  return request<any>({
    method: "post",
    url: `${afterApiPrefix}/tool/save`,
    data,
  });
};

/**
 * @description: 删除工具
 * @return {*}
 */
export const deleteTool = (data: {id:number}) => {
  return request<any>({
    method: "post",
    url: `${afterApiPrefix}/tool/delete`,
    data,
  });
};

/**
 * @description: 工具内容分页数据
 * @return {*}
 */
export const toolContentPage = (data: ApiReq.ToolContentPage) => {
  return request<Api.ApiPageReponse<ApiRes.ToolContentPage>>({
    method: "post",
    url: `${afterApiPrefix}/tool/content/page`,
    data,
  });
};

/**
 * @description: 新增/编辑工具内容
 * @return {*}
 */
export const saveToolContent = (data: ApiReq.SaveToolContent) => {
  return request<any>({
    method: "post",
    url: `${afterApiPrefix}/tool/content/save`,
    data,
  });
};
/**
 * @description: 删除工具内容
 * @return {*}
 */
export const deleteToolContent = (data: {id:number}) => {
  return request<any>({
    method: "post",
    url: `${afterApiPrefix}/tool/content/delete`,
    data,
  });
};
