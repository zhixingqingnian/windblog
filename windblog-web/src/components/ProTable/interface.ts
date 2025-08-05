/*
 * @Description:
 * @Version: 1.0
 * @Author: yzq
 * @Date: 2025-06-11 15:12:05
 * @FilePath: \weblog\src\components\ProTable\interface.ts
 * @LastEditors: yzq
 * @LastEditTime: 2025-06-13 09:53:04
 */

import type { PaginationProps, TableProps } from "element-plus";

// 表格配置项
export interface ProTableProps<T = any> {
  columns: ColumnsProps<T>[];
  dataSource: T[];
  pageParams?: {
    current: number;
    pageSize: number;
  };
  tableProps?: TableProps<T>;
  pageProps?: PaginationProps;
}
// 表格列配置项
export interface ColumnsProps<T = any> {
  type?: "selection" | "radio" | "index";
  label?: string;
  prop?: string;
  width?: number | string;
  fixed?: "center" | "left" | "right";
  formProps?: {
    type?: "input" | "select" | "date-picker";
    span?: number;
  };
  enum?: any;
  align?: "center" | "left" | "right";
  hidden?: boolean;
  showSearch?: boolean;
  searchProps?: any;
  render?: (row: T) => any;
}
// 表格实例
export interface ProTableInstance {
  isSelected: boolean;
  selectedList: { [key: string]: any }[];
  selectedListIds: Array<string | number>;
  setSelection: (rowArr: { [key: string]: any }[]) => void;
  clearSelection: () => void;
  pageParams: Array<{ current: number; pageSize: number }>;
  searchRef: ProSearchInstance;
}
// 搜索框实例
export interface ProSearchInstance {
  formValue: { [key: string]: any };
}
