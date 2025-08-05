<!--
 * @Description: 
 * @Version: 1.0
 * @Author: yzq
 * @Date: 2025-06-04 13:58:27
 * @FilePath: \weblog\src\views\front\home\components\HomeCategory.vue
 * @LastEditors: yzq
 * @LastEditTime: 2025-06-06 09:29:32
-->
<template>
  <div class="home-category">
    <!-- 分类 -->
    <div class="home-card card-category">
      <Title title="分类" />
      <el-tree
        :data="treeData"
        node-key="id"
        :current-node-key="9527"
        highlight-current
        :expand-on-click-node="false"
        :props="{ children: 'children', label: 'label' }"
        @node-click="commEvent.onClickCategory"
      />
    </div>
    <!-- 标签 -->
    <div class="home-card card-tag">
      <Title title="标签" />
      <div class="tag-content">
        <el-tag
          v-for="item in tags"
          :key="item.id"
          @click="commEvent.onClickTag(item)"
          class="tag-item"
          :type="item.id === tagId ? 'primary' : 'info'"
        >
          {{ item.name }}
        </el-tag>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from "vue";
import Title from "@/components/baseComp/Title.vue";
import { categoryList, tagList } from "@/api/front";
import type { ApiRes } from "@/api/front/interface";
interface Tree {
  id: number;
  label: string;
  children?: Tree[];
}
const emit = defineEmits(["changeCategory", "changeTag"]);
// 参数
const treeData = ref<Tree[]>([]); // 分类数据
const tags = ref<ApiRes.CategoryList[]>([]); // 标签数据
const tagId = ref<number>(9527); // 标签id
// 事件
const commEvent = {
  // 获取分类列表
  getCategoryList: async () => {
    const { data } = await categoryList();
    treeData.value = [{ id: 9527, label: "全部" }].concat(
      (data || []).map((item) => ({
        id: item.id,
        label: item.name,
      }))
    );
    const { data: tagData } = await tagList();
    tags.value = [{ id: 9527, name: "全部" }].concat(tagData || []);
  },
  // 点击分类
  onClickCategory: (current: Tree) => {
    emit("changeCategory", current);
  },
  // 点击标签
  onClickTag: (item: ApiRes.CategoryList) => {
    tagId.value = item.id;
    emit("changeTag", item);
  },
};
onMounted(() => {
  commEvent.getCategoryList();
});
</script>

<style lang="scss" scoped>
.home-category {
  width: 100%;
  .home-card {
    width: 100%;
    background-color: #fff;
    padding: 10px;
    box-sizing: border-box;
    box-shadow: 0 0 10px #0000001a;
    border-radius: 5px;
  }
  .card-category {
    max-height: calc(100vh - 360px);
    overflow-y: auto;
  }
  .card-tag {
    margin-top: 20px;
    max-height: 260px;
    overflow-y: auto;
  }
  .tag-content {
    width: 100%;
    margin-top: 10px;
    display: flex;
    flex-wrap: wrap;
    gap: 10px;
    .tag-item {
      cursor: pointer;
    }
  }
}
</style>
