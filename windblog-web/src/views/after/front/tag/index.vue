<!--
 * @Description: 
 * @Version: 1.0
 * @Author: yzq
 * @Date: 2025-06-13 14:16:56
 * @FilePath: \weblog\src\views\after\front\tag\index.vue
 * @LastEditors: yzq
 * @LastEditTime: 2025-06-16 10:09:14
-->
<template>
  <div>
    <pro-table ref="tableRef" :data-source="tableData" :columns="columns" @searh="commEvent.onSearch" @page-change="commEvent.onPageChange">
      <!-- 顶部配置项 -->
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
    <!-- 新增/编辑弹窗 -->
    <save-tag v-model="saveVisible" :row="currentRow" @refresh="commEvent.onSearch" />
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import ProTable from "@/components/ProTable/index.vue";
import SaveTag from "@/views/after/front/tag/components/SaveTag.vue";
import type { ColumnsProps, ProTableInstance } from "@/components/ProTable/interface";
import { ElMessageBox, ElMessage } from "element-plus";
import { tagPage, deleteTag } from "@/api/admin/tag";
import type { ApiReq, ApiRes } from "@/api/admin/tag/interface";
// 参数
const tableRef = ref<ProTableInstance>(); // 表格实例
const tableData = ref<ApiRes.TagPage[]>([]); // 表格数据
const saveVisible = ref<boolean>(false); // 新增/编辑标签弹窗
const currentRow = ref<ApiRes.TagPage>(); // 当前行数据
// 页码参数
const pageParams = ref({
  current: 1,
  pageSize: 10,
});
// 表格列配置
const columns: ColumnsProps[] = [
  { type: "index" },
  { prop: "name", label: "标签名称", formProps: {} },
  {
    prop: "createTime",
    label: "创建时间",
    formProps: { type: "date-picker", span: 2 },
    searchProps: { type: "daterange", valueFormat: "YYYY-MM-DD" },
  },
  { prop: "updateTime", label: "更新时间" },
  { label: "操作", prop: "operation", width: 160, fixed: "right" },
];
// 事件
const commEvent = {
  // 获取表格数据
  getTableData: async (params: ApiReq.TagPage) => {
    const { data } = await tagPage(params);
    tableData.value = data.result;
  },
  // 刷新数据
  onRefresh: () => {
    if (!tableRef.value) return;
    let _searchs = { ...tableRef.value?.searchRef.formValue };
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
  // 新增/编辑标签
  onSave: (row?: ApiRes.TagPage) => {
    currentRow.value = row;
    saveVisible.value = true;
  },
  // 删除
  onDelete: (row: ApiRes.TagPage) => {
    ElMessageBox.confirm("确定删除该标签吗？", "提示", {
      confirmButtonText: "确定",
      cancelButtonText: "取消",
      type: "warning",
    })
      .then(async () => {
        const res = await deleteTag({ id: row.id });
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
