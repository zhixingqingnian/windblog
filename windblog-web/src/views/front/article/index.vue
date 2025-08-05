<!--
 * @Description: 
 * @Version: 1.0
 * @Author: yzq
 * @Date: 2025-06-04 15:14:59
 * @FilePath: \weblog\src\views\front\article\index.vue
 * @LastEditors: yzq
 * @LastEditTime: 2025-06-05 16:35:00
-->
<template>
  <div class="front-article-box">
    <el-row :gutter="20">
      <!-- 章节 -->
      <el-col :span="6">
        <el-affix :offset="70">
          <div class="article-card">
            <Title title="章节目录" />
            <Toc />
          </div>
        </el-affix>
      </el-col>
      <!-- 文章 -->
      <el-col :span="18">
        <!-- 文章信息 -->
        <div class="article-card">
          <div class="article-title">{{ article.title }}</div>
          <div class="article-desc">
            <div class="article-time">发布时间：{{ article.createTime }}</div>
            <div class="article-category">分类：{{ article.categoryName }}</div>
          </div>
          <div ref="articleContentRef" class="article-content" v-viewer v-html="article.content"></div>
          <div class="article-tag-read">
            <div class="article-tag">
              标签：
              <el-space>
                <el-tag v-for="n in article.tags" :key="n.id" size="small" type="info">{{ n.name }}</el-tag>
              </el-space>
            </div>
            <div class="article-read">
              阅读量：
              <span class="read-num">{{ article.readNum }}</span>
            </div>
          </div>
        </div>
        <!-- 上/下篇文章 -->
        <div v-if="article.preArticle || article.nextArticle" class="article-card mt-10">
          <el-row>
            <el-col v-if="article.preArticle" :span="12">
              <div @click="commEvent.onOther(article.preArticle.articleId)" class="article-other">
                <el-tag type="info">上一篇</el-tag>
                <div class="article-other-title">{{ article.preArticle.articleTitle }}</div>
              </div>
            </el-col>
            <el-col v-if="article.nextArticle" :span="12">
              <div @click="commEvent.onOther(article.nextArticle.articleId)" class="article-other">
                <el-tag type="info">下一篇</el-tag>
                <div class="article-other-title">{{ article.nextArticle.articleTitle }}</div>
              </div>
            </el-col>
          </el-row>
        </div>
        <!-- 评论 -->
        <div class="article-card mt-10">
          <!-- 评论标题 -->
          <div class="article-comment">
            <Title title="评论" />
            <div class="article-comment-num"></div>
          </div>
          <!-- 评论内容 -->
          <div class="article-comment-content">
            <div v-for="item in comments" :key="item.id" class="article-comment-list">
              <div class="comment-user">
                <img :src="item.avatar" class="comment-user-avatar" />
                <div class="comment-user-info">
                  <div class="ifno-name">{{ item.username }}</div>
                  <div class="info-desc">{{ item.content }}</div>
                  <div class="info-footer">
                    <div class="info-time">{{ item.time }}</div>
                    <el-icon class="c-p"><ChatDotRound /></el-icon>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from "vue";
import Title from "@/components/baseComp/Title.vue";
import Toc from "@/components/baseComp/Toc.vue";
import { useRoute, useRouter } from "vue-router";
import hljs from "highlight.js";
// 代码高亮样式
import "highlight.js/styles/tokyo-night-dark.css";
import { articleDetail } from "@/api/front";
import type { ApiRes } from "@/api/front/interface";
// 参数
const route = useRoute();
const router = useRouter();
const articleContentRef = ref<any>(null); // 文章内容实例
// 文章详情
const article = ref<ApiRes.ArticleDetail>({
  title: "标题",
  categoryId: 1,
  categoryName: "分类名称",
  content: "内容",
  createTime: "2025-01-01 00:00:00",
  nextArticle: null,
  preArticle: null,
  readNum: 1,
  tags: [{ id: 1, name: "标签名称" }],
});
// 评论
const comments = [
  {
    id: "001",
    username: "知性",
    content: "vue如今已经是前端必备技能之一，唯有深入原理才能更进一步",
    avatar: "https://img0.baidu.com/it/u=2895902758,4240700774&fm=253&fmt=auto&app=120&f=JPEG?w=800&h=800",
    time: "2025-06-01",
  },
];
// 事件
const commEvent = {
  // 获取详情
  getDetail: async (articleId: number) => {
    const { data } = await articleDetail({ articleId });
    article.value = data;
    commEvent.highLightContent();
  },
  // 高亮代码
  highLightContent: () => {
    // 使用 MutationObserver 监视 DOM 的变化
    const observer = new MutationObserver((mutationsList) => {
      for (let mutation of mutationsList) {
        if (mutation.type === "childList") {
          // 获取所有 pre code 节点
          let highlight = document.querySelectorAll("pre code");
          // 循环高亮
          highlight.forEach((block: any) => {
            hljs.highlightBlock(block);
          });
        }
      }
    });
    // 配置监视子节点的变化
    const config = { childList: true, subtree: true };
    // 开始观察正文内容变化
    observer.observe(articleContentRef.value, config);
  },
  // 查看其他文章
  onOther: (id: number) => {
    router.replace(`/front/article/${id}`);
    commEvent.getDetail(id);
  },
};
onMounted(() => {
  const { id = "" } = route.params;
  if (id) {
    commEvent.getDetail(Number(id));
  }
});
</script>

