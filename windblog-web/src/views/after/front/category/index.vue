<!--
 * @Description: 分类管理
 * @Version: 1.0
 * @Author: yzq
 * @Date: 2025-06-05 12:13:32
 * @FilePath: \weblog\src\views\after\front\category\index.vue
 * @LastEditors: yzq
 * @LastEditTime: 2025-06-16 10:09:32
-->
<template>
  <div>
    <pro-table ref="tableRef" :columns="columns" :data-source="tableData" @searh="commEvent.onSearch" @page-change="commEvent.onPageChange">
      <!-- 顶部操作项 -->
      <template #headerOption>
        <el-row justify="end">
          <el-button type="primary" icon="Plus" @click="commEvent.onSave()">新增</el-button>
        </el-row>
      </template>
      <!-- 操作 -->
      <template #operation="{ row }">
        <el-button type="primary" link @click="commEvent.onSave(row)">编辑</el-button>
        <el-button type="danger" link @click="commEvent.onDelete(row)">删除</el-button>
      </template>
    </pro-table>
    <!-- 新增/编辑分类 -->
    <save-category v-model="saveVisible" @refresh="commEvent.onSearch" :row="currentRow" />
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from "vue";
import ProTable from "@/components/ProTable/index.vue";
import SaveCategory from "./components/SaveCategory.vue";
import { ElMessage, ElMessageBox } from "element-plus";
import type { ColumnsProps, ProTableInstance } from "@/components/ProTable/interface";
import { categoryPage, deleteCategory } from "@/api/admin/category";
import type { ApiReq, ApiRes } from "@/api/admin/category/interface";
// 参数
const tableRef = ref<ProTableInstance>(); // 表格实例
const tableData = ref<ApiRes.CategoryPage[]>([]); // 表格数据
const saveVisible = ref<boolean>(false); // 新增/编辑分类弹窗
const currentRow = ref<ApiRes.CategoryPage>(); // 当前行数据
// 页码参数
const pageParams = ref({
  current: 1,
  pageSize: 10,
});
// 表格列配置项
const columns: ColumnsProps[] = [
  { type: "index" },
  { label: "分类名称", prop: "name", formProps: {} },
  {
    label: "创建时间",
    prop: "createTime",
    formProps: { type: "date-picker", span: 2 },
    searchProps: { type: "daterange", valueFormat: "YYYY-MM-DD" },
  },
  { label: "更新时间", prop: "updateTime" },
  { label: "操作", prop: "operation", width: 160, fixed: "right" },
];
// 事件
const commEvent = {
  // 获取表格数据
  getTableData: async (params: ApiReq.CategoryPage) => {
    const { data } = await categoryPage(params);
    tableData.value = data.result;
  },
  // 刷新数据
  onRefresh: () => {
    if (!tableRef.value) return;
    let _searchs: any = { ...tableRef.value?.searchRef.formValue };
    if (_searchs?.createTime) {
      _searchs = {
        ..._searchs,
        startDate: _searchs.createTime[0],
        endDate: _searchs.createTime[1],
      };
      delete _searchs.createTime;
    }
    const params = { ...pageParams.value, ..._searchs };
    commEvent.getTableData(params);
  },
  // 查询
  onSearch: () => {
    if (pageParams.value.current !== 1) return;
    commEvent.onRefresh();
  },
  // 页码变化
  onPageChange: (pages: { current: number; pageSize: number }) => {
    pageParams.value = pages;
    commEvent.onRefresh();
  },
  // 新增/编辑
  onSave: (row?: ApiRes.CategoryPage) => {
    currentRow.value = row;
    saveVisible.value = true;
  },
  // 删除
  onDelete: (row: ApiRes.CategoryPage) => {
    ElMessageBox.confirm("确定删除该分类吗？", "提示", {
      confirmButtonText: "确定",
      cancelButtonText: "取消",
      type: "warning",
    })
      .then(async () => {
        const res = await deleteCategory({ id: row.id });
        if (res.success) {
          ElMessage.success("删除成功");
          commEvent.onSearch();
        }
      })
      .catch(() => {});
  },
};
onMounted(() => {
  commEvent.getTableData(pageParams.value);
});
</script>

<style lang="scss" scoped></style>
