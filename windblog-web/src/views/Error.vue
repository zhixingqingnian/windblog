<template>
  <div class="error-box">
    <div class="error-box-img">
      <img src="@/assets/images/404.svg" class="error-img" />
    </div>
    <div class="error-option">
      <div class="error-text">抱歉，你访问的页面不存在~</div>
      <el-button type="primary" @click="onBack">返回上一页</el-button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from "vue-router";
const router = useRouter();
const onBack = () => {
  router.back();
};
</script>

<style lang="scss" scoped>
.error-box {
  width: 100%;
  .error-box-img {
    width: 100%;
    text-align: center;
    .error-img {
      height: 60vh;
    }
  }
  .error-option {
    display: flex;
    align-items: center;
    justify-content: center;
    margin-top: 20px;
    gap: 20px;
    .error-text {
      color: #999;
      font-size: 16px;
    }
  }
}
</style>
