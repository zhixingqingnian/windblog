// 分类请求参数类型
export namespace ApiReq {
  // 分类分页数据
  export interface CategoryPage {
    current: number;
    pageSize: number;
    name?: string;
    startDate?: string;
    endDate?: string;
  }
  // 新增分类
  export interface SaveCategory {
    id?: number;
    name: string;
  }
}
// 分类接收参数类型
export namespace ApiRes {
  // 分类分页数据
  export interface CategoryPage {
    id: number;
    name: string;
    createTime: string;
    updateTime: string;
  }
  // 分类列表
  export interface CategoryList {
    label: string;
    value: string;
  }
}
