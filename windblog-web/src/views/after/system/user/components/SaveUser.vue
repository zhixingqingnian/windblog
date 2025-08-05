<!-- 新增/编辑标签 -->
<template>
  <el-dialog :title="`${row?.id ? '编辑' : '新增'}用户`" :model-value="modelValue" width="500px" @close="commEvent.onClose">
    <el-form ref="formRef" :model="formValue" :rules="rules">
      <el-form-item label="用户名称" prop="name">
        <el-input v-model="formValue.name" placeholder="请输入标签名称" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="commEvent.onClose">取消</el-button>
      <el-button type="primary" @click="commEvent.onEnter">确定</el-button>
    </template>
  </el-dialog>
</template>

<script lang="ts" setup>
import { ref, watch, nextTick } from "vue";
import type { PropType } from "vue";
import type { FormInstance, FormRules } from "element-plus";
import type { ApiRes } from "@/api/admin/user/interface";

const props = defineProps({
  modelValue: { type: Boolean, default: false },
  row: { type: Object as PropType<ApiRes.UserPage>, default: () => {} },
});
const emit = defineEmits(["update:modelValue", "refresh"]);
// 参数
const formRef = ref<FormInstance>(); // 表单实例
// 表单数据
const formValue = ref({
  tags: [] as string[],
  name: "",
});
// 表单校验
const rules: FormRules = {
  name: [{ required: true, message: "请输入用户名称", trigger: "blur" }],
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
      });
    }
  }
);
</script>

<style lang="scss" scoped></style>
