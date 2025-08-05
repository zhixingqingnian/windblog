import request from "@/utils/request";
import type { ApiReq, ApiRes } from "./interface";
import type { Api } from "@/api/interface";
import { afterApiPrefix } from "@/api/admin";

/**
 * @description: 用户分页数据
 * @return {*}
 */
export const userPage = (data: ApiReq.UserPage) => {
  return request<Api.ApiPageReponse<ApiRes.UserPage>>({
    method: "post",
    url: `${afterApiPrefix}/user/page`,
    data,
  });
};