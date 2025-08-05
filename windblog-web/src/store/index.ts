/*
 * @Description:
 * @Version: 1.0
 * @Author: yzq
 * @Date: 2025-06-03 17:56:40
 * @FilePath: \weblog\src\store\index.ts
 * @LastEditors: yzq
 * @LastEditTime: 2025-06-03 18:06:32
 */
import { createPinia } from "pinia";
import piniaPluginPersistedstate from "pinia-plugin-persistedstate";

const pinia = createPinia();
pinia.use(piniaPluginPersistedstate);

export default pinia;
