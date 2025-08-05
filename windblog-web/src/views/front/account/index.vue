<!--
 * @Description: 
 * @Version: 1.0
 * @Author: yzq
 * @Date: 2025-06-05 15:23:19
 * @FilePath: \weblog\src\views\front\account\index.vue
 * @LastEditors: yzq
 * @LastEditTime: 2025-06-05 16:24:38
-->
<template>
  <div class="front-account-box">
    <el-row :gutter="20">
      <!-- 账号相关 -->
      <el-col :span="6">
        <div class="account-card">
          <!-- 头像信息 -->
          <div class="account-avatar">
            <el-avatar :size="100" :src="userStore.authorInfo.avatar" />
            <div class="user-info">
              <div class="user-name">{{ userStore.authorInfo.author }}</div>
              <div class="user-introduction">{{ userStore.authorInfo.introduction }}</div>
              <div class="user-tag"><el-tag>前端开发工程师</el-tag></div>
            </div>
          </div>
          <!-- 账号信息 -->
          <Title title="账号信息" class="mt-20" />
          <div class="account-info">
            <div v-for="item in accountInfo" :key="item.key" class="info-list">
              <div class="list-label">{{ item.label }}：</div>
              <div class="list-value">{{ item.value }}</div>
            </div>
          </div>
          <Title title="活跃平台" class="mt-10" />
          <div class="active-platfor">
            <div v-for="item in activePlatform" :key="item.key" class="platfor-list">
              <img :src="item.cover" class="platfor-img" />
              <div class="platfor-label">{{ item.label }}</div>
              <el-link :href="item.url" target="_blank">查看</el-link>
            </div>
          </div>
        </div>
      </el-col>
      <!-- 文章相关 -->
      <el-col :span="18">
        <div class="account-card">
          <!-- 数据统计 -->
          <Title title="数据统计" />
          <el-row :gutter="20" class="mt-20">
            <el-col v-for="item in articles" :key="item.key" :span="6">
              <div class="account-article">
                <div class="article-title">{{ item.label }}</div>
                <div class="article-num">{{ item.value }}</div>
              </div>
            </el-col>
          </el-row>
          <!-- 浏览详情 -->
          <Title title="浏览信息" class="mt-20" />
          <div class="look-chart">
            <line-chart />
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { computed } from "vue";
import { useUserStore } from "@/store/user";
import Title from "@/components/baseComp/Title.vue";
import LineChart from "@/components/charts/LineChart.vue";
import giteeCover from "@/assets/images/gitee.png";
import githubCover from "@/assets/images/github.png";
import csdnCover from "@/assets/images/csdn.png";
import juejinCover from "@/assets/images/juejin.png";
interface ActivePlatformType {
  key: string;
  label: string;
  cover: any;
  url: string;
}
// 参数
const userStore = useUserStore();
// 账号信息
const accountInfo = [
  { key: "phone", label: "手机号", value: "15956682104" },
  { key: "email", label: "邮箱", value: "15656682104@163.com" },
];
// 活跃平台
const activePlatform = computed<ActivePlatformType[]>(() => {
  const _form: any = { ...userStore.authorInfo };
  const _activePlatform = [
    { key: "giteeHomepage", label: "Gitee", cover: giteeCover, url: "#" },
    { key: "githubHomepage", label: "Github", cover: githubCover, url: "#" },
    { key: "csdnHomepage", label: "CSDN", cover: csdnCover, url: "#" },
    { key: "juejinHomepage", label: "掘金", cover: juejinCover, url: "#" },
  ];
  let _data: ActivePlatformType[] = [];
  _activePlatform.forEach((item) => {
    if (_form[item.key]) {
      _data = [..._data, { ...item, url: _form[item.key] }];
    }
  });
  return _data;
});
//
const articles = [
  { key: "article", label: "文章", value: 1 },
  { key: "category", label: "分类", value: 5 },
  { key: "tag", label: "标签", value: 2 },
  { key: "look", label: "浏览量", value: 301 },
];
</script>

<style lang="scss" scoped>
.front-account-box {
  width: 100%;
  .account-card {
    width: 100%;
    background-color: #fff;
    padding: 10px;
    box-sizing: border-box;
    box-shadow: 0 0 10px #0000001a;
    border-radius: 5px;
  }
  .account-avatar {
    width: 100%;
    display: flex;
    .user-info {
      flex: 1;
      margin-left: 20px;
      .user-name {
        width: 100%;
        margin-top: 20px;
        font-weight: 20px;
        font-weight: bold;
      }
      .user-introduction {
        width: 100%;
        font-size: 13px;
        color: #666;
        margin-top: 5px;
      }
      .user-tag {
        width: 100%;
        margin-top: 5px;
      }
    }
  }
  .account-info {
    width: 100%;
    margin-top: 10px;
    .info-list {
      padding: 10px;
      box-sizing: border-box;
      display: flex;
      align-items: center;
      .list-label {
        font-size: 14px;
        color: #999;
      }
      .list-value {
        font-size: 14px;
        font-weight: bold;
        color: #111;
      }
    }
  }
  .active-platfor {
    width: 100%;
    margin-top: 10px;
    .platfor-list {
      width: 100%;
      padding: 10px;
      box-sizing: border-box;
      display: flex;
      align-items: center;
      .platfor-img {
        width: 30px;
        height: 30px;
        border-radius: 50%;
      }
      .platfor-label {
        flex: 1;
        margin: 0 20px;
        font-size: 14px;
        font-weight: bold;
      }
    }
  }
  .account-article {
    width: 100%;
    .article-title {
      width: 100%;
      font-size: 16px;
      font-weight: bold;
      text-align: center;
    }
    .article-num {
      width: 100%;
      margin-top: 10px;
      font-size: 16px;
      font-weight: bold;
      color: $main-color;
      text-align: center;
    }
  }
  .look-chart {
    width: 100%;
    height: 300px;
    margin-top: 20px;
  }
}
</style>
