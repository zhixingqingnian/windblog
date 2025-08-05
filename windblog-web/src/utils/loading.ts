/*
 * @Description:
 * @Version: 1.0
 * @Author: yzq
 * @Date: 2025-06-05 18:04:03
 * @FilePath: \weblog\src\utils\loading.ts
 * @LastEditors: yzq
 * @LastEditTime: 2025-06-06 09:18:55
 */
import { ElLoading, ElMessage } from "element-plus";
import nprogress from "nprogress";
import "nprogress/nprogress.css";
import router from "@/router";
import { useUserStore } from "@/store/user";
// 参数
let loadCount: number = 0; // 加载数量
let loading: any; // 加载
// 显示加载
export const showLoading = () => {
  if (loadCount === 0) {
    loading = ElLoading.service({
      lock: true,
      text: "加载中...",
      background: "rgba(0,0,0,0.7)",
    });
  }
  loadCount++;
};
// 隐藏加载
export const hideLoading = (status?: number) => {
  if (loadCount <= 0) return;
  loadCount--;
  if (loadCount === 0) {
    loading.close();
    if (status && status === 401) {
      const userStore = useUserStore();
      ElMessage.error("登录过期，请重新登录");
      userStore.clearToken();
      router.replace("/login");
    }
  }
};
// 页面进度条配置
nprogress.configure({
  easing: "ease", // 动画方式
  speed: 500, // 递增进度条的速度
  showSpinner: true, // 是否显示加载ico
  trickleSpeed: 200, // 自动递增间隔
  minimum: 0.3, // 初始化时的最小百分比
});
// 页面进度条显示
export const showNprogress = () => {
  nprogress.start();
};
// 页面进度条隐藏
export const hideNprogress = () => {
  nprogress.done();
};
