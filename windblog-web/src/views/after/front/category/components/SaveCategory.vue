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
  <el-dialog :title="`${row?.id ? '编辑' : '新增'}分类`" :model-value="modelValue" width="500px" @close="commEvent.onClose">
    <el-form ref="formRef" :model="formValue" :rules="rules">
      <el-form-item label="分类名称" prop="name">
        <el-input v-model="formValue.name" placeholder="请输入分类名称"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="commEvent.onClose">取消</el-button>
      <el-button type="primary" @click="commEvent.onEnter">确定</el-button>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { nextTick, ref, watch, type PropType } from "vue";
import { ElMessage, type FormInstance, type FormRules } from "element-plus";
import { addCategory } from "@/api/admin/category";
import type { ApiRes, ApiReq } from "@/api/admin/category/interface";

const props = defineProps({
  modelValue: { type: Boolean, default: false },
  row: { type: Object as PropType<ApiRes.CategoryPage>, default: () => {} },
});
const emit = defineEmits(["update:modelValue", "refresh"]);
// 参数
const formRef = ref<FormInstance>(); // 表单实例
// 表单数据
const formValue = ref({
  name: "",
});
// 表单校验
const rules: FormRules = {
  name: [{ required: true, message: "请输入分类名称", trigger: "blur" }],
};
// 事件
const commEvent = {
  // 关闭弹窗
  onClose: () => {
    emit("update:modelValue", false);
  },
  // 提交表单
  onEnter: () => {
    if (!formRef.value) return;
    formRef.value.validate(async (valid) => {
      if (!valid) return;
      const params: ApiReq.SaveCategory = { ...formValue.value };
      if (props.row?.id) {
        params.id = props.row.id;
      }
      const res = await addCategory(params);
      if (res.success) {
        ElMessage.success("操作成功");
        emit("refresh");
        commEvent.onClose();
      }
    });
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
        if (props.row?.id) {
          const { name } = props.row;
          formValue.value = { name };
        }
      });
    }
  }
);
</script>

<style scoped></style>
