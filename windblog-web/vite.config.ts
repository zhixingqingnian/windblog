/*
 * @Description:
 * @Version: 1.0
 * @Author: yzq
 * @Date: 2025-06-03 16:49:16
 * @FilePath: \weblog\vite.config.ts
 * @LastEditors: yzq
 * @LastEditTime: 2025-06-10 16:45:51
 */
import { defineConfig, loadEnv } from "vite";
import type { ConfigEnv, UserConfig } from "vite";
import vue from "@vitejs/plugin-vue";
import { resolve } from "path";
// 自动按需加载组件
import AutoImport from "unplugin-auto-import/vite";
import Components from "unplugin-vue-components/vite";
import { ElementPlusResolver } from "unplugin-vue-components/resolvers";

export default defineConfig(({ mode }: ConfigEnv): UserConfig => {
  // 根据当前工作模式加载不同的环境变量
  const env = loadEnv(mode, process.cwd());
  return {
    plugins: [vue(), AutoImport({ resolvers: [ElementPlusResolver()] }), Components({ resolvers: [ElementPlusResolver({ importStyle: "sass" })] })],
    resolve: {
      alias: {
        "@": resolve(__dirname, "./src"),
      },
    },
    css: {
      preprocessorOptions: {
        scss: {
          additionalData: `@use "@/styles/global.scss" as *;`,
        },
      },
    },
    server: {
      host: true,
      port: 7001, // 设置端口号
      proxy: {
        [env.VITE_API_HEADER]: {
          target: env.VITE_API_PROXY,
          changeOrigin: true,
          rewrite: (path) => path.replace(/^\/api/, ""),
        },
      },
    },
  };
});
