<!--
 * @Description: 
 * @Version: 1.0
 * @Author: yzq
 * @Date: 2025-06-05 09:56:10
 * @FilePath: \weblog\src\views\after\home\index.vue
 * @LastEditors: yzq
 * @LastEditTime: 2025-06-12 11:38:07
-->
<template>
  <div class="after-home-box">
    <el-row :gutter="20">
      <el-col v-for="item in statisticData" :key="item.key" :span="6">
        <div class="home-card">
          <div class="card-title">{{ item.title }}</div>
          <div class="card-count">{{ item.count }}</div>
        </div>
      </el-col>
    </el-row>
    <div class="charts-container">
      <div class="chart-header">
        <div class="header-title">文章发布统计</div>
        <div class="header-option">
          <el-segmented v-model="date" :options="dateOption" @change="commEvent.changeDate" />
        </div>
      </div>
      <div class="chart-line">
        <line-chart :data="articleStatistics" />
      </div>
    </div>
    <div class="charts-container">
      <div class="chart-header">
        <div class="header-title">pv 访问量统计</div>
      </div>
      <div class="chart-line">
        <bar-chart :data="pvOption" />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import { statistics, publishArticleStatistics, pvStatistics } from "@/api/admin";
import LineChart from "@/components/charts/LineChart.vue";
import BarChart from "@/components/charts/BarChart.vue";
interface StatisticsType {
  title: string;
  count: number;
  key: string;
}
// 参数
const articleStatistics = ref<{ [key: string]: number }>();
const pvOption = ref<{ [key: string]: number }>();
const date = ref<number>(0); // 选择的时间类型
const dateOption = ref([
  { label: "周", value: 0 },
  { label: "月", value: 1 },
  { label: "年", value: 2 },
]);
// 统计数据
const statisticData = ref<StatisticsType[]>([
  { title: "文章", count: 0, key: "articleTotalCount" },
  { title: "分类", count: 0, key: "categoryTotalCount" },
  { title: "标签", count: 0, key: "tagTotalCount" },
  { title: "浏览量", count: 0, key: "pvTotalCount" },
]);
// 事件
const commEvent = {
  // 获取统计数据
  getStatistics: async () => {
    const { data } = await statistics();
    statisticData.value.forEach((item) => {
      const _key = item.key as keyof typeof data; // 确保类型安全
      item.count = data[_key] || 0; // 设置统计数据
    });
  },
  // 获取发布文章统计数据
  getPublishArticleStatistics: async () => {
    const { data } = await publishArticleStatistics({ type: date.value });
    articleStatistics.value = data;
  },
  getPvStatistics: async () => {
    const { data } = await pvStatistics();
    let _option: any = {};
    for (const index in data.pvDates) {
      _option[data.pvDates[index]] = data.pvCounts[index];
    }
    pvOption.value = _option;
  },
  // 修改日期信息
  changeDate: () => {
    commEvent.getPublishArticleStatistics();
  },
};
onMounted(() => {
  commEvent.getStatistics();
  commEvent.getPublishArticleStatistics();
  commEvent.getPvStatistics();
});
</script>

<style lang="scss" scoped>
.after-home-box {
  width: 100%;
  .home-card {
    width: 100%;
    border-radius: 5px;
    padding: 10px 20px 20px;
    background-color: #fff;
    box-sizing: border-box;
    box-shadow: 5px 5px 10px #0000001a;
    .card-title {
      font-size: 16px;
      font-weight: bold;
      color: #111;
      margin-bottom: 10px;
      margin-bottom: 10px;
    }
    .card-count {
      width: 100%;

      text-align: center;
      font-size: 18px;
      font-weight: bold;
      color: $main-color;
    }
  }
  .charts-container {
    width: 100%;
    margin-top: 20px;
    border-radius: 5px;
    padding: 10px 20px 20px;
    background-color: #fff;
    box-sizing: border-box;
    box-shadow: 5px 5px 10px #0000001a;
    .chart-header {
      width: 100%;
      display: flex;
      .header-title {
        font-size: 16px;
        font-weight: bold;
        color: #111;
      }
      .header-option {
        flex: 1;
        display: flex;
        justify-content: flex-end;
        align-items: center;
      }
    }
    .chart-line {
      width: 100%;
      height: 400px;
    }
  }
}
</style>
