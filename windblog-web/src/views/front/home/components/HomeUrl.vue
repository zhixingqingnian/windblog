<template>
  <div class="home-tool">
    <div v-for="(item, index) in urlList" :key="index" class="tool-list">
      <Title :title="item.title" />
      <div v-for="(n, i) in item.childList" :key="i" class="list-item">
        <el-link :href="n.link" target="_blank">{{ n.label }}</el-link>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import Title from "@/components/baseComp/Title.vue";
const urlList = [
  {
    title: "UI库",
    childList: [
      { label: "Element Plus", link: "https://element-plus.org/zh-CN/" },
      { label: "Ant Design", link: "https://ant-design.antgroup.com/index-cn" },
      { label: "Vant", link: "https://vant-ui.github.io/vant/#/zh-CN" },
      { label: "uView", link: "https://uviewui.com/" },
      { label: "React Icons", link: "https://react-icons.github.io/react-icons/" },
    ],
  },
  {
    title: "图表库",
    childList: [{ label: "Apache Echarts", link: "https://echarts.apache.org/zh/index.html" }],
  },
];
</script>

<style lang="scss" scoped>
.home-tool {
  width: 100%;
  max-height: calc(100vh - 80px);
  overflow-y: auto;
  background-color: #fff;
  padding: 10px;
  box-sizing: border-box;
  box-shadow: 0 0 10px #0000001a;
  border-radius: 5px;
  .tool-list {
    width: 100%;
    margin-bottom: 5px;
    .list-item {
      width: 100%;
      padding-left: 5px;
      box-sizing: border-box;
    }
  }
}
</style>
