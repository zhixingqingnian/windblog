<!--
 * @Description: 页码组件
 * @Version: 1.0
 * @Author: yzq
 * @Date: 2025-06-05 16:19:41
 * @FilePath: \weblog\src\components\ProTable\Pager.vue
 * @LastEditors: yzq
 * @LastEditTime: 2025-06-12 17:41:08
-->
<template>
  <el-pagination
    v-model:current-page="config.current"
    v-model:page-size="config.pageSize"
    :total="total"
    @change="commEvent.onChange"
    v-bind="{
      ...pageProps,
      layout: pageProps?.layout || 'total, sizes, prev, pager, next, jumper',
      pageSizes: pageProps?.pageSizes || [10, 25, 50, 100],
      background: true,
    }"
  />
</template>

<script setup lang="ts">
import type { PaginationProps } from "element-plus";
import type { PropType } from "vue";
defineProps({
  config: { type: Object as PropType<{ current: number; pageSize: number }>, default: () => {} },
  pageProps: { type: Object as PropType<PaginationProps>, default: undefined },
  total: { type: Number, default: 0 },
});
const emit = defineEmits(["change"]);
// 方法
const commEvent = {
  // 改变页码
  onChange: (current: number, pageSize: number) => {
    emit("change", { current, pageSize });
  },
};
</script>

<style lang="scss" scoped></style>
