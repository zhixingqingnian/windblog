<!-- 新增/编辑标签 -->
<template>
  <el-dialog :title="`${row?.id ? '编辑' : '新增'}菜单`" :model-value="modelValue" width="500px" @close="commEvent.onClose">
    <el-form ref="formRef" :model="formValue" :rules="rules">
      <el-form-item label="工具名称" prop="name">
        <el-input v-model="formValue.name" placeholder="请输入标签名称" />
      </el-form-item>
      <el-form-item label="工具图标" prop="icon">
        <Upload v-model="formValue.icon" :max-count="1" />
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
import Upload from "@/components/Upload/index.vue";
import type { PropType } from "vue";
import type { FormInstance, FormRules } from "element-plus";
import { saveTool } from "@/api/admin/tool";
import type { ApiRes, ApiReq } from "@/api/admin/tool/interface";
import { ElMessage } from "element-plus";

const props = defineProps({
  modelValue: { type: Boolean, default: false },
  row: { type: Object as PropType<ApiRes.ToolPage>, default: () => {} },
});
const emit = defineEmits(["update:modelValue", "refresh"]);
// 参数
const formRef = ref<FormInstance>(); // 表单实例
// 表单数据
const formValue = ref({
  name: "",
  icon: [] as any[],
});
// 表单校验
const rules: FormRules = {
  name: [{ required: true, message: "请输入工具名称", trigger: "blur" }],
  icon: [{ required: true, message: "请上传工具图标", trigger: "change" }],
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
      const params: ApiReq.SaveTool = { ...formValue.value, icon: formValue.value.icon[0]?.url || "" };
      if (props.row?.id) {
        params.id = props.row.id;
      }
      const res = await saveTool(params);
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
          const { name, icon } = props.row;
          formValue.value = { name, icon: [{ id: "001", url: icon }] };
        }
      });
    }
  }
);
</script>

<style lang="scss" scoped></style>
