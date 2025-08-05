<!--
 * @Description: 
 * @Version: 1.0
 * @Author: yzq
 * @Date: 2025-06-04 11:34:21
 * @FilePath: \weblog\src\layout\front\UserAvatar.vue
 * @LastEditors: yzq
 * @LastEditTime: 2025-06-05 15:26:10
-->
<template>
  <div class="user-avatar-box">
    <el-dropdown trigger="click" @command="commEvent.onCommand">
      <div class="user-option">
        <el-avatar :size="30" :src="userStore.authorInfo.avatar" />
        <div class="user-name">{{ userStore.authorInfo.author }}</div>
      </div>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item command="1">
            <el-icon><User /></el-icon>账户信息
          </el-dropdown-item>
          <el-dropdown-item command="2">
            <el-icon><Monitor /></el-icon>后台管理
          </el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from "vue-router";
import { useUserStore } from "@/store/user";
// 参数
const router = useRouter();
const userStore = useUserStore();
// 事件
const commEvent = {
  // 下拉项选择
  onCommand: (type: string) => {
    switch (type) {
      case "1":
        router.replace("/front/account");
        break;
      case "2":
        router.push("/after");
        break;
    }
  },
};
</script>

<style lang="scss" scoped>
.user-avatar-box {
  .user-option {
    cursor: pointer;
    margin-left: 10px;
    display: flex;
    align-items: center;
    .user-name {
      font-size: 14px;
      margin-left: 5px;
    }
  }
}
</style>
