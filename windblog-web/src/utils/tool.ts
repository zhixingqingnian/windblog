/*
 * @Description: 工具方法
 * @Version: 1.0
 * @Author: yzq
 * @Date: 2025-06-11 10:26:22
 * @FilePath: \weblog\src\utils\tool.ts
 * @LastEditors: yzq
 * @LastEditTime: 2025-06-12 16:46:53
 */
import { useUserStore } from "@/store/user";
import router from "@/router";
import { v4 as uuidv4 } from "uuid";
// 退出登录
const loginOut = async () => {
  const userStore = useUserStore();
  userStore.clearToken();
  router.replace("/login");
};

// 生成uuid
const generateUid = (): string => uuidv4();

export default {
  loginOut,
  generateUid,
};
