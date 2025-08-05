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
import type { ApiReq, ApiRes } from "./interface";
/**
 * @description: 登录
 * @return {*}
 */
export const login = (data: ApiReq.Login) => {
  return request<ApiRes.Login>({
    method: "post",
    url: "/login",
    data,
  });
};

/**
 * @description: 文件上传
 * @return {*}
 */
export const uploadFile = (data: FormData) => {
  return request<{ url: string }>({
    method: "post",
    url: "/admin/file/upload",
    data,
  });
};
