<!--
 * @Description: 顶部
 * @Version: 1.0
 * @Author: yzq
 * @Date: 2025-06-05 14:37:10
 * @FilePath: \weblog\src\layout\after\AfterHeader.vue
 * @LastEditors: yzq
 * @LastEditTime: 2025-06-11 15:01:37
-->
<template>
  <div class="after-header">
    <!-- 展开/收起 -->
    <div class="header-icons">
      <el-icon v-if="modelValue" @click="$emit('update:modelValue', false)"><Expand /></el-icon>
      <el-icon v-else @click="$emit('update:modelValue', true)"><Fold /></el-icon>
    </div>
    <!-- 面包屑 -->
    <div class="header-breadcrumb">
      <div v-for="(item, index) in breadcrumbs" :key="item.id" class="breadcrumb-list" @click="commEvent.onClick(item, index)">
        <el-icon v-if="item.icon"><component :is="item.icon" /></el-icon>
        <span class="breadcrumb-text">{{ item.name }}</span>
        <el-icon v-if="index !== breadcrumbs.length - 1" class="breadcrumb-right"><ArrowRight /></el-icon>
      </div>
    </div>
    <!-- 用户信息 -->
    <div class="header-user">
      <header-account />
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from "vue";
import type { PropType } from "vue";
import HeaderAccount from "@/layout/after/HeaderAccount.vue";
import { useRoute, useRouter } from "vue-router";
const props = defineProps({
  modelValue: { type: Boolean, default: false },
  menus: { type: Array as PropType<CommType.Menus[]>, default: () => [] },
});
// 参数
const route = useRoute();
const router = useRouter();
// 事件
const commEvent = {
  // 调整menu结构
  formatMenu: (data: CommType.Menus[], parent?: string[]): CommType.Menus[] => {
    let _menus: CommType.Menus[] = [];
    if (data && data.length > 0) {
      data.forEach((item) => {
        const _obj: CommType.Menus = { ...item, allPath: [...(parent || []), item.path], children: [] };
        if (item.children && item.children.length > 0) {
          _obj.children = commEvent.formatMenu(item.children, _obj.allPath);
        }
        _menus = [..._menus, _obj];
      });
    }
    return _menus;
  },
  // 获取当前所有路径信息
  getAllPath: (data: CommType.Menus[], path: string): string[] => {
    let _allPath: string[] = [];
    if (data && data.length > 0) {
      data.forEach((item) => {
        if (item.path === path) {
          _allPath = item.allPath || [];
        } else {
          if (item.children && item.children.length > 0 && _allPath.length <= 0) {
            _allPath = commEvent.getAllPath(item.children, path);
          }
        }
      });
    }
    return _allPath;
  },
  // 获取全部路劲所对应的菜单
  getAllMenus: (data: CommType.Menus[], paths: string[]): CommType.Menus[] => {
    let _menus: CommType.Menus[] = [];
    if (data && data.length > 0) {
      data.forEach((item) => {
        if (paths.some((val) => val === item.path)) {
          _menus = [..._menus, item];
          if (item.children && item.children.length > 0) {
            _menus = [..._menus, ...commEvent.getAllMenus(item.children, paths)];
          }
        }
      });
    }
    return _menus;
  },
  // 点击面包屑
  onClick: (item: CommType.Menus, index: number) => {
    if (index === breadcrumbs.value.length - 1) return;
    router.replace(item.path);
  },
};
const breadcrumbs = computed(() => {
  let _breadcrumbs: CommType.Menus[] = [{ id: "001", name: "首页", path: "/after", icon: "HomeFilled" }];
  const _menus = commEvent.formatMenu(JSON.parse(JSON.stringify(props.menus)));
  const _path = route.path;
  const fistMenu = _menus.find((item) => item.path === _path && (!item.children || item.children.length <= 0));
  if (fistMenu) {
    return [fistMenu];
  } else {
    const _allPath = commEvent.getAllPath(_menus, _path);
    const _allMenus = commEvent.getAllMenus(_menus, _allPath);
    _breadcrumbs = [..._breadcrumbs, ..._allMenus];
  }
  return _breadcrumbs;
});
</script>

<style lang="scss" scoped>
.after-header {
  width: 100%;
  height: 60px;
  padding: 0 10px;
  box-sizing: border-box;
  display: flex;
  align-items: center;
  background-color: #fff;
  .header-icons {
    font-size: 22px;
    cursor: pointer;
  }
  .header-breadcrumb {
    display: flex;
    gap: 5px;
    margin-left: 20px;
    .breadcrumb-list {
      display: flex;
      align-items: center;
      gap: 5px;
      font-size: 16px;
      cursor: pointer;
      &:hover {
        color: $main-color;
      }
      .breadcrumb-text {
        font-size: 14px;
        font-weight: bold;
      }
      .breadcrumb-right {
        font-size: 14px;
        color: #999;
      }
    }
  }
  .header-user {
    flex: 1;
    display: flex;
    justify-content: flex-end;
    margin-left: 20px;
  }
}
</style>
