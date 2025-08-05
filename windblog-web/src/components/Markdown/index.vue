<template>
  <md-editor v-bind="{ ...$attrs }" @on-upload-img="commEvent.onUpload"></md-editor>
</template>

<script setup lang="ts">
import { MdEditor } from "md-editor-v3";
import "md-editor-v3/lib/style.css";
import { uploadFile } from "@/api";
// 事件
const commEvent = {
  // 图片上传
  onUpload: async (files: File[], callback: any) => {
    await Promise.all(
      files.map((item) => {
        return new Promise(() => {
          const formData = new FormData();
          formData.append("file", item);
          uploadFile(formData).then((res) => {
            callback([res.data.url]);
          });
        });
      })
    );
  },
};
</script>

<style lang="scss" scoped>
:deep(.md-editor-footer) {
  height: 40px;
}
</style>
