<template>
  <div class="home-list">
    <template v-if="articles.length > 0">
      <!-- 列表内容 -->
      <div class="home-list-content">
        <div v-for="item in articles" :key="item.id" class="content-list" @click="commEvent.showArticle(item)">
          <!-- 文章详情 -->
          <div class="content-info">
            <div class="content-title">{{ item.title }}</div>
            <div class="content-desc">{{ item.summary }}</div>
            <div class="content-footer">
              <div class="content-footer-ct">
                <span class="category-name">{{ item.category.name }}</span>
                -{{ item.createTime }}
              </div>
              <el-space>
                <el-tag v-for="n in item.tags" :key="n.id" size="small" type="info">{{ n.name }}</el-tag>
              </el-space>
            </div>
          </div>
          <!-- 文章封面 -->
          <div class="content-img">
            <el-image :src="item.cover" class="content-img-cover" />
          </div>
        </div>
      </div>
      <!-- 列表分页 -->
      <el-row v-if="total > 0" justify="end" class="mt-10">
        <el-pagination
          v-model:current-page="pages.current"
          v-model:page-size="pages.pageSize"
          :total="total"
          :page-sizes="[10, 25, 50, 100]"
          layout="total, sizes, prev, pager, next, jumper"
          background
          size="small"
          @change="commEvent.onChange"
        />
      </el-row>
    </template>
    <template v-else>
      <el-empty :image-size="80" />
    </template>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from "vue";
import { useRouter } from "vue-router";
import { articlePage } from "@/api/front";
import type { ApiRes, ApiReq } from "@/api/front/interface";
import { watch } from "vue";
const props = defineProps({
  categoryId: { type: Number, default: 0 },
  tagId: { type: Number, default: 0 },
});
// 参数
const router = useRouter();
const articles = ref<ApiRes.ArticlePage[]>([]); // 文章列表
const total = ref<number>(0); // 总条数
// 页码信息
const pages = ref({
  current: 1,
  pageSize: 10,
});
// 事件
const commEvent = {
  // 文章数据
  getArticles: async () => {
    const params: ApiReq.ArticlePage = { ...pages.value };
    if (props.categoryId && props.categoryId !== 9527) {
      params.categoryId = props.categoryId;
    }
    if (props.tagId && props.tagId !== 9527) {
      params.tagId = props.tagId;
    }
    const { data } = await articlePage(params);
    articles.value = data.result;
    total.value = data.total;
  },
  // 查看文章
  showArticle: (item: any) => {
    router.push(`/front/article/${item.id}`);
  },
  // 切换页码
  onChange: () => {
    commEvent.getArticles();
  },
};
// 分类/标签查询
watch(
  () => props.categoryId,
  () => {
    commEvent.getArticles();
  }
);
watch(
  () => props.tagId,
  () => {
    commEvent.getArticles();
  }
);
onMounted(() => {
  commEvent.getArticles();
});
</script>

<style lang="scss" scoped>
.home-list {
  width: 100%;
  background-color: #fff;
  padding: 10px;
  box-sizing: border-box;
  box-shadow: 0 0 10px #0000001a;
  border-radius: 5px;
  .home-list-content {
    width: 100%;
    .content-list {
      width: 100%;
      padding: 10px;
      box-sizing: border-box;
      display: flex;
      border-bottom: 1px solid #eee;
      user-select: none;
      cursor: pointer;
      &:hover {
        background-color: #f5f5f5;
      }
      .content-info {
        flex: 1;
        display: flex;
        flex-direction: column;
        margin-right: 20px;
        .content-title {
          width: 100%;
          white-space: nowrap;
          overflow: hidden;
          text-overflow: ellipsis;
          font-size: 16px;
          font-weight: bold;
        }
        .content-desc {
          width: 100%;
          flex: 1;
          margin-top: 10px;
          font-size: 13px;
          color: #999;
        }
        .content-footer {
          width: 100%;
          margin-top: 10px;
          display: flex;
          justify-content: space-between;
          align-items: center;
          .content-footer-ct {
            font-size: 12px;
            color: #999;
            .category-name {
              font-weight: bold;
              color: $main-color;
            }
          }
        }
      }
      .content-img {
        width: 150px;
        height: 100px;
        background-color: #f3f3f3;
        overflow: hidden;
        display: flex;
        align-items: center;
        justify-content: center;
        border-radius: 5px;
        .content-img-cover {
          width: 100%;
        }
      }
    }
  }
}
</style>
