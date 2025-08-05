// 上传配置项
export interface UploadProps {
  modelValue: UploadFileType[];
  uploadType?: "image" | "file";
  maxCount?: number;
  accept?: string;
}
// 上传项
export interface UploadFileType {
  uid: string;
  name?: string;
  url: string;
}
