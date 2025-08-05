// 标签请求参数类型
export namespace ApiReq {
  // 文章分页
  export interface ArticlePage {
    current: number;
    pageSize: number;
    title?: string;
    startDate?: string;
    endDate?: string;
  }
  // 发布文章
  export interface SaveArticle {
    id?: number;
    title: string;
    content: string;
    cover: string;
    summary?: string;
    categoryId: number;
    tags: string[];
  }
  // 删除文章
  export interface DeleteArticle {
    id: number;
  }
}
// 标签接收参数类型
export namespace ApiRes {
  // 文章分页
  export interface ArticlePage {
    id: number;
    title: string;
    cover: string;
    createTime: string;
    updateTime: string;
  }
  // 文章详情
  export interface ArticleDetail {
    id: number;
    title: string;
    cover: string;
    content: string;
    categoryId: number;
    tagIds: number[];
    summary: string;
  }
}
