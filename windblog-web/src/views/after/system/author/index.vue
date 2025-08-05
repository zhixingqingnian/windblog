<template>
  <div class="system-author-box">
    <el-card>
      <el-form ref="formRef" :model="formValue" :rules="rules">
        <el-row :gutter="20">
          <el-col :span="24" class="mb-10">
            <Title title="基本信息" />
          </el-col>
          <el-col :span="24">
            <el-form-item label="博客logo" prop="logo">
              <Upload v-model="formValue.logo" :max-count="1" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="作者头像" prop="avatar">
              <Upload v-model="formValue.avatar" :max-count="1" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="博客名称" prop="name">
              <el-input v-model="formValue.name" placeholder="请输入博客名称" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="作者名称" prop="author">
              <el-input v-model="formValue.author" placeholder="请输入作者名称" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="博客简介" prop="introduction">
              <el-input type="textarea" v-model="formValue.introduction" :rows="3" placeholder="请输入博客简介" />
            </el-form-item>
          </el-col>
          <el-col :span="24" class="mb-10">
            <Title title="资源地址" />
          </el-col>
          <el-col :span="12">
            <el-form-item label="GitHub地址" prop="githubHomepage">
              <el-input v-model="formValue.githubHomepage" :rows="3" placeholder="请输入GitHub地址" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="CSDN地址" prop="csdnHomepage">
              <el-input v-model="formValue.csdnHomepage" :rows="3" placeholder="请输入CSDN地址" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Gitee地址" prop="giteeHomepage">
              <el-input v-model="formValue.giteeHomepage" :rows="3" placeholder="请输入Gitee地址" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="掘金地址" prop="juejinHomepage">
              <el-input v-model="formValue.juejinHomepage" :rows="3" placeholder="请输入掘金地址" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <el-row>
        <el-button type="primary" @click="commEvent.onSave">保存配置</el-button>
      </el-row>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import Upload from "@/components/Upload/index.vue";
import Title from "@/components/baseComp/Title.vue";
import { ElMessage } from "element-plus";
import type { FormInstance, FormRules } from "element-plus";
import { saveBlog, blogDetail } from "@/api/admin";
import type { ApiReq } from "@/api/admin/interface";
import { useUserStore } from "@/store/user";

// 参数
const userStore = useUserStore();
const formRef = ref<FormInstance>(); // 表单实例
// 表单参数
const formValue = ref({
  logo: [] as any[],
  avatar: [] as any[],
  name: "",
  author: "",
  introduction: "",
  githubHomepage: "",
  csdnHomepage: "",
  giteeHomepage: "",
  juejinHomepage: "",
});
// 表单校验
const rules: FormRules = {
  logo: [{ required: true, message: "请上传log", trigger: "change" }],
  avatar: [{ required: true, message: "请上传头像", trigger: "change" }],
  name: [{ required: true, message: "请输入博客名称", trigger: "blur" }],
  author: [{ required: true, message: "请输入作者名称", trigger: "blur" }],
  introduction: [{ required: true, message: "请输入博客简介", trigger: "blur" }],
};
// 方法
const commEvent = {
  // 获取详情信息
  getDetail: async () => {
    const { data } = await blogDetail();
    const { logo, avatar, name, author, introduction, githubHomepage, csdnHomepage, giteeHomepage, juejinHomepage } = data;
    formValue.value = {
      logo: [{ id: "001", url: logo }],
      avatar: [{ id: "001", url: avatar }],
      name,
      author,
      introduction,
      githubHomepage,
      csdnHomepage,
      giteeHomepage,
      juejinHomepage,
    };
  },
  onSave: () => {
    if (!formRef.value) return;
    formRef.value.validate(async (valid) => {
      if (!valid) return;
      const params: ApiReq.SaveBlog = {
        ...formValue.value,
        logo: formValue.value.logo[0].url,
        avatar: formValue.value.avatar[0].url,
      };
      const { success } = await saveBlog(params);
      if (success) {
        ElMessage.success("保存成功");
        userStore.resetAuthorInfo();
        commEvent.getDetail();
      }
    });
  },
};
onMounted(() => {
  commEvent.getDetail();
});
</script>

<style lang="scss" scoped>
.system-author-box {
  width: 100%;
}
</style>
