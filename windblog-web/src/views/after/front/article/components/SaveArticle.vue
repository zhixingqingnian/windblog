<!--
 * @Description: 新增/编辑分类
 * @Version: 1.0
 * @Author: yzq
 * @Date: 2025-05-23 10:58:54
 * @FilePath: \weblog\src\views\after\front\category\components\SaveCategory.vue
 * @LastEditors: yzq
 * @LastEditTime: 2025-06-13 14:10:29
-->
<template>
  <el-dialog :title="`${row?.id ? '编辑' : '新增'}文章`" :model-value="modelValue" fullscreen @close="commEvent.onClose">
    <el-form ref="formRef" :model="formValue" :rules="rules">
      <el-row :gutter="20">
        <el-col :span="24">
          <el-form-item label="封面图" prop="cover">
            <Upload v-model="formValue.cover" :max-count="1" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="标题" prop="title">
            <el-input v-model="formValue.title" placeholder="请输入标题"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="分类" prop="categoryId">
            <el-select v-model="formValue.categoryId" placeholder="请选择分类">
              <el-option v-for="item in categoryOptions" :key="item.value" :label="item.label" :value="item.value" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="标签" prop="tags">
            <el-select
              v-model="formValue.tags"
              multiple
              filterable
              remote
              allow-create
              default-first-option
              remote-show-suffix
              placeholder="请选择标签"
              :loading="loading"
              :remote-method="commEvent.remoteMethod"
            >
              <el-option v-for="item in tagOptions" :key="item.value" :label="item.label" :value="item.value" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="摘要" prop="summary">
            <el-input v-model="formValue.summary" type="textarea" :rows="3" placeholder="请输入摘要"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="内容" prop="summary">
            <Markdown v-model="formValue.content" />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <el-affix position="bottom" :offset="20" style="width: 100%">
      <el-row justify="end">
        <el-button @click="commEvent.onClose">取消</el-button>
        <el-button type="primary" @click="commEvent.onEnter">确定</el-button>
      </el-row>
    </el-affix>
  </el-dialog>
</template>

<script setup lang="ts">
import { nextTick, ref, watch } from "vue";
import type { PropType } from "vue";
import Upload from "@/components/Upload/index.vue";
import Markdown from "@/components/Markdown/index.vue";
import { ElMessage, type FormInstance, type FormRules } from "element-plus";
import { publishArticle, updateArticle, articleDetail } from "@/api/admin/article";
import type { ApiReq, ApiRes } from "@/api/admin/article/interface";
import { categoryList } from "@/api/admin/category";
import { tagList } from "@/api/admin/tag";
import tool from "@/utils/tool";
interface OptionType {
  label: string;
  value: string;
}
const props = defineProps({
  modelValue: { type: Boolean, default: false },
  row: { type: Object as PropType<ApiRes.ArticlePage>, default: () => {} },
});
const emit = defineEmits(["update:modelValue", "refresh"]);
// 参数
const formRef = ref<FormInstance>(); // 表单实例
const categoryOptions = ref<OptionType[]>([]); // 分类下拉项
const tagOptions = ref<OptionType[]>([]); // 标签下拉项
const loading = ref<boolean>(false); // 远程搜索加载状态
// 表单数据
const formValue = ref({
  cover: [] as any[],
  title: "",
  categoryId: undefined as undefined | number,
  tags: [] as any[],
  summary: "",
  content: "",
});
// 表单校验
const rules: FormRules = {
  cover: [{ required: true, message: "请上传封面", trigger: "change" }],
  title: [{ required: true, message: "请输入标题", trigger: "blur" }],
  categoryId: [{ required: true, message: "请选择分类", trigger: "change" }],
  tags: [{ required: true, message: "请选择标签", trigger: "change" }],
};
// 事件
const commEvent = {
  // 初始化
  init: async () => {
    const { data: categoryData } = await categoryList();
    categoryOptions.value = categoryData;
    if (props.row?.id) {
      commEvent.getDetail(props.row.id);
    }
  },
  // 获取详情信息
  getDetail: async (id: number) => {
    const { data } = await articleDetail({ id });
    const { data: tagData } = await tagList({ name: "" });
    formValue.value = {
      cover: [{ uid: tool.generateUid(), url: data.cover }],
      title: data.title,
      categoryId: data.categoryId,
      tags: tagData.filter((item) => data.tagIds.includes(Number(item.value))).map((item) => item.label),
      summary: data.summary,
      content: data.content,
    };
  },
  // 关闭弹窗
  onClose: () => {
    emit("update:modelValue", false);
  },
  // 提交表单
  onEnter: () => {
    if (!formRef.value) return;
    formRef.value.validate(async (valid) => {
      if (!valid) return;
      let params: ApiReq.SaveArticle = {
        ...formValue.value,
        categoryId: Number(formValue.value.categoryId),
        cover: formValue.value.cover[0].url,
      };
      let res: any;
      if (props.row?.id) {
        params.id = props.row.id;
        res = await updateArticle(params);
      } else {
        res = await publishArticle(params);
      }
      if (res.success) {
        ElMessage.success("操作成功");
        commEvent.onClose();
        emit("refresh");
      }
    });
  },
  // 远程搜索
  remoteMethod: async (value: string) => {
    if (!value) {
      tagOptions.value = [];
      return;
    }
    loading.value = true;
    const { data } = await tagList({ name: value }).finally(() => {
      loading.value = false;
    });
    tagOptions.value = data;
  },
};
// 监听
watch(
  () => props.modelValue,
  (val) => {
    if (val) {
      nextTick(() => {
        if (!formRef.value) return;
        formRef.value.resetFields();
        commEvent.init();
      });
    }
  }
);
</script>

<style lang="scss" scoped></style>
