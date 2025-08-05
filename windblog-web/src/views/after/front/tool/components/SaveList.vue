<!-- 新增/编辑标签 -->
<template>
  <el-dialog :title="`${row?.id ? '编辑' : '新增'}工具`" :model-value="modelValue" width="500px" @close="commEvent.onClose">
    <el-form ref="formRef" :model="formValue" :rules="rules">
      <el-form-item label="所属菜单" prop="toolId">
        <el-select v-model="formValue.toolId">
          <el-option v-for="item in toolOption" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="工具名称" prop="name">
        <el-input v-model="formValue.name" placeholder="请输入工具名称" />
      </el-form-item>
      <el-form-item label="工具链接" prop="link">
        <el-input v-model="formValue.link" placeholder="请输入工具链接" />
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
import { saveToolContent } from "@/api/admin/tool";
import type { ApiRes, ApiReq } from "@/api/admin/tool/interface";
import { ElMessage } from "element-plus";

const props = defineProps({
  modelValue: { type: Boolean, default: false },
  toolOption: { type: Array as PropType<{ label: string; value: number }[]>, default: () => [] },
  row: { type: Object as PropType<ApiRes.ToolContentPage>, default: () => {} },
});
const emit = defineEmits(["update:modelValue", "refresh"]);
// 参数
const formRef = ref<FormInstance>(); // 表单实例
// 表单数据
const formValue = ref({
  toolId: undefined as number | undefined,
  name: "",
  link: "",
  icon: [] as any[],
});
// 表单校验
const rules: FormRules = {
  toolId: [{ required: true, message: "请选择所属菜单", trigger: "change" }],
  name: [{ required: true, message: "请输入工具名称", trigger: "blur" }],
  link: [{ required: true, message: "请输入工具链接", trigger: "blur" }],
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
      const params: ApiReq.SaveToolContent = { ...formValue.value, toolId: Number(formValue.value.toolId), icon: formValue.value.icon[0]?.url || "" };
      if (props.row?.id) {
        params.id = props.row.id;
      }
      const res = await saveToolContent(params);
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
          const { name, toolId, link, icon } = props.row;
          formValue.value = { name, toolId, link, icon: [{ id: "001", url: icon }] };
        }
      });
    }
  }
);
</script>

<style lang="scss" scoped></style>
