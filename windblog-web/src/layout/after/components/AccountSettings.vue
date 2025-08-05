<!--
 * @Description: 账户设置
 * @Version: 1.0
 * @Author: yzq
 * @Date: 2025-06-05 14:37:10
 * @FilePath: \weblog\src\layout\after\components\AccountSettings.vue
 * @LastEditors: yzq
 * @LastEditTime: 2025-06-11 11:35:38
-->
<template>
  <el-dialog title="账户设置" :model-value="modelValue" width="500px" @close="commEvent.onClose">
    <el-form ref="formRef" :model="formValue" :rules="rules" label-width="100px" label-suffix=":">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="formValue.username" placeholder="请输入用户名" disabled />
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input v-model="formValue.password" type="password" placeholder="请输入密码" show-password />
      </el-form-item>
      <el-form-item label="确认密码" prop="password2">
        <el-input v-model="formValue.password2" type="password" placeholder="请再次输入密码" show-password />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="commEvent.onClose">取消</el-button>
      <el-button type="primary" @click="commEvent.onEnter">确定</el-button>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { nextTick, ref, watch } from "vue";
import { ElMessage } from "element-plus";
import type { FormInstance, FormRules } from "element-plus";
import { updatePassword } from "@/api/admin";
import type { ApiReq } from "@/api/admin/interface";
import tool from "@/utils/tool";
import { useUserStore } from "@/store/user";

const props = defineProps({
  modelValue: { type: Boolean, default: false },
});
const emit = defineEmits(["update:modelValue", "refresh"]);
// 参数
const userStore = useUserStore();
const formRef = ref<FormInstance>(); // 表单实例
// 表单数据
const formValue = ref({
  username: userStore.userInfo.username,
  password: "",
  password2: "",
});
// 校验密码
const validPassword = (_rule: any, value: string, callback: any) => {
  if (value !== formValue.value.password) {
    callback(new Error("两次输入的密码不一致"));
  } else {
    callback();
  }
};
// 表单校验
const rules: FormRules = {
  username: [{ required: true, message: "请输入用户名", trigger: "blur" }],
  password: [{ required: true, message: "请输入密码", trigger: "blur" }],
  password2: [
    { required: true, message: "请再次输入密码", trigger: "blur" },
    { validator: validPassword, trigger: "blur" },
  ],
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
      const params: ApiReq.UpdatePassword = {
        username: formValue.value.username,
        password: formValue.value.password,
      };
      const res = await updatePassword(params);
      if (res.success) {
        ElMessage.success("修改成功");
        commEvent.onClose();
        tool.loginOut();
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
      });
    }
  }
);
</script>

<style lang="scss" scoped></style>
