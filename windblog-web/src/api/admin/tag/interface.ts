// 标签请求参数类型
export namespace ApiReq {
  // 标签分页
  export interface TagPage {
    current: number;
    pageSize: number;
    name?: string;
    startDate?: string;
    endDate?: string;
  }
  // 批量添加标签
  export interface BatchAddTag {
    tags: string[];
  }
  // 编辑标签
  export interface ModifyTag {
    id: number;
    name: string;
  }
}
// 标签接收参数类型
export namespace ApiRes {
  // 标签分页
  export interface TagPage {
    id: number;
    name: string;
    createTime: string;
    updateTime: string;
  }
  // 标签下拉项
  export interface TagList {
    label: string;
    value: string;
  }
}
