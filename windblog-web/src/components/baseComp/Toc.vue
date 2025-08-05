<!-- 文章目录 -->
<template>
  <div class="toc-box">
    <div v-for="item in chapters" :key="item.index" class="toc-list">
      <div @click="commEvent.onLink(item)" class="toc-text" :class="{ 'active-text': item.index === activeIndex }">{{ item.text }}</div>
      <div v-for="n in [...(item.children || [])]" :key="n.index" class="toc-list">
        <div @click="commEvent.onLink(n)" class="toc-text" :class="{ 'active-text': n.index === activeIndex }">{{ n.text }}</div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onBeforeUnmount } from "vue";
interface ChapterType {
  index: number;
  level: number;
  text: string;
  top: number;
  children?: ChapterType[];
}
// 参数
const chapters = ref<ChapterType[]>([]); // 章节信息
const activeIndex = ref<number>(0); // 激活位置
// 事件
const commEvent = {
  // 提取文章内容节点信息
  getContentDom: () => {
    // 节点信息
    const container = document.querySelector(".article-content");
    // 提取信息
    const observer = new MutationObserver((mutationsList) => {
      for (const mutation of mutationsList) {
        if (mutation.type === "childList") {
          if (container) {
            chapters.value = [];
            commEvent.loadContentDom(container);
            const images = container.querySelectorAll("img");
            images.forEach((img) => {
              img.addEventListener("load", () => {
                commEvent.loadContentDom(container);
              });
            });
          }
        }
      }
    });
    const config = { childList: true, subtree: true };
    // 开始观察正文 div 的内容变化
    if (!container) return;
    observer.observe(container, config);
  },
  // 加载内容节点信息
  loadContentDom: (dom: Element) => {
    const titleKeys: string = "h3,h4";
    const hDoms = dom.querySelectorAll(titleKeys);
    // 存放数据数组
    let _chapters: ChapterType[] = [];
    // 下标
    let index: number = 1;
    hDoms.forEach((item) => {
      const node = item as HTMLElement;
      const _level: number = parseInt(node.tagName.substring(1));
      const _text: string = node.innerText;
      const _offsetTop: number = node.offsetTop;
      if (_level === 3) {
        _chapters = [..._chapters, { index, level: _level, text: _text, top: _offsetTop, children: [] }];
      } else {
        const parent: ChapterType = _chapters[_chapters.length - 1];
        if (parent) {
          parent.children = [...(parent.children || []), { index, level: _level, text: _text, top: _offsetTop }];
        }
      }
      index++;
    });
    chapters.value = _chapters;
  },
  // 跳转至指定位置
  onLink: (item: ChapterType) => {
    const dom = document.querySelector("#front-home");
    if (!dom) return;
    dom.scrollTo({ top: item.top, behavior: "smooth" });
  },
  // 滚动监听
  onScroll: (event: Event) => {
    const dom = event.target as HTMLElement;
    const scrollTop: number = dom.scrollTop;
    let _index: number = 0;
    chapters.value.forEach((item) => {
      if (item.top <= scrollTop) {
        _index = item.index;
      }
      if (item.children && item.children.length > 0) {
        item.children.forEach((val) => {
          if (val.top <= scrollTop) {
            _index = val.index;
          }
        });
      }
    });
    activeIndex.value = _index;
  },
};

onMounted(() => {
  commEvent.getContentDom();
  const dom = document.querySelector("#front-home");
  if (dom) {
    dom.addEventListener("scroll", commEvent.onScroll);
  }
});
onBeforeUnmount(() => {
  const dom = document.querySelector("#front-home");
  if (dom) {
    dom.removeEventListener("scroll", commEvent.onScroll);
  }
});
</script>

<style lang="scss" scoped>
.toc-box {
  width: 100%;
  max-height: calc(100vh - 120px);
  overflow-y: auto;
  .toc-list {
    width: 100%;
    padding-left: 5px;
    box-sizing: border-box;
    .toc-text {
      width: 100%;
      padding: 5px 10px;
      box-sizing: border-box;
      font-size: 14px;
      font-weight: bold;
      color: #111;
      cursor: pointer;
      border-radius: 5px;
      &:hover {
        background-color: #36807710;
      }
    }
    .active-text {
      position: relative;
      display: flex;
      align-items: center;
      background-color: #36807730;
      &::before {
        content: "";
        position: absolute;
        left: 0;
        width: 3px;
        height: 100%;
        background-color: $danger-color;
        border-radius: 3px;
      }
    }
  }
}
</style>
