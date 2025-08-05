/*
 * @Description:
 * @Version: 1.0
 * @Author: yzq
 * @Date: 2025-06-03 16:49:16
 * @FilePath: \weblog\src\main.ts
 * @LastEditors: yzq
 * @LastEditTime: 2025-06-07 14:18:03
 */
import { createApp } from "vue";
import App from "./App.vue";
const app = createApp(App);
// 饿了么组件样式
import "element-plus/theme-chalk/el-loading.css";
import "element-plus/theme-chalk/el-message.css";
import "element-plus/theme-chalk/el-message-box.css";
// 图片预览
import "viewerjs/dist/viewer.css";
import VueViewer from "v-viewer";
// 路由
import router from "@/router";
// 状态仓库
import store from "@/store";
// 注册饿了么图标
import * as Icons from "@element-plus/icons-vue";
Object.keys(Icons).forEach((key) => {
  app.component(key, Icons[key as keyof typeof Icons]);
});

app.use(router);
app.use(store);
app.use(VueViewer);
app.mount("#app");
