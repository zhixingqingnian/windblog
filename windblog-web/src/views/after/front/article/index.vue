<template>
  <div class="article-box">
    <pro-table ref="tableRef" :data-source="tableData" :columns="columns" @searh="commEvent.onSearch" @page-change="commEvent.onPageChange">
      <!-- 顶部配置项 -->
      <template #headerOption>
        <el-row justify="end">
          <el-button type="primary" icon="Plus" @click="commEvent.onSave()">新增</el-button>
        </el-row>
      </template>
      <!-- 封面 -->
      <template #cover="{ row }">
        <el-image :src="row.cover" class="cover-img" />
      </template>
      <!-- 内容 -->
      <template #content="{ row }">
        <el-button type="primary" link @click="commEvent.onLook(row)">查看</el-button>
      </template>
      <template #operation="{ row }">
        <el-button type="primary" link @click="commEvent.onSave(row)">编辑</el-button>
        <el-button type="danger" link @click="commEvent.onDelete(row)">删除</el-button>
      </template>
    </pro-table>
    <!-- 新增/编辑文章 -->
    <save-article v-model="saveVisible" :row="currentRow" @refresh="commEvent.onSearch" />
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from "vue";
import ProTable from "@/components/ProTable/index.vue";
import SaveArticle from "./components/SaveArticle.vue";
import type { ColumnsProps, ProTableInstance } from "@/components/ProTable/interface";
import { ElMessageBox, ElMessage } from "element-plus";
import { articlePage, deleteArticle } from "@/api/admin/article";
import type { ApiReq, ApiRes } from "@/api/admin/article/interface";
import { shortcuts } from "@/utils/config";
import { useRouter } from "vue-router";
// 参数
const router = useRouter();
const tableRef = ref<ProTableInstance>(); // 表格实例
const tableData = ref<ApiRes.ArticlePage[]>([]); // 表格数据
const saveVisible = ref<boolean>(false); // 新增/编辑分类弹窗
const currentRow = ref<ApiRes.ArticlePage>(); // 当前行数据
// 页码参数
const pageParams = ref({
  current: 1,
  pageSize: 10,
});
const columns: ColumnsProps<any>[] = [
  { type: "index" },
  { prop: "title", label: "标题", formProps: {} },
  { prop: "cover", label: "封面" },
  { prop: "content", label: "内容" },
  {
    label: "创建时间",
    prop: "createTime",
    formProps: { type: "date-picker", span: 2 },
    searchProps: { type: "daterange", valueFormat: "YYYY-MM-DD", shortcuts },
  },
  { label: "更新时间", prop: "updateTime" },
  { label: "操作", prop: "operation", width: 160, fixed: "right" },
];
// 方法
const commEvent = {
  // 获取表格数据
  getTableData: async (params: ApiReq.ArticlePage) => {
    const { data } = await articlePage(params);
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
  // 查看
  onLook: (row: ApiRes.ArticlePage) => {
    router.push(`/front/article/${row.id}`);
  },
  // 新增/编辑文章
  onSave: (row?: ApiRes.ArticlePage) => {
    currentRow.value = row;
    saveVisible.value = true;
  },
  // 删除
  onDelete: (row: ApiRes.ArticlePage) => {
    ElMessageBox.confirm("是否删除当前文章", {
      confirmButtonText: "确认",
      cancelButtonText: "取消",
      type: "warning",
    })
      .then(async () => {
        const { success } = await deleteArticle({ id: row.id });
        if (success) {
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

<style lang="scss" scoped>
.article-box {
  .cover-img {
    width: 80px;
  }
}
</style>
