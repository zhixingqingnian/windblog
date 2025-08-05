// 工具请求参数类型
export namespace ApiReq {
  // 工具分页
  export interface ToolPage {
    current: number;
    pageSize: number;
    name?: string;
    startDate?: string;
    endDate?: string;
  }
  // 新增/编辑工具
  export interface SaveTool {
    id?: number;
    name: string;
    icon: string;
  }
  // 工具内容分页数据
  export interface ToolContentPage {
    current: number;
    pageSize: number;
    name?: string;
    toolId?: number;
    startDate?: string;
    endDate?: string;
  }
  // 新增/编辑工具内容
  export interface SaveToolContent {
    id?: number;
    toolId: number;
    name: string;
    icon: string;
    link: string;
  }
}
// 工具接收参数类型
export namespace ApiRes {
  // 工具分页
  export interface ToolPage {
    id: number;
    name: string;
    icon: string;
    createTime: string;
    updateTime: string;
  }
  // 标签下拉项
  export interface TagList {
    label: string;
    value: string;
  }
  // 工具内容分页数据
  export interface ToolContentPage {
    id: number;
    toolId: number;
    name: string;
    icon: string;
    link: string;
    createTime: string;
    updateTime: string;
  }
}
