import type { ColumnsProps } from "./interface";

// 格式化表单参数
export const formatFormValue = (data: ColumnsProps[]) => {
  if (!data || data.length === 0) return [];
  const _data = data.filter((item) => item?.formProps);
  return _data.reduce((pre, cur) => {
    pre[cur.prop || ""] = undefined;
    return pre;
  }, {} as any);
};
