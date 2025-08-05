<!--
 * @Description: 搜索组件
 * @Version: 1.0
 * @Author: yzq
 * @Date: 2025-06-05 16:19:41
 * @FilePath: \weblog\src\components\ProTable\Search.vue
 * @LastEditors: yzq
 * @LastEditTime: 2025-06-12 18:10:04
-->
<template>
  <div class="search-box">
    <el-form :model="formValue" label-suffix=":">
      <el-row :gutter="20">
        <template v-for="item in searchs" :key="item.prop">
          <el-col v-if="item.formProps?.type === 'select'" :span="commEvent.getSpan(item)">
            <el-form-item :label="item.label" :prop="item.prop">
              <el-select v-model="formValue[item.prop || '']" placeholder="请选择" clearable v-bind="{ ...item.searchProps }">
                <el-option v-for="n in commEvent.getOption(item)" :key="n.value" :label="n.label" :value="n.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col v-else-if="item.formProps?.type === 'date-picker'" :span="commEvent.getSpan(item)">
            <el-form-item :label="item.label" :prop="item.prop">
              <el-date-picker v-model="formValue[item.prop || '']" clearable v-bind="{ ...item.searchProps, ...commEvent.getPlaceholder(item) }" />
            </el-form-item>
          </el-col>
          <el-col v-else :span="commEvent.getSpan(item)">
            <el-form-item :label="item.label" :prop="item.prop">
              <el-input v-model="formValue[item.prop || '']" placeholder="请输入" clearable v-bind="{ ...item.searchProps }" />
            </el-form-item>
          </el-col>
        </template>
        <el-col :span="spanLen">
          <div class="search-btn">
            <el-button type="primary" icon="Search" @click="commEvent.onSearch">查询</el-button>
            <el-button icon="Refresh" @click="commEvent.onRefresh">重置</el-button>
            <div v-if="collapseVisible">
              <div v-if="!collapse" @click="commEvent.onCollapse" class="form-collapse">
                展开
                <el-icon><ArrowDown /></el-icon>
              </div>
              <div v-else @click="commEvent.onCollapse" class="form-collapse">
                收起
                <el-icon><ArrowUp /></el-icon>
              </div>
            </div>
          </div>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script setup lang="ts">
import { computed, onBeforeUnmount, onMounted, ref } from "vue";
import type { PropType } from "vue";
import { formatFormValue } from "./tool";
import type { ColumnsProps } from "./interface";
const props = defineProps({
  config: { type: Array as PropType<ColumnsProps[]>, default: () => [] },
});
const emit = defineEmits(["search"]);
// 参数
const formValue = ref<any>(formatFormValue(props.config)); // 表单值
const collapseVisible = ref<boolean>(false); // 展开收起展示
const collapse = ref<boolean>(false); // 展开收起
const spanNum = ref<number>(6); // 每行span数量
// 事件
const commEvent = {
  // 展开/收起
  onCollapse: () => {
    collapse.value = !collapse.value;
  },
  // 窗口尺寸变化
  onResize: () => {
    const width = document.body.clientWidth;
    if (width < 768) {
      spanNum.value = 24;
    } else if (width < 992) {
      spanNum.value = 12;
    } else if (width < 1200) {
      spanNum.value = 8;
    } else {
      spanNum.value = 6;
    }
    // 初始化时判断是否需要展开
    let _count: number = 0;
    props.config.forEach((item) => {
      if (item?.formProps) {
        const _span = item.formProps.span || 1;
        _count += _span;
      }
    });
    collapseVisible.value = 24 / spanNum.value <= _count;
  },
  getSpan: (item: ColumnsProps): number => {
    let _span: number = JSON.parse(JSON.stringify(spanNum.value));
    if (item.formProps?.span) {
      _span = item.formProps.span * spanNum.value >= 24 ? 24 : item.formProps.span * spanNum.value;
    }
    return _span;
  },
  // 获取下拉项
  getOption: (item: ColumnsProps): ColumnsProps["enum"] => {
    if (item?.enum) return item.enum;
    return [];
  },
  // 查询
  onSearch: () => {
    emit("search", formValue.value);
  },
  // 重置
  onRefresh: () => {
    const _data: ColumnsProps[] = JSON.parse(JSON.stringify(props.config));
    _data.forEach((item) => {
      formValue.value[item?.prop || ""] = undefined;
    });
  },
  // 获取日期选择器提示信息
  getPlaceholder: (item: ColumnsProps) => {
    const pickerType = ["daterange"];
    if (pickerType.includes(item?.searchProps?.type)) {
      return {
        rangeSeparator: "至",
        startPlaceholder: "开始时间",
        endPlaceholder: "结束时间",
      };
    }
    return { placeholder: "请选择" };
  },
};
// 搜索项
const allSearchs = computed(() => {
  const _data: ColumnsProps[] = JSON.parse(JSON.stringify(props.config));
  return _data.filter((item) => item?.formProps);
});
const searchs = computed(() => {
  if (spanNum.value === 24) return allSearchs.value;
  if (collapse.value) {
    return allSearchs.value;
  }
  let _count: number = 0;
  const _result = allSearchs.value.slice(0, 24 / spanNum.value - 1);
  _result.forEach((item) => {
    if (item.formProps?.span) {
      if (item.formProps?.span > 1) {
        _count += item.formProps?.span - 1;
      }
    }
  });
  const _len = 24 / spanNum.value - 1 - _count;
  return _count > 0 ? _result.slice(0, _len) : _result;
});
// 最后一个col的span值
const spanLen = computed(() => {
  let _count: number = 0;
  searchs.value.forEach((item: ColumnsProps) => {
    const _span = item?.formProps?.span || 1;
    _count += _span;
  });
  if (!collapse.value) return 24 - _count * spanNum.value === 0 ? 24 : 24 - _count * spanNum.value;
  const _num: number = 24 / spanNum.value;
  const _remain: number = _count % _num;
  return (_num - _remain) * spanNum.value;
});
onMounted(() => {
  commEvent.onResize();
  window.addEventListener("resize", commEvent.onResize);
});
onBeforeUnmount(() => {
  window.removeEventListener("resize", commEvent.onResize);
});
defineExpose({
  formValue,
});
</script>

<style lang="scss" scoped>
.search-box {
  width: 100%;
  padding: 10px;
  box-sizing: border-box;
  .search-btn {
    width: 100%;
    display: flex;
    min-width: 240px;
    justify-content: flex-end;
    align-items: center;
    .form-collapse {
      display: flex;
      align-items: center;
      font-size: 14px;
      margin-left: 16px;
      color: $main-color;
      cursor: pointer;
      user-select: none;
      gap: 5px;
      &:hover {
        opacity: 0.6;
      }
      &:active {
        opacity: 1;
      }
    }
  }
}
</style>
