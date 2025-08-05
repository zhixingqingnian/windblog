<template>
  <div class="upload-box">
    <!-- 图片类型 -->
    <div v-if="uploadType === 'image'" class="image-content">
      <div v-for="(item, index) in modelValue" :key="item.uid" class="image-list">
        <el-image :src="item.url" class="image-item" />
        <div class="image-option">
          <el-icon @click="commEvent.onPreview(index)"><ZoomIn /></el-icon>
          <el-icon @click="commEvent.onDelete(item)"><Delete /></el-icon>
        </div>
      </div>
      <el-upload
        v-if="modelValue.length < maxCount"
        :http-request="commEvent.httpRequest"
        @change="commEvent.onChange"
        :accept="accept"
        :show-file-list="false"
      >
        <div class="image-list">
          <el-icon class="image-icon"><Plus /></el-icon>
        </div>
      </el-upload>
      <!-- 图片预览 -->
      <el-image-viewer v-if="showPreview" :url-list="previewList" show-progress :initial-index="previewIndex" @close="showPreview = false" />
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, computed } from "vue";
import type { UploadFileType, UploadProps } from "./interface";
import type { UploadRequestOptions, UploadFile } from "element-plus";
import { uploadFile } from "@/api";
import tool from "@/utils/tool";

const props = withDefaults(defineProps<UploadProps>(), {
  modelValue: () => [],
  uploadType: "image",
  maxCount: Number.MAX_SAFE_INTEGER,
  accept: "",
});
const emit = defineEmits(["update:modelValue"]);
// 参数
const showPreview = ref<Boolean>(false); // 图片预览弹窗
const previewIndex = ref<number>(0); // 预览下标
const previewList = computed(() => props.modelValue.map((item) => item.url)); // 预览图片
// 事件
const commEvent = {
  httpRequest: (options: UploadRequestOptions): Promise<any> => {
    return new Promise(async (resolve, reject) => {
      const _file = options.file;
      const formData = new FormData();
      formData.append("file", _file);
      const { success, data } = await uploadFile(formData);
      if (success) {
        const _upload: UploadFileType = { uid: tool.generateUid(), url: data.url, name: options.filename };
        resolve(_upload);
      }
      reject();
    });
  },
  onChange: (uploadFile: UploadFile) => {
    const { status, response } = uploadFile;
    if (status === "success") {
      const _fileList: UploadFileType[] = JSON.parse(JSON.stringify(props.modelValue));
      emit("update:modelValue", [..._fileList, response]);
    }
  },
  // 预览
  onPreview: (index: number) => {
    previewIndex.value = index;
    showPreview.value = true;
  },
  // 删除
  onDelete: (item: UploadFileType) => {
    const _fileList: UploadFileType[] = JSON.parse(JSON.stringify(props.modelValue));
    const _newFileList = _fileList.filter((val) => val.uid !== item.uid);
    emit("update:modelValue", _newFileList);
  },
};
</script>

<style lang="scss" scoped>
.upload-box {
  width: 100%;
  .image-content {
    width: 100%;
    display: flex;
    flex-wrap: wrap;
    align-content: flex-start;
    gap: 10px;
    .image-list {
      position: relative;
      width: 90px;
      height: 90px;
      background-color: #f3f3f3;
      border-radius: 5px;
      display: flex;
      align-items: center;
      justify-content: center;
      overflow: hidden;
      .image-item {
        width: 100%;
      }
      .image-icon {
        font-size: 18px;
      }
      .image-option {
        opacity: 0;
        width: 100%;
        height: 100%;
        position: absolute;
        left: 0;
        top: 0;
        transition: all 0.3s;
        background-color: #00000080;
        display: flex;
        align-items: center;
        justify-content: center;
        gap: 10px;
        font-size: 16px;
        color: #fff;
        font-weight: bold;
        cursor: pointer;
      }
      &:hover .image-option {
        opacity: 1;
      }
    }
    :deep(.el-upload-list) {
      margin: 0;
    }
  }
}
</style>
