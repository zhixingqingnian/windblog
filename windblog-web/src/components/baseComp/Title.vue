<!--
 * @Description: 标题组件
 * @Version: 1.0
 * @Author: yzq
 * @Date: 2025-06-05 16:19:41
 * @FilePath: \weblog\src\components\baseComp\Title.vue
 * @LastEditors: yzq
 * @LastEditTime: 2025-06-11 09:56:34
-->
<template>
  <div class="mycomp-title">{{ title }}</div>
</template>

<script setup lang="ts">
defineProps({
  title: { type: String, default: "标题" },
});
</script>

<style lang="scss" scoped>
.mycomp-title {
  font-size: 16px;
  font-weight: bold;
  display: flex;
  align-items: center;
  &::before {
    content: "";
    display: inline-block;
    width: 4px;
    height: 16px;
    background-color: $main-color;
    margin-right: 8px;
    border-radius: 8px;
  }
}
</style>
