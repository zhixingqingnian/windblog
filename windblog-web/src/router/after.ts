// 后台路由信息
const routes: CommType.Routes[] = [
  {
    id: "001",
    path: "/after/home",
    name: "after-home",
    icon: "DataBoard",
    meta: { title: "工作台" },
    component: () => import("@/views/after/home/index.vue"),
    children: [],
  },
  {
    id: "002",
    path: "/after/front",
    name: "after-front",
    icon: "Memo",
    meta: { title: "前台管理" },
    component: () => import("@/components/Container.vue"),
    children: [
      {
        id: "002-1",
        name: "after-front-category",
        meta: { title: "分类管理" },
        path: "/after/front/category",
        component: () => import("@/views/after/front/category/index.vue"),
      },
      {
        id: "002-2",
        name: "after-front-tag",
        meta: { title: "标签管理" },
        path: "/after/front/tag",
        component: () => import("@/views/after/front/tag/index.vue"),
      },
      {
        id: "002-3",
        name: "after-front-article",
        meta: { title: "文章管理" },
        path: "/after/front/article",
        component: () => import("@/views/after/front/article/index.vue"),
      },
      {
        id: "002-4",
        path: "/after/front/tool",
        name: "after-front-tool",
        icon: "Brush",
        meta: { title: "工具管理" },
        component: () => import("@/components/Container.vue"),
        children: [
          {
            id: "002-4-1",
            name: "after-front-tool-menu",
            meta: { title: "工具菜单" },
            path: "/after/front/tool/menu",
            component: () => import("@/views/after/front/tool/menu.vue"),
          },
          {
            id: "002-4-1",
            name: "after-front-tool-list",
            meta: { title: "工具列表" },
            path: "/after/front/tool/list",
            component: () => import("@/views/after/front/tool/list.vue"),
          },
        ],
      },
    ],
  },
  {
    id: "003",
    path: "/after/system",
    name: "after-system",
    icon: "Operation",
    meta: { title: "系统管理" },
    component: () => import("@/components/Container.vue"),
    children: [
      {
        id: "002-1",
        name: "after-system-user",
        meta: { title: "用户信息" },
        path: "/after/system/user",
        component: () => import("@/views/after/system/user/index.vue"),
      },
      {
        id: "002-2",
        name: "after-system-author",
        meta: { title: "作者配置" },
        path: "/after/system/author",
        component: () => import("@/views/after/system/author/index.vue"),
      },
    ],
  },
];
// 获取后台路由信息
export const afterRoutes = (data?: CommType.Routes[], noAdd?: boolean): any[] => {
  let _routes: any[] = [];
  const _data: CommType.Routes[] = data || routes;
  _data.forEach((item) => {
    const _obj = { path: item.path, name: item.name, meta: item.meta, component: item.component };
    _routes.push(_obj);
    if (item.children && item.children.length > 0) {
      if (noAdd) {
        _routes = [..._routes, ...afterRoutes(item.children, true)];
      } else {
        _routes = [..._routes, ...afterRoutes(item.children, true)];
      }
    }
  });
  return _routes;
};
// 获取后台路由导航菜单
export const afterMenus = (data?: CommType.Routes[]): CommType.Menus[] => {
  const _data: CommType.Routes[] = data || routes;
  if (!_data || _data.length === 0) return [];
  return _data.map((item) => ({
    id: item.id,
    name: item.meta.title,
    path: item.path,
    icon: item.icon,
    children: item.children ? afterMenus(item.children) : [],
  }));
};
