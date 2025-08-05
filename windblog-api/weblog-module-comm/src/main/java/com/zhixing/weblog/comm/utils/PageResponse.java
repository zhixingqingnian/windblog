package com.zhixing.weblog.comm.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 分页接收参数
 *
 * @param <T>
 */
@Data
public class PageResponse<T> {

    // 总条数
    private Long total = 0L;

    // 每页显示的记录数，默认每页10条
    private Long pageSize = 10L;

    // 当前页码
    private Long current;

    // 总页数
    private Long pages;

    private List<T> result;


    /**
     * 成功响应
     *
     * @param page myBatis Plus 提供的分页接口
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Response<PageResponse<T>> success(IPage page, List<T> data) {
        PageResponse<T> pageResponse = new PageResponse<>();
        pageResponse.setCurrent(Objects.isNull(page) ? 1L : page.getCurrent());
        pageResponse.setPageSize(Objects.isNull(page) ? 10L : page.getSize());
        pageResponse.setPages(Objects.isNull(page) ? 0L : page.getPages());
        pageResponse.setTotal(Objects.isNull(page) ? 0L : page.getTotal());
        List<T> result = new ArrayList<>();
        result = data == null ? result : data;
        pageResponse.setResult(result);
        Response<PageResponse<T>> response = new Response();
        response.setSuccess(true);
        response.setData(pageResponse);
        return response;
    }
}