<style lang="scss" scoped>
.front-article-box {
  width: 100%;
  .article-card {
    width: 100%;
    background-color: #fff;
    padding: 10px;
    box-sizing: border-box;
    box-shadow: 0 0 10px #0000001a;
    border-radius: 5px;
  }
  .article-title {
    width: 100%;
    font-size: 20px;
    font-weight: bold;
    color: #111;
  }
  .article-desc {
    width: 100%;
    margin-top: 10px;
    display: flex;
    gap: 10px;
    color: #999;
    font-size: 12px;
  }
  .article-content {
    margin-top: 10px;
    font-size: 14px;
    line-height: 30px;
  }
  .article-tag-read {
    width: 100%;
    align-items: center;
    display: flex;
    justify-content: space-between;
    .article-tag {
      width: 100%;
      color: #999;
      font-size: 12px;
      margin-top: 10px;
      display: flex;
    }
    .article-read {
      font-size: 13px;
      flex-shrink: 0;
      .read-num {
        color: $main-color;
        font-weight: bold;
      }
    }
  }
  .article-other {
    width: 100%;
    display: flex;
    gap: 10px;
    align-items: center;
    user-select: none;
    cursor: pointer;
    padding: 10px;
    box-sizing: border-box;
    &:hover {
      background-color: #f3f3f3;
    }
    .article-other-title {
      flex: 1;
      min-width: 0;
      overflow: hidden;
      white-space: nowrap;
      text-overflow: ellipsis;
      font-size: 14px;
      color: #111;
    }
  }
  .article-comment {
    width: 100%;
    display: flex;
    align-items: center;
    .article-comment-num {
      font-size: 16px;
      font-weight: bold;
      margin-left: 10px;
    }
  }
  .article-comment-content {
    width: 100%;
    margin-top: 10px;
    .comment-user {
      width: 100%;
      display: flex;
      .comment-user-avatar {
        width: 40px;
        height: 40px;
        border-radius: 50%;
      }
      .comment-user-info {
        flex: 1;
        margin-left: 10px;
        .info-name {
          width: 100%;
          font-size: 16px;
          color: #111;
        }
        .info-desc {
          width: 100%;
          font-size: 14px;
          line-height: 30px;
        }
        .info-footer {
          width: 100%;
          margin-top: 5px;
          display: flex;
          align-items: center;
          gap: 20px;
          .info-time {
            font-size: 14px;
            color: #999;
          }
          .c-p {
            cursor: pointer;
          }
        }
      }
    }
  }
  // markdown 配置
  /* h1, h2, h3, h4, h5, h6 标题样式 */
  :deep(.article-content h1, .article-content h2, .article-content h3, .article-content h4, .article-content h5, .article-content h6) {
    color: #292525;
    line-height: 150%;
    font-family: PingFang SC, Helvetica Neue, Helvetica, Hiragino Sans GB, Microsoft YaHei, "\5FAE\8F6F\96C5\9ED1", Arial, sans-serif;
  }

  :deep(.article-content h2) {
    line-height: 1.5;
    font-weight: 700;
    font-synthesis: style;
    font-size: 24px;
    margin-top: 40px;
    margin-bottom: 26px;
    line-height: 140%;
    border-bottom: 1px solid rgb(241 245 249);
    padding-bottom: 15px;
  }

  :deep(.article-content h3) {
    font-size: 20px;
    margin-top: 40px;
    margin-bottom: 16px;
    font-weight: 600;
  }

  :deep(.article-content h4) {
    font-size: 18px;
    margin-top: 30px;
    margin-bottom: 16px;
    font-weight: 600;
  }

  :deep(.article-content h5) {
    font-size: 16px;
    margin-top: 30px;
    margin-bottom: 14px;
    font-weight: 600;
  }

  :deep(.article-content h6) {
    font-size: 16px;
    margin-top: 30px;
    margin-bottom: 14px;
    font-weight: 600;
  }
  /* p 段落样式 */
  :deep(.article-content p) {
    letter-spacing: 0.3px;
    margin: 0 0 20px;
    line-height: 30px;
    color: #4c4e4d;
    font-weight: 400;
    word-break: normal;
    word-wrap: break-word;
    font-family: -apple-system, BlinkMacSystemFont, PingFang SC, Hiragino Sans GB, Microsoft Yahei, Arial, sans-serif;
  }
  /* blockquote 引用样式 */
  :deep(.article-content blockquote) {
    border-left: 2.3px solid rgb(52, 152, 219);
    quotes: none;
    background: rgb(236, 240, 241);
    color: #777;
    font-size: 16px;
    margin-bottom: 20px;
    padding: 24px;
  }

  /* 设置 blockquote 中最后一个 p 标签的 margin-bottom 为 0 */
  :deep(.article-content blockquote p:last-child) {
    margin-bottom: 0;
  }
  /* 斜体样式 */
  :deep(.article-content em) {
    color: #c849ff;
  }
  /* 超链接样式 */
  :deep(.article-content a) {
    color: #167bc2;
    text-decoration: none;
  }
  :deep(.article-content a:hover) {
    text-decoration: underline;
  }
  /* ul 样式 */
  :deep(.article-content ul) {
    padding-left: 2rem;
  }

  :deep(.article-content > ul) {
    margin-bottom: 20px;
  }

  :deep(.article-content ul li) {
    list-style-type: disc;
    padding-top: 5px;
    padding-bottom: 5px;
    font-size: 16px;
  }

  :deep(.article-content ul li p) {
    margin-bottom: 0 !important;
  }

  :deep(.article-content ul ul li) {
    list-style-type: square;
  }
  /* ol 样式 */
  :deep(.article-content ol) {
    list-style-type: decimal;
    padding-left: 2rem;
  }
  /* 图片样式 */
  :deep(.article-content img) {
    max-width: 100%;
    overflow: hidden;
    display: block;
    margin: 0 auto;
    border-radius: 8px;
  }

  :deep(.article-content img:hover, img:focus) {
    box-shadow: 2px 2px 10px 0 rgba(0, 0, 0, 0.15);
  }
  /* 图片描述文字 */
  :deep(.image-caption) {
    min-width: 20%;
    max-width: 80%;
    min-height: 43px;
    display: block;
    padding: 10px;
    margin: 0 auto;
    font-size: 13px;
    color: #999;
    text-align: center;
  }
  /* code 样式 */
  :deep(.article-content code:not(pre code)) {
    padding: 2px 4px;
    margin: 0 2px;
    font-size: 95% !important;
    border-radius: 4px;
    color: rgb(41, 128, 185);
    background-color: rgba(27, 31, 35, 0.05);
    font-family: Operator Mono, Consolas, Monaco, Menlo, monospace;
  }
  /* pre code 样式 */
  :deep(code) {
    font-size: 98%;
  }

  :deep(pre) {
    margin-bottom: 20px;
  }

  :deep(pre code.hljs) {
    padding-top: 2rem;
    padding-left: 1rem;
    padding-right: 1rem;
    padding-bottom: 0.7rem;
    border-radius: 6px;
  }

  :deep(pre:before) {
    background: #fc625d;
    border-radius: 50%;
    box-shadow: 20px 0 #fdbc40, 40px 0 #35cd4b;
    content: " ";
    height: 10px;
    margin-top: 10px;
    margin-left: 10px;
    position: absolute;
    width: 10px;
  }
  /* 表格样式 */
  :deep(table) {
    margin-bottom: 20px;
    width: 100%;
  }

  :deep(table tr) {
    background-color: #fff;
    border-top: 1px solid #c6cbd1;
  }

  :deep(table th) {
    padding: 6px 13px;
    border: 1px solid #dfe2e5;
  }

  :deep(table td) {
    padding: 6px 13px;
    border: 1px solid #dfe2e5;
  }

  :deep(table tr:nth-child(2n)) {
    background-color: #f6f8fa;
  }
  /* hr 横线 */
  :deep(hr) {
    margin-bottom: 20px;
  }
}
</style>
