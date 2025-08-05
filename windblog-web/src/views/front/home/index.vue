<!--
 * @Description: 
 * @Version: 1.0
 * @Author: yzq
 * @Date: 2025-06-04 13:40:52
 * @FilePath: \weblog\src\views\front\home\index.vue
 * @LastEditors: yzq
 * @LastEditTime: 2025-06-05 18:43:50
-->
<template>
  <div class="front-home-box">
    <el-row :gutter="20">
      <!-- 分类信息 -->
      <el-col :span="6">
        <home-category @change-category="commEvent.changeCategory" @change-tag="commEvent.changeTag" />
      </el-col>
      <!-- 文章列表 -->
      <el-col :span="12">
        <home-list :category-id="categoryId" :tag-id="tagId" />
      </el-col>
      <!-- 展示栏 -->
      <el-col :span="6">
        <home-url />
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import HomeCategory from "@/views/front/home/components/HomeCategory.vue";
import HomeList from "@/views/front/home/components/HomeList.vue";
import HomeUrl from "@/views/front/home/components/HomeUrl.vue";
import { ref } from "vue";
// 参数
const categoryId = ref<number>(0); // 分类id
const tagId = ref<number>(0); // 标签id
// 事件
const commEvent = {
  changeCategory: (item: { id: number; label: string }) => {
    categoryId.value = item.id;
  },
  changeTag: (item: { id: number; value: string }) => {
    tagId.value = item.id;
  },
};
</script>

<style lang="scss" scoped>
.front-home-box {
  width: 100%;
}
</style>
