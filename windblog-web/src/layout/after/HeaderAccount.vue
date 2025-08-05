<!--
 * @Description: 顶部账号信息
 * @Version: 1.0
 * @Author: yzq
 * @Date: 2025-06-05 14:37:10
 * @FilePath: \weblog\src\layout\after\HeaderAccount.vue
 * @LastEditors: yzq
 * @LastEditTime: 2025-06-11 11:32:38
-->
<template>
  <div class="header-account">
    <el-dropdown trigger="click" @command="commEvent.onCommand">
      <div class="user-avatar">
        <el-avatar :size="30" :src="_avatar" />
        <div class="user-name">{{ userStore.userInfo.username }}</div>
      </div>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item command="1">
            <el-icon><User /></el-icon>账户设置
          </el-dropdown-item>
          <el-dropdown-item command="2">
            <el-icon><Position /></el-icon>跳转前台
          </el-dropdown-item>
          <el-dropdown-item command="3" divided>
            <el-icon><SwitchButton /></el-icon>退出登录
          </el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
    <!-- 账户设置 -->
    <account-settings v-model="settingVisible" />
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import AccountSettings from "@/layout/after/components/AccountSettings.vue";
import { useRouter } from "vue-router";
import { useUserStore } from "@/store/user";
import { ElMessageBox } from "element-plus";
import tool from "@/utils/tool";
// 参数
const _avatar = "https://img1.baidu.com/it/u=4118625424,727122652&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=500";
const router = useRouter();
const userStore = useUserStore();
const settingVisible = ref<boolean>(false); // 账户设置设置弹窗
// 事件
const commEvent = {
  // 下拉项选择
  onCommand: (type: string) => {
    switch (type) {
      case "1":
        settingVisible.value = true;
        break;
      case "2":
        router.replace("/");
        break;
      case "3":
        ElMessageBox.confirm("确定要退出登录吗？", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(() => {
            tool.loginOut();
          })
          .catch(() => {});
        break;
    }
  },
};
</script>

<style lang="scss" scoped>
.header-account {
  display: flex;
  align-items: center;
  .user-avatar {
    display: flex;
    align-items: center;
    cursor: pointer;
    .user-name {
      font-size: 14px;
      font-weight: bold;
      margin-left: 10px;
    }
  }
}
</style>
