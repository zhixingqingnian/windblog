// 后台请求参数类型
export namespace ApiReq {
  // 分页文章数据
  export interface ArticlePage {
    current: number;
    pageSize: number;
    categoryId?: number;
    tagId?: number;
  }
}
// 后台接收类型
export namespace ApiRes {
  // 分页文章数据
  export interface ArticlePage {
    category: { id: number; name: string };
    cover: string;
    createTime: string;
    id: number;
    summary: string;
    tags: Array<{ id: number; name: string }>;
    title: string;
  }
  // 文章详情数据
  export interface ArticleDetail {
    categoryId: number;
    categoryName: string;
    content: string;
    createTime: string;
    nextArticle: { articleId: number; articleTitle: string } | null;
    preArticle: { articleId: number; articleTitle: string } | null;
    readNum: number;
    tags: Array<{ id: number; name: string }>;
    title: string;
  }
  // 分页列表
  export interface CategoryList {
    id: number;
    name: string;
  }
  // 博客详情
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
  // 工具列表
  export interface ToolList {
    id: number;
    name: string;
    icon: string;
  }
  // 工具内容列表
  export interface ToolContentList {
    id: number;
    toolId: number;
    link: string;
    name: string;
    icon: string;
  }
}
