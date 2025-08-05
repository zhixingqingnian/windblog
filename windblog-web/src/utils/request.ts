/*
 * @Description:
 * @Version: 1.0
 * @Author: yzq
 * @Date: 2025-06-05 17:52:59
 * @FilePath: \weblog\src\utils\request.ts
 * @LastEditors: yzq
 * @LastEditTime: 2025-06-13 09:33:07
 */
import axios from "axios";
import { showLoading, hideLoading } from "@/utils/loading";
import { ElMessage } from "element-plus";
import { useUserStore } from "@/store/user";
import type { Api } from "@/api/interface";
const userStore = useUserStore();
// 创建请求实例
const service = axios.create({
  baseURL: "/api",
  timeout: 5000,
});
// 请求拦截
service.interceptors.request.use((config: Api.RequestInterceptorsConfig) => {
  if (typeof config.loading === "undefined" || config.loading) {
    showLoading();
  }
  let _token: string = userStore.token;
  if (_token) {
    config.headers["Authorization"] = `Bearer ${_token}`;
  }
  return config;
});
// 返回拦截
service.interceptors.response.use(
  (res) => {
    setTimeout(() => {
      hideLoading();
    }, 200);
    const { success, message } = res.data;
    if (!success) {
      ElMessage.error(message);
      return Promise.reject(res);
    }
    return res;
  },
  (error) => {
    const { status } = error.response;
    setTimeout(() => {
      hideLoading(status);
    }, 200);
    return Promise.reject(error);
  }
);
// 封装请求方法，设置接收参数类型
const request = async <T>(config: Api.RequestConfig) => {
  return service.request<Api.ApiReponse<T>>(config).then((res) => res.data);
};
export default request;
