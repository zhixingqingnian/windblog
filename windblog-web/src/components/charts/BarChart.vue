<!--
 * @Description: 折线图
 * @Version: 1.0
 * @Author: yzq
 * @Date: 2025-06-05 16:19:41
 * @FilePath: \weblog\src\components\charts\LineChart.vue
 * @LastEditors: yzq
 * @LastEditTime: 2025-06-11 09:55:19
-->
<template>
  <div ref="container" class="bar-chart" />
</template>

<script setup lang="ts">
import { ref, onMounted, watch, computed } from "vue";
import type { PropType } from "vue";
import * as echarts from "echarts";

const props = defineProps({
  data: { type: Object as PropType<{ [key: string]: any }>, default: () => ({}) },
});

// 参数
const container = ref<any>(); // 容器实例
// 图表数据
const chart = computed(() => {
  if (!props.data || Object.keys(props.data).length === 0) {
    return { xData: [], seriesData: [] };
  }
  const _xAxis = Object.keys(props.data).map((key) => key);
  const _series = Object.values(props.data).map((value) => value);
  return { xData: _xAxis, seriesData: _series };
});
// 事件
const commEvent = {
  // 加载图表
  loadChart: () => {
    let myCharts = echarts.getInstanceByDom(container.value);
    if (!myCharts) {
      myCharts = echarts.init(container.value);
    } else {
      myCharts.dispose();
      myCharts = echarts.init(container.value);
    }
    const option = {
      grid: {
        left: "3%",
        right: "4%",
        bottom: "5%",
        containLabel: true,
      },
      tooltip: {
        trigger: "axis",
      },
      xAxis: {
        type: "category",
        data: chart.value.xData,
      },
      yAxis: {
        type: "value",
      },
      series: [
        {
          data: chart.value.seriesData,
          type: "bar",
        },
      ],
    };
    option && myCharts.setOption(option);
  },
};
watch(
  () => props.data,
  (newData) => {
    if (Object.keys(newData).length > 0) {
      commEvent.loadChart();
    }
  },
  { immediate: true }
);
onMounted(() => {
  commEvent.loadChart();
});
</script>

<style lang="scss" scoped>
.bar-chart {
  width: 100%;
  height: 100%;
}
</style>
