/*
 * @Description:
 * @Version: 1.0
 * @Author: yzq
 * @Date: 2025-06-04 09:09:48
 * @FilePath: \weblog\src\model\comm.d.ts
 * @LastEditors: yzq
 * @LastEditTime: 2025-06-10 15:46:42
 */
declare namespace CommType {
  // 路由菜单
  interface Routes {
    id: string;
    path: string;
    name: string;
    icon?: string;
    meta: { title: string };
    component: () => any;
    children?: Array<Routes>;
  }
  // 侧边栏菜单
  interface Menus {
    id: string;
    name: string;
    path: string;
    allPath?: string[];
    icon?: string;
    children?: Menus[];
  }
}
