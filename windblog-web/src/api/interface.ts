import type { AxiosRequestHeaders, AxiosRequestConfig } from "axios";
// api 类型定义
export namespace Api {
  // api请求参数
  export interface RequestConfig extends AxiosRequestConfig {
    headers?: AxiosRequestHeaders;
    loading?: boolean;
  }
  // api请求拦截器使用
  export interface RequestInterceptorsConfig extends RequestConfig {
    headers: AxiosRequestHeaders;
  }
  // 接收参数类型
  export interface ApiReponse<T> {
    success: boolean;
    message: string;
    code: number;
    data: T;
  }
  // 接收参数类型
  export interface ApiPageReponse<T> {
    current: number;
    pageSize: number;
    result: T[];
    total: number;
    pages: number;
  }
}
// 通用接口请求参数
export namespace ApiReq {
  // 登录
  export interface Login {
    username: string;
    password: string;
  }
}
// 通用接口接收参数
export namespace ApiRes {
  // 登录
  export interface Login {
    token: string;
  }
}
