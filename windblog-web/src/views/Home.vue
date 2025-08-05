<!--
 * @Description: 
 * @Version: 1.0
 * @Author: yzq
 * @Date: 2025-06-03 17:00:44
 * @FilePath: \weblog\src\views\Home.vue
 * @LastEditors: yzq
 * @LastEditTime: 2025-06-05 09:34:03
-->
<template>
  <div id="front-home" class="home-box">
    <!-- 顶部 -->
    <Header />
    <div class="fill-box" />
    <!-- 内容区 -->
    <div class="p-10">
      <router-view />
    </div>
  </div>
</template>

<script setup lang="ts">
import Header from "@/layout/front/Header.vue";
import { onMounted } from "vue";
import { blogDetail } from "@/api/front";
import { useUserStore } from "@/store/user";
const userStore = useUserStore();
const commEvent = {
  init: async () => {
    if (userStore.authorInfo?.name) return;
    const { data } = await blogDetail();
    userStore.setAuthorInfo(data);
  },
};
onMounted(() => {
  commEvent.init();
});
</script>

<style lang="scss" scoped>
.p-10 {
  padding: 10px;
  box-sizing: border-box;
}
.home-box {
  width: 100vw;
  height: 100vh;
  background-color: #f3f3f3;
  overflow-y: auto;
  .fill-box {
    width: 100%;
    height: 60px;
  }
}
</style>
