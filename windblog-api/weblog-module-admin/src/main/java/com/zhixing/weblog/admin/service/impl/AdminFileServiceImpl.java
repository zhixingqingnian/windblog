package com.zhixing.weblog.admin.service.impl;

import com.zhixing.weblog.admin.model.vo.file.UploadFileResVO;
import com.zhixing.weblog.admin.service.AdminFileService;
import com.zhixing.weblog.admin.utils.MinioUtil;
import com.zhixing.weblog.comm.enums.ResponseCodeEnum;
import com.zhixing.weblog.comm.exception.BizException;
import com.zhixing.weblog.comm.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class AdminFileServiceImpl implements AdminFileService {
    @Autowired
    private MinioUtil minioUtil;

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    @Override
    public Response uploadFile(MultipartFile file) {
        try {
            // 上传文件
            String url = minioUtil.uploadFile(file);
            // 返回图片链接
            return Response.success(UploadFileResVO.builder().url(url).build());
        } catch (Exception e) {
            log.error("-->上传文件至 Minio 错误：", e);
            throw new BizException(ResponseCodeEnum.FILE_UPLOAD_FAILED);
        }
    }
}
