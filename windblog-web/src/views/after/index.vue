<!--
 * @Description: 
 * @Version: 1.0
 * @Author: yzq
 * @Date: 2025-06-05 09:56:10
 * @FilePath: \weblog\src\views\after\index.vue
 * @LastEditors: yzq
 * @LastEditTime: 2025-06-13 14:18:52
-->
<template>
  <div class="after-box">
    <!-- 导航菜单 -->
    <div class="after-aside">
      <div class="menu-logo">
        <img src="@/assets/images/logo.svg" class="logo-img" :class="{ 'active-logo-img': collapse }" />
        <span v-if="showText" class="logo-text">weblog</span>
      </div>
      <div class="menu-content">
        <el-menu
          background-color="#18191f"
          text-color="#fff"
          :unique-opened="true"
          :default-active="activeMenu"
          :collapse="collapse"
          class="el-menu-vertical-demo"
        >
          <after-aside :menus="menus" />
        </el-menu>
      </div>
    </div>
    <div class="after-main">
      <!-- 顶部状态栏 -->
      <after-header v-model="collapse" :menus="menus" />
      <!-- 内容区域展示 -->
      <div class="after-content">
        <div class="after-content-box">
          <router-view v-slot="{ Component }">
            <Transition name="fade">
              <keep-alive :max="10">
                <component :is="Component" />
              </keep-alive>
            </Transition>
          </router-view>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted, ref, watch } from "vue";
import AfterAside from "@/layout/after/AfterAside.vue";
import AfterHeader from "@/layout/after/AfterHeader.vue";
import { useRoute } from "vue-router";
import { useUserStore } from "@/store/user";
import { userInfo } from "@/api/admin";
import { afterMenus } from "@/router/after";
// 参数
const route = useRoute();
const userStore = useUserStore();
const collapse = ref<boolean>(false); // 侧边栏状态
const showText = ref<boolean>(true); // 侧边栏文字状态
// 侧边栏菜单
const menus: CommType.Menus[] = afterMenus();
// 事件
const commEvent = {
  // 获取用户信息
  getUser: async () => {
    const { data } = await userInfo();
    userStore.setUserInfo(data);
  },
};
// 激活菜单
const activeMenu = computed<string>(() => {
  let _activeMenu: string = "/after/home";
  if (route) {
    _activeMenu = route.path;
  }
  return _activeMenu;
});
watch(
  () => collapse.value,
  (val) => {
    if (val) {
      showText.value = false;
    } else {
      setTimeout(() => {
        showText.value = true;
      }, 300);
    }
  }
);
onMounted(() => {
  if (!userStore.userInfo.username) {
    commEvent.getUser();
  }
});
</script>

<style lang="scss" scoped>
.after-box {
  width: 100vw;
  height: 100vh;
  background-color: #f3f3f3;
  display: flex;
  .after-aside {
    width: fit-content;
    height: 100%;
    background-color: $menu-bg-color;
    .menu-logo {
      width: 100%;
      transition: all 0.3s;
      height: 60px;
      display: flex;
      align-items: center;
      justify-content: center;
      overflow: hidden;
      padding: 10px;
      box-sizing: border-box;
      border-bottom: 1px solid #ffffff20;
      gap: 10px;
      .logo-img {
        width: 50px;
        transition: all 0.3s;
      }
      .logo-text {
        font-size: 18px;
        font-weight: bold;
        color: #fff;
      }
      .active-logo-img {
        width: 35px;
      }
    }
    .menu-content {
      position: relative;
      width: 100%;
      height: calc(100% - 60px);
      overflow-y: auto;
      .el-menu-vertical-demo {
        border-right: 1px solid $menu-bg-color;
      }
      .el-menu-vertical-demo:not(.el-menu--collapse) {
        width: 240px;
      }
    }
  }
  .after-main {
    flex: 1;
    display: flex;
    flex-direction: column;
    .after-content {
      width: 100%;
      flex: 1;
      position: relative;
      .after-content-box {
        position: absolute;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        padding: 10px;
        box-sizing: border-box;
        overflow-y: auto;
      }
    }
  }
}
// 内容区域过渡动画：淡入淡出效果
// 刚开始进入时
.fade-enter-from {
  opacity: 0;
}

// 刚开始结束
.fade-enter-to {
  opacity: 1;
}

// 刚开始离开
.fade-leave-from {
  opacity: 1;
}

// 离开已结束
.fade-leave-to {
  opacity: 0;
}

// 离开进行中
.fade-leave-active {
  transition: all 0.3s;
}

// 进入进行中
.fade-enter-active {
  transition: all 0.3s;
  transition-delay: 0.3s;
}
</style>
