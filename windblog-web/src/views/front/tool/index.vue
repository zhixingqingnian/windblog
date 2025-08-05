<!--
 * @Description: 
 * @Version: 1.0
 * @Author: yzq
 * @Date: 2025-06-04 11:02:58
 * @FilePath: \weblog\src\views\front\tool\index.vue
 * @LastEditors: yzq
 * @LastEditTime: 2025-06-04 13:41:20
-->
<template>
  <div class="front-tool-box">
    <!-- 导航栏 -->
    <div class="tool-nav">
      <div
        v-for="item in tools"
        :key="item.id"
        class="nav-items"
        :class="{ 'active-nav': item.id === activeNav }"
        @click="commEvent.changeNav(item.id)"
      >
        <el-image :src="item.icon" class="nav-icon"></el-image>
        <div class="nav-text">{{ item.name }}</div>
      </div>
    </div>
    <!-- 展示区 -->
    <div class="tool-container">
      <el-row :gutter="10">
        <el-col v-for="item in toolContent" :key="item.id" :sm="8" :md="6" :lg="4">
          <div @click="commEvent.onLink(item.link)" class="tool-item">
            <div class="item-icon">
              <el-image class="icon-img" :src="item.icon" />
            </div>
            <div class="item-text">{{ item.name }}</div>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import { toolList, toolContentList } from "@/api/front";
import type { ApiRes } from "@/api/front/interface";

// 参数
// 工具导航数据
const activeNav = ref<number>(0); // 当前激活的导航
const tools = ref<ApiRes.ToolList[]>([]); // 工具列表
const toolContent = ref<ApiRes.ToolContentList[]>([]); // 工具内容列表
// 事件
const commEvent = {
  // 获取工具列表
  getTools: async () => {
    const { data } = await toolList();
    tools.value = data;
    if (data && data.length > 0) {
      const _id: number = data[0].id;
      activeNav.value = _id;
      commEvent.getToolCntent(_id);
    }
  },
  getToolCntent: async (id: number) => {
    const { data } = await toolContentList({ id });
    toolContent.value = data || [];
  },
  // 点击导航
  changeNav: (id: number) => {
    if (activeNav.value === id) return;
    activeNav.value = id;
    commEvent.getToolCntent(id);
  },
  onLink: (link: string) => {
    if (link) {
      window.open(link, "_blank");
    }
  },
};
onMounted(() => {
  commEvent.getTools();
});
</script>

<style lang="scss" scoped>
.front-tool-box {
  width: 100%;
  height: calc(100vh - 80px);
  background-color: #fff;
  padding: 10px;
  box-sizing: border-box;
  box-shadow: 0 0 10px #0000001a;
  border-radius: 5px;
  display: flex;
  .tool-nav {
    width: 120px;
    height: 100%;
    border-right: 1px solid #f3f3f3;
    overflow-y: auto;
    .nav-items {
      width: 100%;
      cursor: pointer;
      padding: 10px 8px;
      box-sizing: border-box;
      display: flex;
      align-items: center;
      gap: 5px;
      &:hover {
        background-color: #f3f3f3;
      }
      .nav-icon {
        width: 25px;
        height: 25px;
        border-radius: 50%;
      }
      .nav-text {
        color: #111;
        font-size: 14px;
      }
    }
    .active-nav {
      background-color: #36807710;
      color: $main-color;
      font-weight: bold;
      border-left: 3px solid $main-color;
    }
  }
  .tool-container {
    flex: 1;
    height: 100%;
    padding: 10px;
    box-sizing: border-box;
    overflow-y: auto;
    .tool-item {
      width: 100%;
      padding: 10px 5px;
      background-color: #f3f3f3;
      box-sizing: border-box;
      border-radius: 5px;
      cursor: pointer;
      &:hover {
        box-shadow: 5px 5px 10px #0000001a;
      }
      margin-bottom: 10px;
      .item-icon {
        width: 100%;
        text-align: center;
        .icon-img {
          width: 50px;
          height: 50px;
          border-radius: 50%;
        }
      }
      .item-text {
        width: 100%;
        margin-top: 10px;
        font-size: 14px;
        font-weight: bold;
        text-align: center;
      }
    }
  }
}
</style>
