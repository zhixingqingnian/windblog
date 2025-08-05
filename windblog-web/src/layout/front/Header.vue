<!--
 * @Description: 
 * @Version: 1.0
 * @Author: yzq
 * @Date: 2025-06-04 09:27:30
 * @FilePath: \weblog\src\layout\front\Header.vue
 * @LastEditors: yzq
 * @LastEditTime: 2025-06-10 14:05:31
-->
<template>
  <div class="front-header">
    <!-- logo -->
    <div class="header-logo">
      <el-image :src="userStore.authorInfo.logo" class="logo-img" />
      <div class="logo-text">{{ userStore.authorInfo.name }}</div>
    </div>
    <div class="header-option">
      <!-- 导航栏 -->
      <div class="tab-list">
        <div
          v-for="(item, index) in tabs"
          :key="item.key"
          @click="commEvent.tabChange(item)"
          class="tab-list-item"
          :class="{ 'tab-list-item-active': index === avtivityIndex }"
        >
          {{ item.label }}
        </div>
        <div class="tab-list-line" :style="{ left: `${avtivityIndex * 80}px` }">
          <div class="line-box" />
        </div>
      </div>
      <!-- 用户头像信息 -->
      <user-avatar />
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from "vue";
import UserAvatar from "@/layout/front/UserAvatar.vue";
import { useRoute, useRouter } from "vue-router";
import { useUserStore } from "@/store/user";
interface TabsType {
  key: string;
  label: string;
  path: string;
}
// 参数
const route = useRoute();
const router = useRouter();
const userStore = useUserStore();
// tab数据
const tabs: TabsType[] = [
  { key: "home", label: "首页", path: "/home" },
  { key: "tool", label: "工具", path: "/tool" },
];
// 事件
const commEvent = {
  // tab切换
  tabChange: (item: TabsType) => {
    router.replace(`/front${item.path}`);
  },
};
// 当前路由下标
const avtivityIndex = computed(() => {
  return tabs.findIndex((item) => `front-${item.key}` === route.name);
});
</script>

<style lang="scss" scoped>
.front-header {
  position: fixed;
  z-index: 999;
  left: 0;
  top: 0;
  width: 100%;
  height: 60px;
  display: flex;
  align-items: center;
  padding: 0 10px;
  box-sizing: border-box;
  border-bottom: 1px solid #eee;
  background-color: #ffffff80;
  backdrop-filter: blur(10px);
  box-shadow: 0 0 10px 0 #0000001a;
  .header-logo {
    display: flex;
    align-items: center;
    .logo-img {
      width: 40px;
    }
    .logo-text {
      margin-left: 10px;
      font-size: 16px;
      color: #111;
      font-weight: bold;
    }
  }
  .header-option {
    flex: 1;
    height: 100%;
    margin-left: 20px;
    display: flex;
    justify-content: flex-end;
    align-items: center;
    .tab-list {
      position: relative;
      height: 100%;
      display: flex;
      overflow: hidden;
      .tab-list-item {
        width: 80px;
        height: 100%;
        display: flex;
        font-size: 14px;
        color: #111;
        align-items: center;
        justify-content: center;
        cursor: pointer;
      }
      .tab-list-item-active {
        color: $main-color;
        font-weight: bold;
      }
      .tab-list-line {
        position: absolute;
        z-index: -1;
        width: 80px;
        height: 100%;
        top: 0;
        display: flex;
        justify-content: center;
        align-items: flex-end;
        transition: all 0.3s ease-in-out;
        .line-box {
          width: 50%;
          height: 3px;
          background-color: $main-color;
          border-radius: 3px;
        }
      }
    }
  }
}
</style>
