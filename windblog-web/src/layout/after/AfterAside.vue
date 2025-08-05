<!--
 * @Description: 侧边栏菜单
 * @Version: 1.0
 * @Author: yzq
 * @Date: 2025-06-05 14:37:10
 * @FilePath: \weblog\src\layout\after\AfterAside.vue
 * @LastEditors: yzq
 * @LastEditTime: 2025-06-11 09:58:17
-->
<template>
  <template v-for="item in menus" :key="item.id">
    <el-sub-menu v-if="item.children?.length" :index="item.path">
      <template #title>
        <el-icon v-if="item.icon"><component :is="item.icon" /></el-icon>
        <span>{{ item.name }}</span>
      </template>
      <after-aside :menus="item.children" />
    </el-sub-menu>
    <el-menu-item v-else :index="item.path" @click="commEvent.onClick(item)">
      <el-icon v-if="item.icon"><component :is="item.icon" /></el-icon>
      <span>{{ item.name }}</span>
    </el-menu-item>
  </template>
</template>

<script setup lang="ts">
import type { PropType } from "vue";
import { useRouter } from "vue-router";
defineProps({
  menus: { type: Array as PropType<CommType.Menus[]>, default: () => [] },
});
// 参数
const router = useRouter();
// 事件
const commEvent = {
  onClick: (item: CommType.Menus) => {
    if (item.path) {
      router.push(item.path);
    }
  },
};
</script>

<style lang="scss" scoped>
.el-menu-item {
  &:hover {
    color: #f5f5f5;
    background-color: #000;
  }
  &.is-active {
    color: #f5f5f5;
    background-color: #000;
    &::before {
      content: "";
      position: absolute;
      left: 0;
      top: 0;
      bottom: 0;
      width: 4px;
      background-color: $main-color;
    }
  }
}
</style>
