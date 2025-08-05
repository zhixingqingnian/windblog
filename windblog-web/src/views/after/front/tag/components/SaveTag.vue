<!-- 新增/编辑标签 -->
<template>
  <el-dialog :title="`${row?.id ? '编辑' : '新增'}标签`" :model-value="modelValue" width="500px" @close="commEvent.onClose">
    <el-form ref="formRef" :model="formValue" :rules="rules">
      <template v-if="row?.id">
        <el-form-item label="标签名称" prop="name">
          <el-input v-model="formValue.name" placeholder="请输入标签名称" />
        </el-form-item>
      </template>
      <template v-else>
        <el-form-item label="标签名称" prop="tags">
          <el-input-tag v-model="formValue.tags" placeholder="请输入标签名称" />
        </el-form-item>
      </template>
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
import type { FormInstance, FormRules } from "element-plus/lib";
import { batchAddTag, modifyTag } from "@/api/admin/tag";
import type { ApiRes } from "@/api/admin/tag/interface";
import { ElMessage } from "element-plus";

const props = defineProps({
  modelValue: { type: Boolean, default: false },
  row: { type: Object as PropType<ApiRes.TagPage>, default: () => {} },
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
  name: [{ required: true, message: "请输入标签名称", trigger: "blur" }],
  tags: [{ required: true, message: "请输入标签名称", trigger: "blur" }],
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
      let res: any;
      if (props.row?.id) {
        res = await modifyTag({ id: props.row.id, name: formValue.value.name });
      } else {
        res = await batchAddTag({ tags: formValue.value.tags });
      }
      if (res.success) {
        commEvent.onClose();
        emit("refresh");
        ElMessage.success("操作成功");
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
          formValue.value.name = props.row.name;
        }
      });
    }
  }
);
</script>

<style lang="scss" scoped></style>
