import dayjs from "dayjs";
// 日期快捷选项
export const shortcuts = [
  {
    text: "最近一周",
    value: [dayjs().subtract(1, "week"), dayjs()],
  },
  {
    text: "最近一个月",
    value: [dayjs().subtract(1, "month"), dayjs()],
  },
  {
    text: "最近三个月",
    value: [dayjs().subtract(3, "month"), dayjs()],
  },
  {
    text: "最近一年",
    value: [dayjs().subtract(1, "year"), dayjs()],
  },
];
