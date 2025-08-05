/*
 * @Description:
 * @Version: 1.0
 * @Author: yzq
 * @Date: 2025-06-03 17:02:28
 * @FilePath: \weblog\src\router\index.ts
 * @LastEditors: yzq
 * @LastEditTime: 2025-06-13 14:17:45
 */
import { hideNprogress, showNprogress } from "@/utils/loading";
import { createRouter, createWebHistory } from "vue-router";
import { afterRoutes } from "./after";

import { useUserStore } from "@/store/user";
const routes = [
  {
    path: "/",
    name: "home",
    redirect: "/front",
    component: () => import("@/views/Home.vue"),
    children: [
      {
        path: "/front",
        name: "front",
        redirect: "/front/home",
        children: [
          { path: "/front/home", name: "front-home", component: () => import("@/views/front/home/index.vue") },
          { path: "/front/tool", name: "front-tool", component: () => import("@/views/front/tool/index.vue") },
          { path: "/front/article/:id", name: "front-article", component: () => import("@/views/front/article/index.vue") },
          { path: "/front/account", name: "front-account", component: () => import("@/views/front/account/index.vue") },
        ],
      },
    ],
  },
  {
    path: "/:pathMatch(.*)*",
    component: () => import("@/views/Error.vue"),
  },
  {
    path: "/login",
    name: "login",
    component: () => import("@/views/Login.vue"),
  },
  {
    path: "/after",
    name: "after",
    component: () => import("@/views/after/index.vue"),
    redirect: "/after/home",
    children: afterRoutes(),
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});
// 路由前置守卫
router.beforeEach((to, _from, next) => {
  showNprogress();
  const userStore = useUserStore();
  // 非登录人员禁止访问后台
  if (to.path.startsWith("/after")) {
    if (!userStore.token) {
      next("/login");
      return;
    }
  }
  next();
});
// 路由后置守卫
router.afterEach((to) => {
  hideNprogress();
  if (to?.meta?.title) {
    document.title = to.meta.title as string;
  } else {
    document.title = "知性";
  }
});
// 路由错误
router.onError(() => {
  hideNprogress();
});
export default router;
