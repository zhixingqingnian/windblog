<!--
 * @Description: 高级表格组件
 * @Version: 1.0
 * @Author: yzq
 * @Date: 2025-06-05 16:19:41
 * @FilePath: \weblog\src\components\ProTable\index.vue
 * @LastEditors: yzq
 * @LastEditTime: 2025-06-13 10:25:04
-->
<template>
  <div class="protable-box">
    <!-- 搜索框 -->
    <div v-if="hasSearch" class="table-card mb-10">
      <Search ref="searchRef" :config="columns" @search="commEvent.onSearch" />
    </div>
    <!-- 表格 -->
    <div class="table-card">
      <div class="table-main">
        <slot name="headerOption" />
        <el-table ref="tableRef" border v-bind="{ ...tableProps, data: dataSource }" @selection-change="selectionChange">
          <template v-for="item in columns" :key="item.prop">
            <template v-if="!item.hidden">
              <!-- 表格列 -->
              <el-table-column v-if="item?.type === 'selection'" type="selection" :width="80" :reserve-selection="true" />
              <el-table-column v-else-if="item?.type === 'index'" label="序号" :width="item?.width ?? 80" :align="item?.align ?? 'center'">
                <template #default="scope">
                  {{ (pageParams.current - 1) * pageParams.pageSize + scope.$index + 1 }}
                </template>
              </el-table-column>
              <el-table-column
                v-else
                :label="item.label"
                :prop="item.prop"
                :width="item.width"
                :align="item?.align ?? 'center'"
                :fixed="item?.fixed"
                show-overflow-tooltip
              >
                <!-- 自定义插槽 -->
                <template #default="scope">
                  <slot :name="item.prop" :row="scope.row"> {{ scope.row[item.prop || ""] || "--" }}</slot>
                </template>
              </el-table-column>
            </template>
          </template>
        </el-table>
        <!-- 页码 -->
        <el-row v-if="total > 0" justify="end">
          <Pager :config="pageParams" :total="total" :page-props="pageProps" @change="commEvent.onPageChange" />
        </el-row>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, ref } from "vue";
import Search from "./Search.vue";
import Pager from "./Pager.vue";
import { useSelection } from "@/hooks/userSection";
import type { ProSearchInstance, ProTableProps } from "./interface";
import type { TableInstance } from "element-plus";
const props = withDefaults(defineProps<ProTableProps>(), {
  columns: () => [],
  dataSource: () => [],
  tableProps: undefined,
  pageProps: undefined,
});
const emit = defineEmits(["searh", "pageChange"]);
// 参数
const searchRef = ref<ProSearchInstance>(); // 搜索框实例
const tableRef = ref<TableInstance>(); // 表格实例
// 页码信息
const pageParams = ref({
  current: 1,
  pageSize: 10,
});
const total = computed<number>(() => props.dataSource.length); // 总条数
// 事件
const commEvent = {
  // 查询
  onSearch: (value: { [key: string]: any }) => {
    pageParams.value.current = 1;
    emit("searh", value);
  },
  // 页码变化
  onPageChange: (pages: ProTableProps["pageParams"]) => {
    emit("pageChange", pages);
  },
};
// 多选处理
const { selectionChange, isSelected, selectedList, selectedListIds } = useSelection(props.tableProps?.rowKey as string);
// 清空选中数据列表
const clearSelection = () => tableRef.value!.clearSelection();
// 设置选中数据列表
const setSelection = (rowArr: { [key: string]: any }[]) => {
  selectionChange(rowArr);
  rowArr.forEach((row) => {
    tableRef.value!.toggleRowSelection(row, true);
  });
};
// 是否存在查询项
const hasSearch = computed<Boolean>(() => {
  return props.columns.some((item) => item?.formProps);
});
defineExpose({
  isSelected,
  selectedList,
  selectedListIds,
  setSelection,
  clearSelection,
  pageParams,
  searchRef,
});
</script>

<style lang="scss" scoped>
.protable-box {
  width: 100%;
  .table-card {
    width: 100%;
    padding: 5px 10px;
    box-sizing: border-box;
    background-color: #fff;
    border-radius: 5px;
    box-shadow: 0 0 10px #0000001a;
  }
  .table-main {
    width: 100%;
    padding: 15px 10px;
    box-sizing: border-box;
    display: flex;
    flex-direction: column;
    gap: 15px;
    :deep(.el-table) {
      flex: 1;
      table {
        width: 100%;
      }
      .el-table__header-wrapper {
        th {
          height: 45px;
          font-size: 14px;
          font-weight: bold;
          color: var(--el-text-color-primary);
          background: var(--el-fill-color-light);
        }
      }
    }
  }
}
</style>
