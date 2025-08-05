<!--
 * @Description: 
 * @Version: 1.0
 * @Author: yzq
 * @Date: 2025-06-04 17:08:01
 * @FilePath: \weblog\src\views\Login.vue
 * @LastEditors: yzq
 * @LastEditTime: 2025-06-11 11:19:12
-->
<template>
  <div class="login-box">
    <el-row :gutter="20">
      <el-col :span="16">
        <div class="login-bg">
          <div class="login-logo">
            <img src="@/assets/images/logo.svg" class="logo-img" />
            <div class="logo-text">blog</div>
          </div>
          <div class="login-bg-img" :class="{ 'loading-success': loading }">
            <img src="@/assets/images/login.svg" class="w-75" />
          </div>
        </div>
      </el-col>
      <el-col :span="8">
        <div class="login-form">
          <div class="login-form-card">
            <div class="login-title">博客后台登录</div>
            <el-form ref="formRef" :model="formValue" :rules="rules" class="mt-20">
              <el-form-item prop="username" class="form-item">
                <div class="form-item-field">
                  <el-icon><User /></el-icon>
                  <el-input v-model="formValue.username" placeholder="请输入用户名" class="ml-10" />
                </div>
              </el-form-item>
              <el-form-item prop="password" class="form-item">
                <div class="form-item-field">
                  <el-icon><Lock /></el-icon>
                  <el-input v-model="formValue.password" type="password" placeholder="请输入密码" class="ml-10" show-password />
                </div>
              </el-form-item>
            </el-form>
            <el-row justify="center">
              <el-button type="primary" class="login-btn" @click="commEvent.onLogin">登 录</el-button>
            </el-row>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { onMounted, onBeforeUnmount, ref } from "vue";
import { ElMessage, type FormInstance, type FormRules } from "element-plus";
import router from "@/router";
import { login } from "@/api";
import { useUserStore } from "@/store/user";
// 参数
const userStore = useUserStore();
const loading = ref<boolean>(false); // 页面加载
const formRef = ref<FormInstance>(); // 表单实例
const formValue = ref({
  username: "admin",
  password: "qwer1234",
}); // 表单参数
const rules: FormRules = {
  username: [{ required: true, message: "请输入用户名", trigger: "blur" }],
  password: [{ required: true, message: "请输入密码", trigger: "blur" }],
};
// 事件
const commEvent = {
  // 登录
  onLogin: () => {
    if (!formRef.value) return;
    formRef.value.validate(async (valid) => {
      if (!valid) return;
      const { data } = await login(formValue.value);
      userStore.setToken(data.token);
      router.push("/after");
      ElMessage.success("登录成功");
    });
  },
  // 回车登录
  listenEnter: (e: KeyboardEvent) => {
    if (e.key === "Enter") {
      commEvent.onLogin();
    }
  },
};
onMounted(() => {
  document.addEventListener("keydown", commEvent.listenEnter);
  setTimeout(() => {
    loading.value = true;
  });
});
onBeforeUnmount(() => {
  document.removeEventListener("keydown", commEvent.listenEnter);
});
</script>

<style lang="scss" scoped>
.login-box {
  width: 100vw;
  height: 100vh;
  overflow: hidden;
  background-color: #f3f3f3;
  .login-bg {
    width: 100%;
    height: 100vh;
    position: relative;
    display: flex;
    align-items: center;
    justify-content: center;
    .login-logo {
      position: absolute;
      left: 10px;
      top: 10px;
      display: flex;
      gap: 10px;
      align-items: center;
      .logo-img {
        width: 35px;
      }
      .logo-text {
        font-size: 16px;
        font-weight: bold;
      }
    }
    .login-bg-img {
      position: absolute;
      width: 100%;
      text-align: center;
      transition: all 0.3s;
      left: -100%;
      .w-75 {
        width: 75%;
      }
    }
    .loading-success {
      left: 0;
    }
  }
  .login-form {
    width: 100%;
    height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;
    .login-form-card {
      width: 400px;
      padding: 10px;
      box-sizing: border-box;
      background-color: #fff;
      box-shadow: 0 0 10px #0000001a;
      border-radius: 10px;
      .login-title {
        width: 100%;
        text-align: center;
        color: $main-color;
        font-size: 20px;
        font-weight: bold;
        padding: 10px 0;
      }
      .form-item {
        padding: 0;
        margin: 0;
        width: 100%;
        margin-bottom: 20px;
        .form-item-field {
          display: flex;
          align-items: center;
          width: 100%;
          padding: 5px 10px;
          box-sizing: border-box;
          background-color: #f3f3f3;
          font-size: 16px;
          border-radius: 5px;
          :deep(.el-input__wrapper) {
            border: none !important;
            box-shadow: none !important;
            background-color: #f3f3f3;
          }
        }
      }
      .login-btn {
        width: 60%;
        height: 40px;
        margin: 20px 0;
        font-size: 16px;
      }
    }
  }
}
</style>
