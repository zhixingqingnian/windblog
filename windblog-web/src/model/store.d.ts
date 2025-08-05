/*
 * @Description: 状态管理类型
 * @Version: 1.0
 * @Author: yzq
 * @Date: 2025-06-04 09:10:39
 * @FilePath: \weblog\src\model\store.d.ts
 * @LastEditors: yzq
 * @LastEditTime: 2025-06-11 11:27:43
 */
declare namespace StoreType {
  // 用户信息
  interface UserStore {
    token: string;
    authorInfo: {
      avatar?: string;
      name?: string;
      author: string;
      csdnHomepage?: string;
      giteeHomepage?: string;
      githubHomepage?: string;
      introduction?: string;
      juejinHomepage?: string;
      logo?: string;
    };
    userInfo: { username: string };
  }
}
