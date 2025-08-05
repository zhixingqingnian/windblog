// 后台请求参数类型
export namespace ApiReq {
  // 更新密码
  export interface UpdatePassword {
    username: string;
    password: string;
  }
  // 更新博客
  export interface SaveBlog {
    logo: string;
    name: string;
    author: string;
    introduction: string;
    avatar: string;
    githubHomepage?: string;
    csdnHomepage?: string;
    giteeHomepage?: string;
    juejinHomepage?: string;
  }
}
// 后台接收类型
export namespace ApiRes {
  // 用户信息
  export interface UserInfo {
    username: string;
  }
  // 博客信息
  export interface BlogDetail {
    logo: string;
    name: string;
    author: string;
    introduction: string;
    avatar: string;
    githubHomepage: string;
    csdnHomepage: string;
    giteeHomepage: string;
    juejinHomepage: string;
  }
  // 数据统计
  export interface Statistics {
    articleTotalCount: number;
    categoryTotalCount: number;
    tagTotalCount: number;
    pvTotalCount: number;
  }
  // pv数据统计
  export interface PvStatistics {
    pvDates: string[];
    pvCounts: number[];
  }
}
