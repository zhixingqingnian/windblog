<!--
 * @Description: 
 * @Version: 1.0
 * @Author: yzq
 * @Date: 2025-06-03 16:49:16
 * @FilePath: \weblog\src\App.vue
 * @LastEditors: yzq
 * @LastEditTime: 2025-06-12 15:11:22
-->
<template>
  <el-config-provider :locale="zhCn">
    <router-view />
  </el-config-provider>
</template>

<script setup lang="ts">
import { ElConfigProvider } from "element-plus";
import zhCn from "element-plus/es/locale/lang/zh-cn";
</script>

<style lang="scss">
html,
body {
  margin: 0;
  padding: 0;
}
</style>
