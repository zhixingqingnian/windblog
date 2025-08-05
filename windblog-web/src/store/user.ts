/*
 * @Description:
 * @Version: 1.0
 * @Author: yzq
 * @Date: 2025-06-03 17:59:56
 * @FilePath: \weblog\src\store\user.ts
 * @LastEditors: yzq
 * @LastEditTime: 2025-06-13 09:31:33
 */
import { defineStore } from "pinia";
const normalAuthorInfo = { author: "知性" };

export const useUserStore = defineStore("user", {
  state: (): StoreType.UserStore => ({
    token: "",
    // 作者信息
    authorInfo: { ...normalAuthorInfo },
    // 用户信息
    userInfo: { username: "" },
  }),
  getters: {
    getToken: (state): string => state.token,
    getAuthorInfo: (state) => state.authorInfo,
    getUserInfo: (state) => state.userInfo,
  },
  actions: {
    // 设置token
    setToken(token: string) {
      this.token = token;
    },
    // 设置作者信息
    setAuthorInfo(authorInfo: StoreType.UserStore["authorInfo"]) {
      this.authorInfo = authorInfo;
    },
    // 设置用户信息
    setUserInfo(userInfo: StoreType.UserStore["userInfo"]) {
      this.userInfo = userInfo;
    },
    // 重置作者信息
    resetAuthorInfo() {
      this.authorInfo = { ...normalAuthorInfo };
    },
    // 清除token
    clearToken() {
      this.token = "";
      this.userInfo = { username: "" };
    },
  },
  persist: {
    storage: localStorage,
  },
});
