// 用户请求参数类型
export namespace ApiReq {
  // 用户分页
  export interface UserPage {
    current: number;
    pageSize: number;
    username?: string;
    startDate?: string;
    endDate?: string;
  }
}
// 用户接收参数类型
export namespace ApiRes {
  // 用户分页
  export interface UserPage {
    id: number;
    username: string;
    createTime: string;
    updateTime: string;
  }
}
